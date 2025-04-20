package com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Impl;

import com.phasmidsoftware.dsaipg.projects.mcts.chess.ChessBoard;
import com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Chess;


public class R extends Chess {
	// Constructor to initialize the Rook with name and faction
	public R(String name, Integer faction) {
		super(name, faction);
	}

	// Implementation of the move method for the Rook
	@Override
	public void move() {

		// Direction vectors for the Bishop's possible moves (diagonal directions)
		int[][] dirs={{0,1},{0,-1},{1,0},{-1,0}};
		multiMove(dirs);

	}
}
