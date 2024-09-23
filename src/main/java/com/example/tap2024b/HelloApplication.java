package com.example.tap2024b;

import com.example.tap2024b.vistas.Calculadora;
import com.example.tap2024b.vistas.Loteria;
import javafx.application.Application;
import javafx.geometry.Pos; // Importar Pos
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
    private StackPane stackPane; // Cambiar VBox a StackPane
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

        // Cargar la imagen de fondo desde el classpath
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/fondo.jpg")); // Ruta de la imagen dentro de resources

        // Crear un tamaño de fondo que cubra todo el VBox
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );

        // Asignar la imagen de fondo al VBox
        vBox.setBackground(new Background(background));

        // Crear el StackPane y añadir el VBox
        stackPane = new StackPane(vBox);

        // Cargar la imagen pequeña
        Image smallImage = new Image(getClass().getResourceAsStream("/images/tec.png")); // Ruta de la imagen pequeña
        ImageView imageView = new ImageView(smallImage);

        // Ajustar el tamaño de la imagen si es necesario
        imageView.setFitWidth(200); // Establecer el ancho
        imageView.setFitHeight(200); // Establecer la altura
        imageView.setPreserveRatio(true); // Mantener la proporción

        // Añadir la imagen pequeña al StackPane
        stackPane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER); // Alinear la imagen en la parte superior
    }

    @Override
    public void start(Stage stage) {
        CreateUI();

        // Establecer el ícono de la aplicación desde el classpath
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
