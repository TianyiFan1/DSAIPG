package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

// Abstract class representing a chess piece with common attributes and methods
public abstract class Chess implements Movable {
    protected String name;
    protected Integer faction;
    protected Integer col;
    protected Integer row;

    public final static int ENERMY = -1;
    public final static int ALLY = 1;
    public final static int EMPTY = 0;
    public final static int OUT = -2;

    public abstract void move();

    public int BaseMove(int row, int col) {
        if (!isValid(row, col)) return OUT;
        if (isEmpty(row, col)) {
            ChessBoard.selectedBoard[row][col] = true;
            return EMPTY;
        }
        if (isEnemy(row, col)) {
            ChessBoard.selectedBoard[row][col] = true;
            return ENERMY;
        }
        return ALLY;
    }

    public void multiMove(int[][] dirs) {
        multiMove(dirs, true);
    }

    public void multiMove(int[][] dirs, boolean ifContinue) {
        for (int[] dir : dirs) {
            int i = dir[0];
            int j = dir[1];
            int n = 1;
            do {
                if (!isValid(this.row + n * i, this.col + n * j)) break;
                int result = BaseMove(this.row + n * i, this.col + n * j);
                if (result == ALLY || result == ENERMY || result == OUT) break;
                n++;
            } while (ifContinue);
        }
    }

    public Chess() {
    }

    public Chess(String name) {
        this.name = name;
    }

    public Chess(String name, Integer faction) {
        this.name = name;
        this.faction = faction;
    }

    public Chess(String name, Integer faction, Integer row, Integer col) {
        this.name = name;
        this.faction = faction;
        this.col = col;
        this.row = row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFaction() {
        return faction;
    }

    public void setFaction(Integer faction) {
        this.faction = faction;
    }

    public Integer getCol() {
        return col;
    }

    public Chess setCol(Integer col) {
        this.col = col;
        return this;
    }

    public Integer getRow() {
        return row;
    }

    public Chess setRow(Integer row) {
        this.row = row;
        return this;
    }

    public boolean isValid(Integer row, Integer col) {
        return row >= 0 && row < ChessBoardGraphic.ROWS && col >= 0 && col < ChessBoardGraphic.COLS;
    }

    public boolean isEmpty(Integer row, Integer col) {
        Chess c = ChessBoard.board[row][col];
        return c == null;
    }

    public boolean isEnemy(Integer row, Integer col) {
        Chess c = ChessBoard.board[row][col];
        return c != null && !c.getFaction().equals(this.getFaction());
    }

    @Override
    public String toString() {
        return "Chess{" +
                "name='" + name + '\'' +
                ", faction=" + faction +
                ", x=" + col +
                ", y=" + row +
                '}';
    }

    /**
     * ✅ Cloning method for MCTS usage — without reflection!
     */
    public Chess cloneChess() {
        Chess clone = null;
        if (this instanceof K) clone = new K(this.name, this.faction);
        else if (this instanceof Q) clone = new Q(this.name, this.faction);
        else if (this instanceof R) clone = new R(this.name, this.faction);
        else if (this instanceof B) clone = new B(this.name, this.faction);
        else if (this instanceof KN) clone = new KN(this.name, this.faction);
        else if (this instanceof P) clone = new P(this.name, this.faction);

        if (clone != null) {
            clone.setRow(this.row);
            clone.setCol(this.col);
        }
        return clone;
    }
}
