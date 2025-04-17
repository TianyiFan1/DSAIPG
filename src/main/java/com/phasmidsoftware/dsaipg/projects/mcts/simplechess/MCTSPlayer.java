package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Move;
import com.phasmidsoftware.dsaipg.projects.mcts.core.Node;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

import java.util.Comparator;

public class MCTSPlayer {

    private final int simulations;
    private final int maxDepth;

    public MCTSPlayer(int simulations) {
        this(simulations, 5); 
    }

    public MCTSPlayer(int simulations, int maxDepth) {
        this.simulations = simulations;
        this.maxDepth = maxDepth;
    }

    public ChessMove selectMove(ChessState currentState) {
        ChessNode root = new ChessNode(currentState);

        for (int i = 0; i < simulations; i++) {
            simulate(root, 0); 
        }

        return root.children().stream()
                .map(node -> (ChessNode) node)
                .max(Comparator.comparingDouble(n -> (double) n.wins() / n.playouts()))
                .map(best -> {
                    ChessState nextState = (ChessState) best.state();
                    return findMove(currentState, nextState);
                })
                .orElse(null);
    }

    private void simulate(ChessNode node, int depth) {
        if (depth > maxDepth || node.isLeaf()) return;

        if (node.children().isEmpty()) {
            try {
                node.explore();
            } catch (RuntimeException e) {

            }
        } else {
            for (Node<SimpleChessGame> child : node.children()) {
                simulate((ChessNode) child, depth + 1);
            }
        }
    }

    private ChessMove findMove(ChessState from, ChessState to) {
        for (Move<SimpleChessGame> move : from.moves(from.player())) {
            State<SimpleChessGame> after = from.next(move);
            if (after.toString().equals(to.toString())) {
                return (ChessMove) move;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ChessState initialState = new ChessState();
        MCTSPlayer ai = new MCTSPlayer(10); 

        ChessMove move = ai.selectMove(initialState);

        if (move != null) {
            System.out.println("The AI ​​recommended move is：");
            System.out.println("From [" + move.getFromRow() + "," + move.getFromCol() + "] → to [" + move.getToRow() + "," + move.getToCol() + "]");
        } else {
            System.out.println("❌ No legal way out found!");
        }
    }
}

