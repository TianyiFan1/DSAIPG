package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

import java.util.ArrayList;
import java.util.Random;

public class ChessBoard {
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final ArrayList<Chess> list = new ArrayList<>();
    private static final ArrayList<Integer[]> locations = new ArrayList<>();

    public static Chess[][] board = new Chess[ROWS][COLS];
    public static Boolean[][] selectedBoard = new Boolean[ROWS][COLS];
    public static int selectedRow = -1;
    public static int selectedCol = -1;

    static {
        // Initialize the selectedBoard array
        for (int i = 0; i < selectedBoard.length; i++) {
            for (int j = 0; j < selectedBoard[i].length; j++) {
                selectedBoard[i][j] = false;
            }
        }

        // List of piece types and their quantities
        String[] chessNames = {"K", "Q", "R", "B", "KN", "P"};
        int[] chessNumber = {1, 1, 2, 2, 2, 8};

        // Dynamically create chess pieces using reflection (correct package path!)
        for (int i = 0; i < 6; i++) {
            int num = chessNumber[i];
            for (int j = 0; j < num; j++) {
                Class<?> chessClass;
                try {
                    chessClass = Class.forName("com.phasmidsoftware.dsaipg.projects.mcts.simplechess." + chessNames[i]);
                    Chess chess1 = (Chess) chessClass.getConstructor(String.class, Integer.class)
                            .newInstance(chessNames[i], 1);
                    Chess chess2 = (Chess) chessClass.getConstructor(String.class, Integer.class)
                            .newInstance(chessNames[i], 0);

                    list.add(chess1);
                    list.add(chess2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Chess[][] initial() {
        // Clear the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }
        }

        // Prepare all positions on the board
        locations.clear();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                locations.add(new Integer[]{i, j});
            }
        }

        // Randomly place pieces
        Random random = new Random();
        for (int i = list.size() - 1; i >= 0; i--) {
            int index = random.nextInt(locations.size());
            Integer[] location = locations.get(index);
            int row = location[0];
            int col = location[1];
            if (board[row][col] == null) {
                board[row][col] = list.get(i).setCol(col).setRow(row);
                locations.remove(index);
            } else {
                i++; // retry this piece
            }
        }

        return board;
    }

    public static int getROWS() {
        return ROWS;
    }

    public static int getCOLS() {
        return COLS;
    }

    public static void resetSelectedBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                selectedBoard[i][j] = false;
            }
        }
    }
}
