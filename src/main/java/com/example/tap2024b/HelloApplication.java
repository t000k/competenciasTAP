package com.example.tap2024b;

import com.example.tap2024b.vistas.Calculadora;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private MenuBar menuBar;
    private Menu menuCompetencias;
    private MenuItem menuItemCalculadora;
    private VBox vBox;

    public void CreateUI() {
        // Crear barra de menú
        menuBar = new MenuBar();

        // Crear menú "Competencias"
        menuCompetencias = new Menu("Competencias");

        // Crear item "Calculadora" dentro del menú "Competencias"
        menuItemCalculadora = new MenuItem("Calculadora");

        // Añadir acción para abrir la calculadora
        menuItemCalculadora.setOnAction(event -> {
            // Abrir la clase Calculadora
            try {
                Calculadora calculadora = new Calculadora();
                Stage calculadoraStage = new Stage();
                calculadora.show(); // Inicia la ventana de la calculadora
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Añadir el item "Calculadora" al menú "Competencias"
        menuCompetencias.getItems().add(menuItemCalculadora);

        // Añadir el menú "Competencias" a la barra de menú
        menuBar.getMenus().add(menuCompetencias);

        // Crear layout principal
        vBox = new VBox(menuBar);
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
