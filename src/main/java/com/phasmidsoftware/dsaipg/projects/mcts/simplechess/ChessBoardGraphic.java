package com.phasmidsoftware.dsaipg.projects.mcts.simplechess;

import javax.swing.*;
import java.awt.*;

public class ChessBoardGraphic extends JFrame {
    public static int ROWS = 8;
    public static int COLS = 8;
    public static int LENGTH = 100;
    public static Chess[][] chessBoard;

    static {
        chessBoard = ChessBoard.initial();
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                System.out.println(chessBoard[i][j]);
            }
        }
    }

    public ChessBoardGraphic() {
        this.setTitle("Chess");
        this.setSize(800, 800);
        this.setLayout(new GridLayout(ROWS, COLS, 0, 0));
        this.setVisible(true);
        this.setResizable(false);
        rendering();
    }

    private void handleButtonClick(Chess chess) {
        int selectedCol = ChessBoard.selectedCol;
        int selectedRow = ChessBoard.selectedRow;

        if (chess != null) {
            int x = chess.getCol();
            int y = chess.getRow();

            if (x != selectedCol || y != selectedRow) {
                ChessBoard.selectedCol = x;
                ChessBoard.selectedRow = y;
                ChessBoard.resetSelectedBoard();
                chess.move();
                rendering();
            } else {
                ChessBoard.resetSelectedBoard();
                ChessBoard.selectedCol = -1;
                ChessBoard.selectedRow = -1;
                rendering();
            }

            System.out.println("x= " + x + " y= " + y + " selectedCol= " + ChessBoard.selectedCol + " selectedRow= " + ChessBoard.selectedRow);
        } else {
            ChessBoard.resetSelectedBoard();
            rendering();
            openSecondWindow();
        }
    }

    private void openSecondWindow() {
        JFrame secondWindow = new JFrame("Legal Moves for All Chess");
        secondWindow.setSize(800, 600);
        secondWindow.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setText("The king moves one square in any direction. ..."); // 省略长文说明
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setBackground(Color.BLUE);
        textArea.setForeground(Color.GREEN);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        secondWindow.add(scrollPane, BorderLayout.CENTER);
        secondWindow.setVisible(true);
    }

    public void rendering() {
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Chess chess = ChessBoard.board[i][j];
                JButton button = new JButton(chess != null ? chess.getName() : "");
                button.addActionListener(e -> handleButtonClick(chess));

                if (chess != null) {
                    button.setForeground(chess.getFaction() == 1 ? Color.red : Color.black);
                }

                if (ChessBoard.selectedBoard[i][j]) {
                    try {
                        ImageIcon originalIcon = new ImageIcon(getClass().getResource("target.png")); // ✅ 修复路径
                        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                        ImageIcon scaleIcon = new ImageIcon(scaledImage);
                        button.setIcon(scaleIcon);
                        button.setHorizontalAlignment(SwingConstants.CENTER);
                        button.setVerticalAlignment(SwingConstants.CENTER);
                        button.setContentAreaFilled(false);
                        button.setOpaque(true);
                        button.setBackground((i + j) % 2 == 0 ? new Color(250, 234, 218) : new Color(123, 129, 125));
                        button.setHorizontalTextPosition(SwingConstants.CENTER);
                        button.setVerticalTextPosition(SwingConstants.CENTER);
                    } catch (Exception e) {
                        System.err.println("❌ Failed to load target.png: " + e.getMessage());
                    }
                }

                button.setFont(new Font("Arial", Font.PLAIN, 30));
                button.setSize(LENGTH, LENGTH);
                button.setLocation(i * LENGTH, j * LENGTH);
                button.setBackground((i + j) % 2 == 0 ? new Color(250, 234, 218) : new Color(123, 129, 125));
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.black));

                this.add(button);
            }
        }

        this.setVisible(true);
        System.out.println("render");
    }
}
