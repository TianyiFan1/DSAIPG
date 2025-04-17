package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Node;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ChessNode implements Node<SimpleChessGame> {

    private final State<SimpleChessGame> state;
    private final Collection<Node<SimpleChessGame>> children;
    private int wins;
    private int playouts;

    public ChessNode(State<SimpleChessGame> state) {
        this.state = state;
        this.children = new ArrayList<>();
        initializeStats();
    }

    private void initializeStats() {
        if (isLeaf()) {
            playouts = 1;
            Optional<Integer> winner = state.winner();
            if (winner.isPresent())
                wins = 2; // win
            else
                wins = 1; // draw
        }
    }

    @Override
    public boolean isLeaf() {
        return state.isTerminal();
    }

    @Override
    public State<SimpleChessGame> state() {
        return state;
    }

    @Override
    public boolean white() {
        return state.player() == state.game().opener();
    }

    @Override
    public Collection<Node<SimpleChessGame>> children() {
        return children;
    }

    @Override
    public void addChild(State<SimpleChessGame> childState) {
        children.add(new ChessNode(childState));
    }

    @Override
    public void backPropagate() {
        wins = 0;
        playouts = 0;
        for (Node<SimpleChessGame> child : children) {
            wins += child.wins();
            playouts += child.playouts();
        }
    }

    @Override
    public int wins() {
        return wins;
    }

    @Override
    public int playouts() {
        return playouts;
    }
}
