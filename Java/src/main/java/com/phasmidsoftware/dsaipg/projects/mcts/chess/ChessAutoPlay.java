package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Move;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

import java.util.*;

/**
 * Auto-play demo for ChessGame using random legal moves.
 */
public class ChessAutoPlay {

    public static void main(String[] args) {
        // Set a random seed for reproducibility
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        System.out.println("🧪 Random seed: " + seed);

        ChessGame game = new ChessGame();
        State<ChessGame> state = game.start();

        int turn = 1;

        System.out.println("\n🏁 Starting auto-play Chess match");
        ChessBoard.printBoard();

        while (!state.isTerminal()) {
            System.out.println("Turn #" + turn + " - " + (state.player() == 0 ? "White" : "Black"));

            List<Move<ChessGame>> moves = new ArrayList<>(state.moves(state.player()));
            if (moves.isEmpty()) {
                System.out.println("❌ No legal moves available.");
                break;
            }

            // Randomly select a legal move
            Move<ChessGame> move = moves.get(random.nextInt(moves.size()));
            System.out.println("👉 Move: " + move);

            state = state.next(move);
            ChessBoard.printBoard();

            turn++;
            try {
                Thread.sleep(400); // Add delay for readability
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Game result
        System.out.println("🏁 Game Over after " + (turn - 1) + " turns!");
        state.winner().ifPresentOrElse(
                winner -> System.out.println("🎉 Winner: " + (winner == 0 ? "White" : "Black")),
                () -> System.out.println("🤝 Draw")
        );
    }
}
