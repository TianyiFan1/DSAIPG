package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.Move;
import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

import java.util.*;

public class ChessState implements State<SimpleChessGame> {
    private final Chess[][] board;
    private final int player;
    private final Random random;

    public ChessState(Chess[][] board, int player, Random random) {
        this.board = board;
        this.player = player;
        this.random = random;
    }

    public ChessState() {
        this.board = cloneBoard(ChessBoard.initial());
        this.player = 0;
        this.random = new Random();
    }

    @Override
    public SimpleChessGame game() {
        return new SimpleChessGame();
    }

    @Override
    public boolean isTerminal() {
        return !containsKing(board, 0) || !containsKing(board, 1);
    }

    @Override
    public int player() {
        return player;
    }

    @Override
    public Optional<Integer> winner() {
        if (!containsKing(board, 0)) return Optional.of(1);
        if (!containsKing(board, 1)) return Optional.of(0);
        return Optional.empty(); // draw not handled
    }

    @Override
    public Random random() {
        return random;
    }

    @Override
    public Collection<Move<SimpleChessGame>> moves(int currentPlayer) {
        List<Move<SimpleChessGame>> legalMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Chess piece = board[i][j];
                if (piece != null && piece.getFaction() == currentPlayer) {
                    for (int x = 0; x < board.length; x++) {
                        for (int y = 0; y < board[x].length; y++) {
                            if (isValidMove(piece, i, j, x, y)) {
                                legalMoves.add(new ChessMove(i, j, x, y, currentPlayer));
                            }
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public State<SimpleChessGame> next(Move<SimpleChessGame> move) {
        ChessMove m = (ChessMove) move;
        Chess[][] newBoard = cloneBoard(board);
        Chess movingPiece = newBoard[m.getFromRow()][m.getFromCol()];
        newBoard[m.getToRow()][m.getToCol()] = movingPiece.setRow(m.getToRow()).setCol(m.getToCol());
        newBoard[m.getFromRow()][m.getFromCol()] = null;
        return new ChessState(newBoard, 1 - player, random);
    }

    private boolean containsKing(Chess[][] board, int faction) {
        for (Chess[] row : board)
            for (Chess c : row)
                if (c instanceof K && c.getFaction() == faction)
                    return true;
        return false;
    }

    private boolean isValidMove(Chess piece, int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow == toRow && fromCol == toCol) return false;
        if (toRow < 0 || toRow >= board.length || toCol < 0 || toCol >= board[0].length) return false;

        Chess target = board[toRow][toCol];
        if (target != null && target.getFaction().equals(piece.getFaction())) return false;

        return true;
    }

    private Chess[][] cloneBoard(Chess[][] original) {
        Chess[][] copy = new Chess[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                Chess c = original[i][j];
                copy[i][j] = (c == null) ? null : c.cloneChess();
            }
        }
        return copy;
    }

    @Override
    public String toString() {
        return "ChessState{" + "player=" + player + ", board=" + Arrays.deepToString(board) + '}';
    }
}

