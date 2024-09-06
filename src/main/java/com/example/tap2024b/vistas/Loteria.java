package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Loteria extends Stage {

    private HBox hBoxMain, hBoxButtons;
    private VBox vBoxTablilla, vBoxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTiempo;
    private GridPane gdpTabla;
    private ImageView imageViewMazo;
    private Scene escena;

    public Loteria(){
        CreateUI();
        this.setTitle("Mi loteria!!!!");
        this.setScene(escena);
        this.show();
    }

    private void CreateUI(){
        ImageView imageViewAnterior, imageViewSiguiente;
        imageViewAnterior = new ImageView(new Image(getClass().getResource("/images/prev icon.png").toString()));
        imageViewSiguiente = new ImageView( new Image(getClass().getResource("/images/next icon.png").toString()));

        gdpTabla = new GridPane();
        btnAnterior= new Button();
        btnAnterior.setGraphic(imageViewAnterior);
        btnSiguiente =  new Button();
        btnSiguiente.setGraphic(imageViewSiguiente);
        hBoxButtons = new HBox(btnAnterior,btnSiguiente);
        vBoxTablilla = new VBox(gdpTabla, hBoxButtons);
    }







}
