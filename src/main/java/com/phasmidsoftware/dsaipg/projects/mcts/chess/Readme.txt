This is a simplified version of the chess game:

At the beginning of the game, the system will randomly distribute the chess pieces on the 8×8 chessboard;

Both sides take turns to make moves, and the white side goes first (faction = 1);

Each move is made by a legal chess piece (belonging to the current player);

The game ends in any of the following situations:

One side's king is captured (the other side wins)

No more moves can be made (theoretically draw, but the current version ends directly)

Rules reference: Wikipedia: Rules of chess.

How to simulate a chess game
Run class:

com.phasmidsoftware.dsaipg.projects.mcts.chess.ChessAutoPlay
This program will simulate a complete chess game using random legal moves, and output the board state and final result of each step in the console.

If you are using Eclipse, just:

Find ChessAutoPlay.java

Right click → Run As → Java Application

to start the simulation.