package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Button[][] arrBtns;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vBox;
    private Scene escena;
    private String[] strTeclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "0", ".", "=", "-"};
    private Button btnClear;

    private void CreateUI() {
        arrBtns = new Button[4][4];
        txtPantalla = new TextField("0");
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        gdpTeclado = new GridPane();
        CrearTeclado();
        btnClear = new Button("CE");
        btnClear.setOnAction(event -> txtPantalla.setText("0"));
        vBox = new VBox(txtPantalla, gdpTeclado, btnClear);
        btnClear.setId("font-button");
        escena = new Scene(vBox, 300, 300);
        escena.getStylesheets().add(getClass().getResource("/styles/cal.css").toString());
    }

    private void CrearTeclado() {
        for (int i = 0; i < arrBtns.length; i++) {
            for (int j = 0; j < arrBtns.length; j++) {
                arrBtns[j][i] = new Button(strTeclas[4 * i + j]);
                arrBtns[j][i].setPrefSize(75, 75);
                String tecla = strTeclas[4 * i + j];
                arrBtns[j][i].setOnAction(event -> DetectarTecla(tecla));
                gdpTeclado.add(arrBtns[j][i], j, i);
            }
        }
    }

    private void DetectarTecla(String tecla) {
        if ("0123456789.".contains(tecla)) {
            if (txtPantalla.getText().equals("0") || txtPantalla.getText().equals("Error")) {
                txtPantalla.setText(tecla);
            } else {
                txtPantalla.appendText(tecla);
            }
        } else if ("+-*/".contains(tecla)) {
            String text = txtPantalla.getText();
            if (!text.endsWith(" ") && !text.equals("Error")) {
                txtPantalla.appendText(" " + tecla + " ");
            }
        } else if ("=".equals(tecla)) {
            try {
                String resultado = evaluarExpresion(txtPantalla.getText());
                txtPantalla.setText(resultado);
            } catch (NumberFormatException e) {
                txtPantalla.setText("Error");
            } catch (ArithmeticException e) {
                txtPantalla.setText("Error");
            }
        }
    }

    private String evaluarExpresion(String expresion) {
        String[] tokens = expresion.split(" ");
        if (tokens.length < 3) return expresion; // Not a valid expression

        double num1 = Double.parseDouble(tokens[0]);
        double num2 = Double.parseDouble(tokens[2]);
        String operador = tokens[1];

        double resultado = 0;
        switch (operador) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "*":
                resultado = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    resultado = num1 / num2;
                } else {
                    throw new ArithmeticException("División por cero");
                }
                break;
            default:
                throw new IllegalArgumentException("Operador no soportado");
        }

        return String.valueOf(resultado);
    }

    public Calculadora() {
        CreateUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }
}
