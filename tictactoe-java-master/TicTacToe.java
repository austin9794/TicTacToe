import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe {
    private JFrame frame;
    private JButton[][] board;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JButton restartButton, quitButton;

    private String currentPlayer = "X";
    private boolean gameOver = false;

    private int xWins = 0;
    private int oWins = 0;
    private int ties = 0;

    private String startingPlayer = "X"; // keeps track of who starts each round

    public TicTacToe() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500); 
        frame.setLayout(new BorderLayout());

        // Title/Status Panel
        statusLabel = new JLabel("X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(statusLabel, BorderLayout.NORTH);

        // Board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        board = new JButton[3][3];

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton("");
                tile.setFont(new Font("Arial", Font.BOLD, 60));
                tile.setFocusPainted(false);
                board[r][c] = tile;

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver || !tile.getText().equals("")) return;
                        tile.setText(currentPlayer);
                        checkWinner();
                        if (!gameOver) {
                            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                            statusLabel.setText(currentPlayer + "'s turn");
                        }
                    }
                });

                boardPanel.add(tile);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        // Bottom Panel (Scoreboard + Buttons)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));

        scoreLabel = new JLabel("X Wins: 0 | O Wins: 0 | Ties: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bottomPanel.add(scoreLabel);

        JPanel buttonPanel = new JPanel();
        restartButton = new JButton("Restart");
        quitButton = new JButton("Quit");

        restartButton.addActionListener(e -> resetBoard());
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(restartButton);
        buttonPanel.add(quitButton);

        bottomPanel.add(buttonPanel);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

private void checkWinner() {
    // Rows & Columns
    for (int i = 0; i < 3; i++) {
        if (!board[i][0].getText().isEmpty() &&
            board[i][0].getText().equals(board[i][1].getText()) &&
            board[i][1].getText().equals(board[i][2].getText())) {
            setWinner(board[i][0].getText());
            highlightWinningLine(board[i][0], board[i][1], board[i][2]);
            return;
        }
        // Winners
        if (!board[0][i].getText().isEmpty() &&
            board[0][i].getText().equals(board[1][i].getText()) &&
            board[1][i].getText().equals(board[2][i].getText())) {
            setWinner(board[0][i].getText());
            highlightWinningLine(board[0][i], board[1][i], board[2][i]);
            return;
        }
    }

    // Diagonals
    if (!board[0][0].getText().isEmpty() &&
        board[0][0].getText().equals(board[1][1].getText()) &&
        board[1][1].getText().equals(board[2][2].getText())) {
        setWinner(board[0][0].getText());
        highlightWinningLine(board[0][0], board[1][1], board[2][2]);
        return;
    }
    // Winners
    if (!board[0][2].getText().isEmpty() &&
        board[0][2].getText().equals(board[1][1].getText()) &&
        board[1][1].getText().equals(board[2][0].getText())) {
        setWinner(board[0][2].getText());
        highlightWinningLine(board[0][2], board[1][1], board[2][0]);
        return;
    }

    // Tie
    boolean full = true;
    for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
            if (board[r][c].getText().isEmpty()) full = false;
        }
    }
    if (full) {
        statusLabel.setText("It's a tie!");
        ties++;
        updateScoreboard();
        highlightTie(); 
        gameOver = true;
    }
}

private void highlightTie() {
    for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
            board[r][c].setBackground(Color.YELLOW);
        }
    }
}


    private void setWinner(String winner) {
        statusLabel.setText(winner + " wins!");
        if (winner.equals("X")) {
            xWins++;
        } else {
            oWins++;
        }
        updateScoreboard();
        gameOver = true;
    }

    private void highlightWinningLine(JButton... tiles) {
        for (JButton tile : tiles) {
            tile.setBackground(Color.GREEN);
        }
    }
    // Reset
    private void resetBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(null);
            }
        }
        gameOver = false;

        // Alternate starting player
        startingPlayer = startingPlayer.equals("X") ? "O" : "X";
        currentPlayer = startingPlayer;

        statusLabel.setText(currentPlayer + "'s turn");
    }

    private void updateScoreboard() {
        scoreLabel.setText("X Wins: " + xWins + " | O Wins: " + oWins + " | Ties: " + ties);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}