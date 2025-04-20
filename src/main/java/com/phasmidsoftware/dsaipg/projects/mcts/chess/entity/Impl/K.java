package com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Impl;

import com.phasmidsoftware.dsaipg.projects.mcts.chess.ChessBoard;
import com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Chess;

public class K extends Chess {
	// Constructor to initialize the King with name and faction
	public K(String name, Integer faction) {
		super(name, faction);
	}

	// Implementation of the move method for the King
	@Override
	public void move() {
		// Direction vectors for the King's possible moves
		int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

		// Use the multiMove method to handle multiple possible moves based on direction vectors
		multiMove(dirs, false);
	}
}
