package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Node;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ChessNode implements Node<ChessGame> {

    private final ChessState state;
    private final Collection<Node<ChessGame>> children = new ArrayList<>();
    private int wins = 0;
    private int playouts = 0;

    public ChessNode(ChessState state) {
        this.state = state;
        if (state.isTerminal()) {
            this.playouts = 1;
            Optional<Integer> result = state.winner();
            if (result.isPresent()) this.wins = 2;  // 胜利得 2 分
            else this.wins = 1;                     // 平局得 1 分
        }
    }

    @Override
    public boolean isLeaf() {
        return state.isTerminal();
    }

    @Override
    public ChessState state() {
        return state;
    }

    @Override
    public boolean white() {
        return state.player() == 0;
    }

    @Override
    public Collection<Node<ChessGame>> children() {
        return children;
    }

    @Override
    public void backPropagate() {
        wins = 0;
        playouts = 0;
        for (Node<ChessGame> child : children) {
            wins += child.wins();
            playouts += child.playouts();
        }
    }

    @Override
    public void addChild(State<ChessGame> s) {
        children.add(new ChessNode((ChessState) s));
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
