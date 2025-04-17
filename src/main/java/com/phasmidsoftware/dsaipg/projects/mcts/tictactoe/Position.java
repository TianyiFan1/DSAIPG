/*
 * Copyright (c) 2024. Robin Hillyard
 */

package com.phasmidsoftware.dsaipg.projects.mcts.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the board of the Tic-tac-toe game.
 * It is a 3x3 matrix of 0s, 1s, and -1s for O, X, and . respectively.
 */
public class Position {

    static Position parsePosition(final String grid, final int last) {
        int[][] matrix = new int[gridSize][gridSize];
        int count = 0;
        String[] rows = grid.split("\\n", gridSize);
        for (int i = 0; i < gridSize; i++) {
            String[] cells = rows[i].split(" ", gridSize);
            for (int j = 0; j < gridSize; j++) {
                int cell = parseCell(cells[j].trim());
                if (cell >= 0) count++;
                matrix[i][j] = cell;
            }
        }
        return new Position(matrix, count, last);
    }

    static int parseCell(String cell) {
        return switch (cell.toUpperCase()) {
            case "O", "0" -> 0;
            case "X", "1" -> 1;
            default -> -1;
        };
    }

    public Position move(int player, int x, int y) {
        if (full()) throw new RuntimeException("Position is full");
        if (player == last) throw new RuntimeException("consecutive moves by same player: " + player);
        int[][] matrix = copyGrid();
        if (matrix[x][y] < 0) {
            matrix[x][y] = player;
            return new Position(matrix, count + 1, player);
        }
        throw new RuntimeException("Position is occupied: " + x + ", " + y);
    }

    public List<int[]> moves(int player) {
        if (player == last) throw new RuntimeException("consecutive moves by same player: " + player);
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if (grid[i][j] < 0)
                    result.add(new int[]{i, j});
        return result;
    }

    public Position reflect(int axis) {
        int[][] matrix = copyGrid();
        switch (axis) {
            case 0:
                for (int j = 0; j < gridSize; j++) swap(matrix, 0, j, 2, j);
                break;
            case 1:
                for (int i = 0; i < gridSize; i++) swap(matrix, i, 0, i, 2);
                break;
            default:
                throw new RuntimeException("reflect not implemented for " + axis);
        }
        return new Position(matrix, count, last);
    }

    public Position rotate() {
        int[][] matrix = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                matrix[i][j] = grid[j][gridSize - i - 1];
        return new Position(matrix, count, last);
    }

    public Optional<Integer> winner() {
        if (count > 4 && threeInARow()) return Optional.of(last);
        return Optional.empty();
    }

    boolean threeInARow() {
        for (int i = 0; i < 3; i++) {
            if (Arrays.equals(projectRow(i), xxx)) return true;
            if (Arrays.equals(projectCol(i), xxx)) return true;
        }
        if (Arrays.equals(projectDiag(true), xxx)) return true;
        if (Arrays.equals(projectDiag(false), xxx)) return true;
        return false;
    }

    int[] projectRow(int i) {
        return grid[i];
    }

    int[] projectCol(int j) {
        int[] result = new int[gridSize];
        for (int i = 0; i < gridSize; i++)
            result[i] = grid[i][j];
        return result;
    }

    int[] projectDiag(boolean b) {
        int[] result = new int[gridSize];
        for (int j = 0; j < gridSize; j++) {
            int i = b ? j : gridSize - j - 1;
            result[j] = grid[i][j];
        }
        return result;
    }

    boolean full() {
        return count == 9;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                sb.append(render(grid[i][j]));
                if (j < gridSize - 1) sb.append(' ');
            }
            if (i < gridSize - 1) sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                sb.append(grid[i][j]);
                if (j < gridSize - 1) sb.append(',');
            }
            if (i < gridSize - 1) sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return Arrays.deepEquals(grid, position.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    Position(int[][] grid, int count, int last) {
        this.grid = grid;
        this.count = count;
        this.last = last;
        xxx = new int[]{last, last, last};
    }

    private int[][] copyGrid() {
        int[][] result = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++)
            result[i] = Arrays.copyOf(grid[i], gridSize);
        return result;
    }

    private char render(int x) {
        return switch (x) {
            case 0 -> 'O';
            case 1 -> 'X';
            default -> '.';
        };
    }

    private void swap(int[][] matrix, int i1, int j1, int i2, int j2) {
        int temp = matrix[i1][j1];
        matrix[i1][j1] = matrix[i2][j2];
        matrix[i2][j2] = temp;
    }

    private final int[][] grid;
    final int last;
    private final int count;
    private final static int gridSize = 3;
    private final int[] xxx;
}

