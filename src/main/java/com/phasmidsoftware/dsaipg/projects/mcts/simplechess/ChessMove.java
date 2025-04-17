package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Move;

public class ChessMove implements Move<SimpleChessGame> {
    private final int fromRow;
    private final int fromCol;
    private final int toRow;
    private final int toCol;
    private final int player;

    public ChessMove(int fromRow, int fromCol, int toRow, int toCol, int player) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.player = player;
    }

    @Override
    public int player() {
        return player;
    }

    public int getFromRow() { return fromRow; }
    public int getFromCol() { return fromCol; }
    public int getToRow() { return toRow; }
    public int getToCol() { return toCol; }

    @Override
    public String toString() {
        return String.format("Player %d: (%d,%d) -> (%d,%d)", player, fromRow, fromCol, toRow, toCol);
    }
}
