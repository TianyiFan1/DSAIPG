package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Chess;

import java.util.ArrayList;
import java.util.Random;

public class ChessBoard {
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final ArrayList<Chess> list = new ArrayList<>();
    private static final ArrayList<Integer[]> locations = new ArrayList<>();

    public static final Chess[][] board = new Chess[ROWS][COLS];
    public static final Boolean[][] selectedBoard = new Boolean[ROWS][COLS];
    public static int selectedRow = -1;
    public static int selectedCol = -1;

    static {
        // Initialize the selectedBoard array
        for (int i = 0; i < selectedBoard.length; i++) {
            for (int j = 0; j < selectedBoard[i].length; j++) {
                selectedBoard[i][j] = false;
            }
        }

        String[] chessNames = {"K", "Q", "R", "B", "KN", "P"};
        int[] chessNumber = {1, 1, 2, 2, 2, 8};

        for (int i = 0; i < chessNames.length; i++) {
            int num = chessNumber[i];
            for (int j = 0; j < num; j++) {
                String fullClassName = "com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Impl." + chessNames[i];
                try {
                    Class<?> chessClass = Class.forName(fullClassName);
                    Chess chess1 = (Chess) chessClass.getConstructor(String.class, Integer.class).newInstance(chessNames[i], 1);
                    Chess chess2 = (Chess) chessClass.getConstructor(String.class, Integer.class).newInstance(chessNames[i], -1);
                    list.add(chess1);
                    list.add(chess2);
                } catch (Exception e) {
                    System.err.println("❌ Failed to load chess piece: " + fullClassName);
                    e.printStackTrace();
                }
            }
        }
    }

    public static Chess[][] initial() {
        // Initialize the board with null values
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }
        }

        locations.clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                locations.add(new Integer[]{i, j});
            }
        }

        // Randomly place chess pieces on the board
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
                i++;
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

    public static void printBoard() {
        System.out.println("\nCurrent board:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Chess piece = board[i][j];
                if (piece == null) {
                    System.out.print(". ");
                } else {
                    // Capital letter for White (1), lowercase for Black (-1)
                    String symbol = piece.getName();
                    if (piece.getFaction() == 1) {
                        System.out.print(symbol.toUpperCase() + " ");
                    } else {
                        System.out.print(symbol.toLowerCase() + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println(); // newline for spacing
    }

}
