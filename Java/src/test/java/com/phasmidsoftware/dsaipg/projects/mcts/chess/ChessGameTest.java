package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChessGameTest {

    private ChessGame game;

    @Before
    public void setUp() {
        game = new ChessGame();
    }

    @Test
    public void testStartReturnsValidState() {
        ChessState state = (ChessState) game.start();
        assertNotNull(state);
        assertEquals(0, state.player()); // white starts
    }

    @Test
    public void testWinnerInitiallyEmpty() {
        ChessState state = (ChessState) game.start();
        assertFalse(state.winner().isPresent());
    }

    @Test
    public void testNextStateSwitchesPlayer() {
        ChessState state = (ChessState) game.start();
        assertEquals(0, state.player());

        // Choose a valid move
        ChessMove move = (ChessMove) state.moves(0).iterator().next();

        ChessState next = (ChessState) state.next(move);
        assertEquals(1, next.player());
    }

    @Test
    public void testMovesNotNull() {
        ChessState state = (ChessState) game.start();
        assertNotNull(state.moves(0));
    }

    @Test
    public void testGameToString() {
        ChessState state = (ChessState) game.start();
        assertNotEquals("", state.toString());
    }
}
