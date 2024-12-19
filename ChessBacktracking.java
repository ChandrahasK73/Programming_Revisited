import java.util.*;

public class ChessBacktracking {

    // Function to print the chessboard
    private static void printBoard(int[][] board, int solutionNumber) {
        System.out.println("Solution " + solutionNumber + ":");
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((cell == 1 ? "Q " : ". "));
            }
            System.out.println();
        }
        System.out.println("====================");
    }

    // Function to check if a queen can be placed at board[row][col]
    private static boolean isSafe(int[][] board, int row, int col, int n) {
        // Check this row on the left
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Check upper diagonal on the left
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Check lower diagonal on the left
        for (int i = row, j = col; i < n && j >= 0; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    // Backtracking function to solve the N-Queens problem
    private static boolean solveNQueens(int[][] board, int col, int n, int[] solutionCounter) {
        // Base case: If all queens are placed
        if (col >= n) {
            solutionCounter[0]++;
            printBoard(board, solutionCounter[0]);
            return true;
        }

        boolean result = false; // To check if at least one solution exists

        // Try placing a queen in all rows for this column
        for (int i = 0; i < n; i++) {
            if (isSafe(board, i, col, n)) {
                // Place the queen
                board[i][col] = 1;

                // Recur to place the rest of the queens
                result = solveNQueens(board, col + 1, n, solutionCounter) || result;

                // Backtrack: Remove the queen
                board[i][col] = 0;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int n = 8; // Size of the chessboard (N x N)
        int[][] board = new int[n][n];
        int[] solutionCounter = {0}; // To count the number of solutions

        System.out.println("Solutions for " + n + "-Queens problem:");

        long startTime = System.currentTimeMillis();
        if (!solveNQueens(board, 0, n, solutionCounter)) {
            System.out.println("No solution exists.");
        } else {
            System.out.println("Total solutions found: " + solutionCounter[0]);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }
}
