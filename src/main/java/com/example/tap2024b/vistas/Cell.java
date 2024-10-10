package com.example.tap2024b.vistas;

public class Cell {
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int neighboringMines;

    public Cell() {
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.neighboringMines = 0;
    }

    // Getters and setters
    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        this.isRevealed = true;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void flag() {
        this.isFlagged = !this.isFlagged;
    }

    public int getNeighboringMines() {
        return neighboringMines;
    }

    public void setNeighboringMines(int neighboringMines) {
        this.neighboringMines = neighboringMines;
    }
}
