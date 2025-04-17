package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

public class B extends Chess {
	// Constructor to initialize the Bishop with name and faction
	public B(String name, Integer faction) {
		super(name, faction);
	}

	// Implementation of the move method for the Bishop
	@Override
	public void move() {
		// Direction vectors for the Bishop's possible moves (diagonal directions)
		int[][] dirs = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

		// Use the multiMove method to handle multiple possible moves based on direction vectors
		multiMove(dirs);
	}
}
