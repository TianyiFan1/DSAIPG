package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

public class Q extends Chess {
	// Constructor to initialize the Queen with name and faction
	public Q(String name, Integer faction) {
		super(name, faction);
	}

	// Implementation of the move method for the Queen
	@Override
	public void move() {

		// Direction vectors for the Queen's possible moves
		int[][] dirs = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}, {-1, 0}, {1, 0}};

		// Use the multiMove method to handle multiple possible moves based on direction vectors
		multiMove(dirs);
	}
}
