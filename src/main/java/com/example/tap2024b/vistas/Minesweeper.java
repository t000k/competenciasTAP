package com.example.tap2024b.vistas;

public class Minesweeper {
    private Cell[][] grid;
    private int rows;
    private int cols;
    private int mines;

    public Minesweeper(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.grid = new Cell[rows][cols];
        initializeGrid();
        placeMines();
        calculateNeighboringMines();
    }

    private void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < mines) {
            int randRow = (int) (Math.random() * rows);
            int randCol = (int) (Math.random() * cols);
            if (!grid[randRow][randCol].isMine()) {
                grid[randRow][randCol].setMine(true);
                minesPlaced++;
            }
        }
    }

    private void calculateNeighboringMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!grid[i][j].isMine()) {
                    grid[i][j].setNeighboringMines(countMines(i, j));
                }
            }
        }
    }

    private int countMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (grid[newRow][newCol].isMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void revealCell(int row, int col) {
        if (!grid[row][col].isRevealed()) {
            grid[row][col].reveal();
            if (grid[row][col].getNeighboringMines() == 0 && !grid[row][col].isMine()) {
                revealNeighbors(row, col);
            }
        }
    }

    private void revealNeighbors(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (!grid[newRow][newCol].isRevealed()) {
                        revealCell(newRow, newCol);
                    }
                }
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
