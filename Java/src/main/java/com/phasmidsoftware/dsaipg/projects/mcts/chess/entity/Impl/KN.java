package com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Impl;

import com.phasmidsoftware.dsaipg.projects.mcts.chess.ChessBoard;
import com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Chess;


public class KN extends Chess {
	// Constructor to initialize the Knight with name and faction
	public KN(String name, Integer faction) {
		super(name, faction);
	}

	// Implementation of the move method for the Knight
	@Override
	public void move() {

		// Direction vectors for the Bishop's possible moves (diagonal directions)
		int[][] dirs={{2,1},{1,2},{-2,1},{-1,2},{2,-1},{1,-2},{-2,-1},{-1,-2}};
		multiMove(dirs,false);

	}
}
