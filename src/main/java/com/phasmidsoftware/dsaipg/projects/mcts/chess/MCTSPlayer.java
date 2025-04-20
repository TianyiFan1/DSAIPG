package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Move;
import com.phasmidsoftware.dsaipg.projects.mcts.core.Node;

import java.util.Comparator;

public class MCTSPlayer {
    private final int simulations;

    public MCTSPlayer(int simulations) {
        this.simulations = simulations;
    }

    public ChessMove selectMove(ChessState state) {
        ChessNode root = new ChessNode(state);

        for (int i = 0; i < simulations; i++) {
            try {
                root.explore();
            } catch (RuntimeException e) {
                // 如果已经探索过就忽略
            }
        }

        return root.children().stream()
                .map(node -> (ChessNode) node)
                .max(Comparator.comparingDouble(n ->
                        (double) n.wins() / Math.max(1, n.playouts())))
                .map(best -> {
                    ChessState result = (ChessState) best.state();
                    return findMove(state, result);
                })
                .orElse(null);
    }

    private ChessMove findMove(ChessState from, ChessState to) {
        for (Move<ChessGame> move : from.moves(from.player())) {
            if (from.next(move).toString().equals(to.toString())) {
                return (ChessMove) move;
            }
        }
        return null;
    }
}
