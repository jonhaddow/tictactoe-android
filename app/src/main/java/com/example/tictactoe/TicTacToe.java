package com.example.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToe extends AppCompatActivity {

    // This tracks whose turn it is
    private boolean xturn = true;
    private boolean winState = false;

    // Create Array to access each id in grid
    private final int[][] BUTTON_ARRAY = {
            {
                    R.id.grid1,
                    R.id.grid2,
                    R.id.grid3
            },
            {
                    R.id.grid4,
                    R.id.grid5,
                    R.id.grid6
            },
            {
                    R.id.grid7,
                    R.id.grid8,
                    R.id.grid9
            }
    };

    // dialog messages after game is complete
    private final String DRAW_MESSAGE = "It's a draw! Would you like to play again?";
    private final String NEW_GAME_MESSAGE = "Are you sure you would like to start a new game?";
    private final String WIN_MESSAGE = "The winner is ";
    private final String WIN_MESSAGE_2 = "! Would you like to play again?";

    // default starting turn. true if X, false if O
    private final boolean DEFAULT_TURN = true;

    /**
     * When activity is created, set main activity layout
     * @param savedInstanceState previously saved activity state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

    }

    /**
     * Inflate the option menu on the action bar.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;

    }

    /**
     * When menu item is clicked, look at the item clicked and take action
     * @param item menu item clicked
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // get the id of the item clicked
        switch (item.getItemId()) {

            // if the about menu item, open about activity
            case R.id.action_about:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
                return true;

            // if new game...
            case R.id.action_new_game:
                //If the game is in draw state, start new game immediately
                if (GameLogic.checkDraw(getGridData()) || winState) {
                    playAgain();
                } else {
                    // else check with player dialog
                    checkPlayAgain("new");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * This method is called when a grid square has been selected.
     * @param view The grid square selected
     */
    public void onClick(View view) {

        //If player has won, they cannot continue
        if (winState) {
            return;
        }

        // get id of the button selected
        Button grid = (Button) findViewById(view.getId());

        // if the button has no text (X or O)...
        if (!(grid.getText().equals("X") || grid.getText().equals("O"))) {
            // ... and it's X's turn...
            if (xturn) {
                // ... then change button text to an X
                grid.setText("X");
                grid.setTextColor(getResources().getColor(R.color.colorX));
            } else {
                // ... else change button text to O
                grid.setText("O");
                grid.setTextColor(getResources().getColor(R.color.colorO));
            }

            // start turn animation
            grid.setScaleX(0);
            grid.animate().scaleX(1).start();

            // change turn to the opposite turn state
            changeTurn(!xturn);

            // check if game has been won
            Boolean win = GameLogic.checkWin(getGridData());

            // If someone has won...
            if (win == true) {
                winState = true;
                String winner;
                // Check who's won...
                if (xturn) {
                    winner = "O";
                } else {
                    winner = "X";
                }
                checkPlayAgain(winner);
            } else {
                if (GameLogic.checkDraw(getGridData())) {
                    checkPlayAgain("draw");
                }
            }
        }
    }

    /**
     * Changes the current turn from X to O or O to X
     * @param newTurn current turn: true if X, false if O
     */
    public void changeTurn(boolean newTurn) {

        // change xturn field
        xturn = newTurn;

        // change turn label to new turn
        if (newTurn) {
            ((TextView) findViewById(R.id.turnLabel)).setText("X");
        } else {
            ((TextView) findViewById(R.id.turnLabel)).setText("O");
        }

    }

    /**
     * This shows a dialog which asks the user whether they would like to play again
     * @param winner The winner is either: X, O, draw, new
     */
    public void checkPlayAgain(String winner) {

        //build alert dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)

                // set positive button and play again if clicked
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        playAgain();
                    }
                })

                        // set negative button and do nothing when clicked
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })

                        // stop the dialog from being cancelled
                .setCancelable(false);

        // adapt response message dependant on the winner condition
        if (winner.equals("draw")) {
            dialog.setMessage(DRAW_MESSAGE);
        } else if (winner.equals("new")) {
            dialog.setMessage(NEW_GAME_MESSAGE);
        } else {
            dialog.setMessage(WIN_MESSAGE + winner + WIN_MESSAGE_2);
        }

        //display dialog
        dialog.show();

    }

    /**
     * This sets up a new game
     */
    public void playAgain() {

        // For each button, remove all text
        for (int[] arrays: BUTTON_ARRAY) {
            for (int i: arrays) {
                ((Button) findViewById(i)).setText("");
            }
        }

        // set game win state back to false
        winState = false;

        // change turn to the default
        changeTurn(DEFAULT_TURN);

    }

    /**
     * collect text from every button on grid
     * @return array of text for each grid button
     */
    public String[][] getGridData() {

        String[][] gridText = new String[3][3];

        // Extract text from each space in grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridText[i][j] = ((Button) findViewById(BUTTON_ARRAY[i][j])).getText().toString();
            }
        }

        return gridText;

    }
}
