package com.example.tap2024b;

import com.example.tap2024b.vistas.Calculadora;
import com.example.tap2024b.vistas.Loteria;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private MenuBar menuBar;
    private Menu menuCompetencias;
    private MenuItem menuItemCalculadora;
    private MenuItem menuItemLoteria;
    private VBox vBox;

    public void CreateUI() {
        // Crear barra de menú
        menuBar = new MenuBar();

        // Crear menú "Competencias"
        menuCompetencias = new Menu("Competencias");

        // Crear item "Calculadora" dentro del menú "Competencias"
        menuItemCalculadora = new MenuItem("Calculadora");

        // Crear item "Loteria" dentro del menú "Competencias"
        menuItemLoteria = new MenuItem("Loteria");

        // Añadir acción para abrir la calculadora
        menuItemCalculadora.setOnAction(event -> {
            try {
                Calculadora calculadora = new Calculadora();
                Stage calculadoraStage = new Stage();
                calculadora.show(); // Inicia la ventana de la calculadora
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Añadir acción para abrir la Loteria
        menuItemLoteria.setOnAction(event -> {
            try {
                Loteria loteria = new Loteria();
                Stage loteriaStage = new Stage();
                loteria.show(); // Inicia la ventana de la Loteria
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Añadir los ítems al menú "Competencias"
        menuCompetencias.getItems().addAll(menuItemCalculadora, menuItemLoteria);
        menuBar.getMenus().add(menuCompetencias);

        // Crear layout principal
        vBox = new VBox(menuBar);

        // Cargar la imagen de fondo
        Image backgroundImage = new Image("C:\\Users\\cisne\\IdeaProjects\\TAP2024b\\src\\main\\resources\\images\\fondo.jpg"); // Ruta de la imagen
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );

        // Asignar la imagen de fondo al VBox
        vBox.setBackground(new Background(background));
    }

    @Override
    public void start(Stage stage) {
        CreateUI();
        Scene scene = new Scene(vBox, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
