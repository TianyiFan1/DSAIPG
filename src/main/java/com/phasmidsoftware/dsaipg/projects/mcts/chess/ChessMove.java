package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Move;

public class ChessMove implements Move<ChessGame> {
    public final int fromX, fromY;
    public final int toX, toY;
    public final int player;

    public ChessMove(int player, int fromX, int fromY, int toX, int toY) {
        this.player = player;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public int player() {
        return player;
    }

    @Override
    public String toString() {
        return String.format("[%d] Move (%d, %d) → (%d, %d)", player, fromX, fromY, toX, toY);
    }
}
