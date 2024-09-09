package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

public class Loteria extends Stage {

    private HBox hBoxButtons;
    private VBox vBoxTablilla;
    private Button btnAnterior, btnSiguiente;
    private GridPane gdpTabla;
    private Scene escena;

    public Loteria(){
        CreateUI();
        this.setTitle("Mi lotería");
        this.setScene(escena);
        this.show();
    }

    private void CreateUI(){
        ImageView imageViewAnterior, imageViewSiguiente;

        // Cargar imágenes
        URL anteriorUrl = getClass().getResource("/images/prev icon.png");
        URL siguienteUrl = getClass().getResource("/images/next icon.png");

        if (anteriorUrl == null) {
            System.out.println("No se pudo encontrar la imagen prev icon.png");
        }
        if (siguienteUrl == null) {
            System.out.println("No se pudo encontrar la imagen next icon.png");
        }

        // Crear ImageView solo si las imágenes se cargaron correctamente
        imageViewAnterior = (anteriorUrl != null) ? new ImageView(new Image(anteriorUrl.toString())) : new ImageView();
        imageViewSiguiente = (siguienteUrl != null) ? new ImageView(new Image(siguienteUrl.toString())) : new ImageView();

        // Ajustar tamaño de las imágenes
        imageViewAnterior.setFitWidth(32);
        imageViewAnterior.setFitHeight(32);
        imageViewSiguiente.setFitWidth(32);
        imageViewSiguiente.setFitHeight(32);

        // Crear botones y asignar imágenes
        btnAnterior = new Button();
        btnAnterior.setGraphic(imageViewAnterior);
        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imageViewSiguiente);

        // Crear layout
        hBoxButtons = new HBox(10, btnAnterior, btnSiguiente);
        gdpTabla = new GridPane();
        vBoxTablilla = new VBox(10, gdpTabla, hBoxButtons);

        // Crear escena
        VBox root = new VBox(vBoxTablilla);
        escena = new Scene(root, 400, 300);
    }
}
