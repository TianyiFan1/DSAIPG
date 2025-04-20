package com.phasmidsoftware.dsaipg.projects.mcts.chess.entity;

import com.phasmidsoftware.dsaipg.projects.mcts.chess.ChessBoard;
import com.phasmidsoftware.dsaipg.projects.mcts.chess.ChessMove;

import java.util.ArrayList;
import java.util.List;

public abstract class Chess {
	private String name;      // 名称，如 K, Q, P...
	private Integer faction;  // 阵营：1 = 白，-1 = 黑
	private Integer row;      // 行号
	private Integer col;      // 列号

	public final static int ENEMY = -1;
	public final static int ALLY = 1;
	public final static int EMPTY = 0;
	public final static int OUT = -2;

	// 用于 MCTS 存储每一步合法走法
	private final List<ChessMove> legalMoves = new ArrayList<>();

	// 抽象方法：子类实现自己的 move()
	public abstract void move();

	// ✅ 给 State 调用
	public List<ChessMove> getLegalMoves() {
		return legalMoves;
	}

	// ✅ 清空所有可走步（每次调用 move() 时）
	protected void clearMoves() {
		legalMoves.clear();
	}

	// ✅ 尝试走某一步
	public int baseMove(int row, int col) {
		if (!isValid(row, col)) return OUT;
		if (isEmpty(row, col)) {
			ChessMove move = new ChessMove(faction == 1 ? 0 : 1, this.row, this.col, row, col);
			legalMoves.add(move);
			return EMPTY;
		}
		if (isEnemy(row, col)) {
			ChessMove move = new ChessMove(faction == 1 ? 0 : 1, this.row, this.col, row, col);
			legalMoves.add(move);
			return ENEMY;
		}
		return ALLY;
	}

	// ✅ 多步移动（如车/象/后）
	public void multiMove(int[][] dirs) {
		multiMove(dirs, true);
	}

	public void multiMove(int[][] dirs, boolean ifContinue) {
		clearMoves(); // 重置走法
		for (int[] dir : dirs) {
			int i = dir[0];
			int j = dir[1];

			int n = 1;
			while (true) {
				int newRow = this.row + n * i;
				int newCol = this.col + n * j;

				int result = baseMove(newRow, newCol);
				if (result == OUT || result == ALLY || result == ENEMY) break;
				if (!ifContinue) break;
				n++;
			}
		}
	}

	// ✅ 判断是否合法格子
	public boolean isValid(Integer row, Integer col) {
		return row >= 0 && row < ChessBoard.getROWS() && col >= 0 && col < ChessBoard.getCOLS();
	}

	public boolean isEmpty(Integer row, Integer col) {
		Chess chess = ChessBoard.board[row][col];
		return chess == null;
	}

	public boolean isEnemy(Integer row, Integer col) {
		Chess chess = ChessBoard.board[row][col];
		return chess != null && !chess.getFaction().equals(this.getFaction());
	}

	// ✅ 构造函数
	public Chess() {}

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
		this.row = row;
		this.col = col;
	}

	// ✅ Getter/Setter
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

	@Override
	public String toString() {
		return "Chess{" +
				"name='" + name + '\'' +
				", faction=" + faction +
				", row=" + row +
				", col=" + col +
				'}';
	}
}
