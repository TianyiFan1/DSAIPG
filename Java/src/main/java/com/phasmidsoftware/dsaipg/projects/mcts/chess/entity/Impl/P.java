package com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Impl;

import com.phasmidsoftware.dsaipg.projects.mcts.chess.ChessBoard;
import com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Chess;


public class P extends Chess {
	// Constructor to initialize the Pawn with name and faction
	public P(String name, Integer faction) {
		super(name, faction);
	}

	// Implementation of the move method for the Pawn
	@Override
	public void move() {


		int row = getRow();
		int col = getCol();

		int maxRow = ChessBoard.getROWS();
		int maxCol = ChessBoard.getCOLS();

		// Direction vectors for the Bishop's possible moves (diagonal directions)
		int[][] dirs={{getFaction(), 0},{0,0},{0,0}};

		//forward
		if(!isValid(row+getFaction(),col) || !isEmpty(row+getFaction(),col)){
			dirs[0] = new int[]{0,0};
		}
		if(isValid(row+getFaction(),col+1) && isEnemy(row+getFaction(),col+1)){
			dirs[1]= new int[]{getFaction(), 1};
		}
		if(isValid(row+getFaction(),col-1) && isEnemy(row+getFaction(),col-1)){
			dirs[2]= new int[]{getFaction(), -1};
		}
		multiMove(dirs,false);

	}
}
