package com.phasmidsoftware.dsaipg.projects.mcts.chess;


import com.phasmidsoftware.dsaipg.projects.mcts.core.Game;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

public class ChessGame implements Game<ChessGame> {

    @Override
    public State<ChessGame> start() {
        ChessBoard.initial();
        return new ChessState(ChessBoard.board, 0);
    }

    @Override
    public int opener() {
        return 0;
    }
}