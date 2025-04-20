package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import com.phasmidsoftware.dsaipg.projects.mcts.chess.entity.Chess;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;
import com.phasmidsoftware.dsaipg.projects.mcts.core.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Collection;

public class ChessState implements State<ChessGame> {

    private final Chess[][] board;
    private final int currentPlayer; 

    public ChessState(Chess[][] board, int currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public ChessGame game() {
        return new ChessGame();
    }

    @Override
    public int player() {
        return currentPlayer;
    }

    @Override
    public Random random() {
        return new Random(); 
    }

    @Override
    public Collection<Move<ChessGame>> moves(int player) {
        List<Move<ChessGame>> moves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Chess piece = board[i][j];
                if (piece != null) {
        
                    if ((player == 0 && piece.getFaction() == 1) ||
                            (player == 1 && piece.getFaction() == -1)) {

                        piece.move(); 
                        moves.addAll(piece.getLegalMoves());
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public State<ChessGame> next(Move<ChessGame> move) {
        ChessMove cm = (ChessMove) move;
        Chess[][] nextBoard = copyBoard(board);
        nextBoard[cm.toX][cm.toY] = nextBoard[cm.fromX][cm.fromY];
        nextBoard[cm.fromX][cm.fromY] = null;

        return new ChessState(nextBoard, 1 - currentPlayer);
    }

    @Override
    public Optional<Integer> winner() {
        boolean hasWhiteKing = false;
        boolean hasBlackKing = false;

        for (Chess[] row : board) {
            for (Chess c : row) {
                if (c != null && c.getName().equals("K")) {
                    if (c.getFaction() == 1) hasWhiteKing = true;
                    else if (c.getFaction() == -1) hasBlackKing = true;
                }
            }
        }

        if (!hasWhiteKing) return Optional.of(1); 
        if (!hasBlackKing) return Optional.of(0); 
        return Optional.empty(); 
    }

    @Override
    public boolean isTerminal() {
        return winner().isPresent();
    }

    private Chess[][] copyBoard(Chess[][] board) {
        Chess[][] newBoard = new Chess[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        return newBoard;
    }

    @Override
    public String toString() {
        return "[ChessState, currentPlayer=" + currentPlayer + "]";
    }
}
