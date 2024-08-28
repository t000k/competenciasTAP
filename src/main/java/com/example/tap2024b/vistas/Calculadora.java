package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Button[] arrBtns;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vBox;
    private Scene scene;

    private void CreateUI(){
        arrBtns = new Button[16];
        txtPantalla = new TextField("0");
        gdpTeclado = new GridPane();
        vBox = new VBox();
        scene = new Scene();
    }

    public Calculadora(){
        this.setTitle("Calculadora");
        this.setScene();
        this.show();
    }
}
