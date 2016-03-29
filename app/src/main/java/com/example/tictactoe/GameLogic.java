package com.example.tictactoe;

/**
 * Created by useradmin on 29/02/2016.
 */
public class GameLogic {
    /**
     * Check if the game is in a draw state
     * @param gridText Array of button states
     * @return Turn if draw, else false
     */
    public static Boolean checkDraw(String[][] gridText) {

        // set draw to true
        Boolean draw = true;

        // go through each element in array of button texts
        for (String[] array : gridText) {
            for (String text : array) {
                // if text is clear then set draw to false
                if (text.equals("")) {
                    draw = false;
                }
            }
        }

        // check if draw has been changed to false or not and return
        if (draw == true) {
            return true;
        }
        return false;

    }

    /**
     * Checks if winning move exists
     * @param gridText array of each button text
     * @return true if won, false if not
     */
    public static Boolean checkWin(String[][] gridText) {

        // set win to false
        Boolean win = false;

        //check each row
        for (int i = 0; i < 3; i++) {
            if (checkLine(gridText[i])) {win = true;}
        }

        //check each column
        for (int i = 0; i < 3; i++) {
            String[] column = {gridText[0][i],gridText[1][i],gridText[2][i]};
            if (checkLine(column)) {win = true;}
        }

        //check diagonals
        String[] diagonalBack = {gridText[0][0],gridText[1][1],gridText[2][2]};
        if (checkLine(diagonalBack)) {win = true;}
        String[] diagonalForward = {gridText[2][0],gridText[1][1],gridText[0][2]};
        if (checkLine(diagonalForward)) {win = true;}

        return win;

    }

    /**
     * Compare string array. If they are equal return true, else false
     * @param array string array
     * @return true if equal, false if not
     */
    public static boolean checkLine(String[] array) {

        for (String text : array) {
            if (!text.equals(array[0]) || text.equals("")){
                return false;
            }
        }
        return true;

    }
}
