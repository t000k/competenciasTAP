package com.example.tap2024b;

import com.example.tap2024b.models.Conexion;
import com.example.tap2024b.vistas.Calculadora;
import com.example.tap2024b.vistas.Loteria;
import com.example.tap2024b.vistas.UsuariosInterface;
import com.example.tap2024b.vistas.MinesweeperApp; // Importar MinesweeperApp
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private MenuBar menuBar;
    private Menu menuCompetencias;
    private MenuItem menuItemCalculadora;
    private MenuItem menuItemLoteria;
    private MenuItem menuItemUsuarios;
    private MenuItem menuItemMinesweeper; // Cambiado a MinesweeperApp
    private StackPane stackPane;
    private VBox vBox;

    public void CreateUI() {
        // Crear barra de menú
        menuBar = new MenuBar();

        // Crear menú "Competencias"
        menuCompetencias = new Menu("Competencias");

        // Crear ítems dentro del menú "Competencias"
        menuItemCalculadora = new MenuItem("Calculadora");
        menuItemLoteria = new MenuItem("Loteria");
        menuItemUsuarios = new MenuItem("Usuarios");
        menuItemMinesweeper = new MenuItem("Minesweeper"); // Crear el ítem MinesweeperApp

        // Añadir acción para abrir la calculadora
        menuItemCalculadora.setOnAction(event -> {
            try {
                Calculadora calculadora = new Calculadora();
                Stage calculadoraStage = new Stage();
                calculadora.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Añadir acción para abrir la Lotería
        menuItemLoteria.setOnAction(event -> {
            try {
                Loteria loteria = new Loteria();
                Stage loteriaStage = new Stage();
                loteria.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Añadir acción para abrir Usuarios
        menuItemUsuarios.setOnAction(event -> new UsuariosInterface());

        // Añadir acción para abrir Minesweeper
        menuItemMinesweeper.setOnAction(event -> {
            try {
                MinesweeperApp minesweeperApp = new MinesweeperApp();
                Stage minesweeperStage = new Stage();
                minesweeperApp.show(); // Llamar al método show
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Añadir los ítems al menú "Competencias"
        menuCompetencias.getItems().addAll(menuItemCalculadora, menuItemLoteria, menuItemUsuarios, menuItemMinesweeper); // Añadir Minesweeper
        menuBar.getMenus().add(menuCompetencias);

        // Crear layout principal
        vBox = new VBox(menuBar);

        // Cargar la imagen de fondo
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/fondo.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        vBox.setBackground(new Background(background));

        // Crear el StackPane y añadir el VBox
        stackPane = new StackPane(vBox);

        // Cargar la imagen pequeña
        Image smallImage = new Image(getClass().getResourceAsStream("/images/tec.png"));
        ImageView imageView = new ImageView(smallImage);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        stackPane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);
    }

    @Override
    public void start(Stage stage) {
        CreateUI();
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/logoitcelaya.png"));
            stage.getIcons().add(icon);
        } catch (NullPointerException e) {
            System.out.println("El ícono no se pudo cargar: " + e.getMessage());
        }

        Scene scene = new Scene(stackPane, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
