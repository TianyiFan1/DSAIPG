package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Game;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

public class SimpleChessGame implements Game<SimpleChessGame> {

    @Override
    public State<SimpleChessGame> start() {
        return new ChessState(); 
    }

    @Override
    public int opener() {
        return 0; 
    }
}
