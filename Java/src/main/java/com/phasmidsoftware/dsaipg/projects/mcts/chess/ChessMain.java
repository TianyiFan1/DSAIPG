package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Move;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

public class ChessMain {
    public static void main(String[] args) {
        // Initialize the game and get the initial state
        ChessGame game = new ChessGame();
        State<ChessGame> state = game.start();

        // Print the board visually
        ChessBoard.printBoard();
        // Print the current game state
        System.out.println("Initial game state: " + state);

        // Print which player's turn it is
        System.out.println("Current player: " + (state.player() == 0 ? "White (1)" : "Black (-1)"));

        // Print all valid moves for the current player
        System.out.println("Valid moves:");
        for (Move<ChessGame> move : state.moves(state.player())) {
            System.out.println(move);
        }

        // Print total number of valid moves
        System.out.println("Total number of valid moves: " + state.moves(state.player()).size());
    }
}
