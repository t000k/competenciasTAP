package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class MinesweeperApp {

    private final int rows = 10;  // Número de filas del tablero
    private final int cols = 10;  // Número de columnas del tablero
    private final int totalMines = 10;  // Número total de minas
    private Button[][] buttons;   // Matriz de botones que representarán el tablero
    private int[][] minesBoard;   // Matriz que almacena las minas y los números
    private boolean[][] revealed; // Matriz para saber si una casilla ha sido descubierta
    private boolean gameOver;     // Indica si el juego ha terminado

    public void show() {
        // Crear una nueva ventana para el juego
        Stage stage = new Stage();
        stage.setTitle("Minesweeper");

        // Crear el layout principal del juego (GridPane)
        GridPane gridPane = new GridPane();

        // Inicializar las matrices
        buttons = new Button[rows][cols];
        minesBoard = new int[rows][cols];
        revealed = new boolean[rows][cols];

        // Inicializar el estado del juego
        gameOver = false;

        // Generar minas y números
        generateMines();
        calculateNumbers();

        // Rellenar el GridPane con botones
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button button = new Button();
                button.setPrefSize(40, 40); // Establecer el tamaño de los botones
                buttons[row][col] = button;

                // Crear variables finales para usarlas dentro de la expresión lambda
                final int currentRow = row;
                final int currentCol = col;

                // Añadir la lógica para manejar clics en los botones
                button.setOnAction(event -> handleButtonClick(currentRow, currentCol));

                // Añadir el botón al GridPane
                gridPane.add(button, col, row);
            }
        }

        // Crear la escena y asignarla al escenario
        Scene scene = new Scene(gridPane, 400, 400);
        stage.setScene(scene);

        // Mostrar la ventana
        stage.show();
    }

    // Método para generar minas en posiciones aleatorias
    private void generateMines() {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            if (minesBoard[row][col] != -1) { // -1 representa una mina
                minesBoard[row][col] = -1;
                minesPlaced++;
            }
        }
    }

    // Método para calcular los números que representan minas adyacentes
    private void calculateNumbers() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (minesBoard[row][col] == -1) {
                    // No calcular números para las minas
                    continue;
                }

                // Contar las minas adyacentes
                int minesCount = 0;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int newRow = row + i;
                        int newCol = col + j;

                        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && minesBoard[newRow][newCol] == -1) {
                            minesCount++;
                        }
                    }
                }

                minesBoard[row][col] = minesCount;
            }
        }
    }

    // Método para manejar clics en los botones del tablero
    private void handleButtonClick(int row, int col) {
        if (gameOver || revealed[row][col]) {
            return; // No hacer nada si el juego ha terminado o si la casilla ya fue descubierta
        }

        revealed[row][col] = true;

        if (minesBoard[row][col] == -1) {
            // El jugador ha hecho clic en una mina, termina el juego
            buttons[row][col].setText("💣");
            buttons[row][col].setStyle("-fx-background-color: red;");
            gameOver = true;
            revealAllMines();
            System.out.println("¡Has perdido!");
        } else {
            // Mostrar el número de minas adyacentes
            buttons[row][col].setText(String.valueOf(minesBoard[row][col]));

            // Si el número es 0, descubrir las casillas adyacentes
            if (minesBoard[row][col] == 0) {
                buttons[row][col].setText("");
                revealAdjacentCells(row, col);
            }

            // Verificar si el jugador ha ganado
            checkWin();
        }
    }

    // Método para descubrir las casillas adyacentes si no hay minas alrededor
    private void revealAdjacentCells(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !revealed[newRow][newCol]) {
                    handleButtonClick(newRow, newCol); // Descubrir la casilla adyacente
                }
            }
        }
    }

    // Método para mostrar todas las minas cuando el jugador pierde
    private void revealAllMines() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (minesBoard[row][col] == -1) {
                    buttons[row][col].setText("💣");
                }
            }
        }
    }

    // Método para verificar si el jugador ha ganado
    private void checkWin() {
        boolean won = true;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (minesBoard[row][col] != -1 && !revealed[row][col]) {
                    won = false;
                    break;
                }
            }
        }

        if (won) {
            gameOver = true;
            System.out.println("¡Has ganado!");
        }
    }
}
