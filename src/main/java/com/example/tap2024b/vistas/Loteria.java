package com.example.tap2024b.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loteria extends Stage {

    private HBox hBoxMain, hBoxButtons;
    private VBox vbxTablilla, vbxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTimer;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private String[] arImages = {"/images/barril.jpeg","/images/botella.jpeg","/images/catrin.jpeg","/images/chavorruco.jpeg","/images/concha.jpeg","/images/luchador.jpeg","/images/maceta.jpeg","/images/rosa.jpeg","/images/tacos.jpeg","/images/venado.jpeg"};
    private Button[][] arBtnTab;
    private Panel pnlPrincipal;

    private List<Image> mazo; // Para manejar el mazo de cartas
    private int currentIndex; // Índice para manejar las cartas mostradas
    private List<Image> cartasMostradas; // Cartas que ya se han mostrado

    private boolean[][] marcadas; // Para saber si una carta en la tablilla ha sido marcada
    private Timeline timeline; // Para el temporizador
    private int segundos; // Para contar el tiempo

    public Loteria() {
        CrearUI();
        this.setTitle("Loteria Mexicana :)");
        this.setScene(escena);
        this.setResizable(true);
        this.show();
    }

    private Image loadImage(String path) {
        Image img = null;
        try {
            img = new Image(getClass().getResource(path).toString());
        } catch (NullPointerException e) {
            System.out.println("No se pudo cargar la imagen: " + path);
        }
        return img;
    }

    private void CrearUI() {
        ImageView imvAnt, imvSig;
        imvAnt = new ImageView(loadImage("/images/last.png"));
        imvAnt.setFitHeight(50);
        imvAnt.setFitWidth(50);
        imvSig = new ImageView(loadImage("/images/next.png"));
        imvSig.setFitWidth(50);
        imvSig.setFitHeight(50);

        gdpTablilla = new GridPane();
        CrearTablilla();

        btnAnterior = new Button();
        btnAnterior.setGraphic(imvAnt);
        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imvSig);

        // Funcionalidad de navegación de cartas
        btnAnterior.setOnAction(event -> mostrarCartaAnterior());
        btnSiguiente.setOnAction(event -> mostrarCartaSiguiente());

        hBoxButtons = new HBox(btnAnterior, btnSiguiente);
        vbxTablilla = new VBox(gdpTablilla, hBoxButtons);

        CrearMazo();

        hBoxMain = new HBox(vbxTablilla, vbxMazo);
        pnlPrincipal = new Panel("Loteria Mexicana :)");
        pnlPrincipal.getStyleClass().add("panel-success");
        pnlPrincipal.setBody(hBoxMain);
        hBoxMain.setSpacing(20);
        hBoxMain.setPadding(new Insets(20));
        escena = new Scene(pnlPrincipal, 800, 600);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        escena.getStylesheets().add(getClass().getResource("/styles/loteria.css").toExternalForm());
    }

    private void CrearMazo() {
        lblTimer = new Label("00:00");
        imvMazo = new ImageView(loadImage("/images/dorso.jpeg"));
        imvMazo.setFitHeight(450);
        imvMazo.setFitWidth(300);
        btnIniciar = new Button("Iniciar Juego");
        btnIniciar.getStyleClass().setAll("btn-sm", "btn-danger");

        // Inicializa el mazo y las cartas mostradas
        mazo = new ArrayList<>();
        for (String imgPath : arImages) {
            mazo.add(loadImage(imgPath));
        }
        Collections.shuffle(mazo); // Barajear las cartas
        currentIndex = -1; // Ninguna carta mostrada al inicio
        cartasMostradas = new ArrayList<>();

        // Iniciar el juego al presionar el botón
        btnIniciar.setOnAction(event -> iniciarJuego());

        vbxMazo = new VBox(lblTimer, imvMazo, btnIniciar);
        vbxMazo.setSpacing(10);

        // Inicializar temporizador
        segundos = 0;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            segundos++;
            lblTimer.setText(String.format("%02d:%02d", segundos / 60, segundos % 60));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void CrearTablilla() {
        arBtnTab = new Button[4][4];
        marcadas = new boolean[4][4];
        Image img;
        ImageView imv;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                img = loadImage(arImages[(i * 4 + j) % arImages.length]);
                imv = new ImageView(img);
                imv.setFitWidth(70);
                imv.setFitHeight(120);
                arBtnTab[j][i] = new Button();
                arBtnTab[j][i].setGraphic(imv);

                int finalI = i;
                int finalJ = j;

                // Funcionalidad para marcar la carta
                arBtnTab[j][i].setOnAction(event -> marcarCarta(finalJ, finalI));

                gdpTablilla.add(arBtnTab[j][i], j, i);
            }
        }
    }

    private void iniciarJuego() {
        timeline.play(); // Inicia el temporizador

        Timeline timelineCartas = new Timeline(new KeyFrame(Duration.seconds(0.1), ev -> {
            mostrarSiguienteCarta();
        }));
        timelineCartas.setCycleCount(mazo.size());
        timelineCartas.play();
    }

    private void mostrarSiguienteCarta() {
        if (currentIndex < mazo.size() - 1) {
            currentIndex++;
            Image siguienteCarta = mazo.get(currentIndex);
            imvMazo.setImage(siguienteCarta);
            cartasMostradas.add(siguienteCarta);
        }
    }

    private void mostrarCartaAnterior() {
        if (currentIndex > 0) {
            currentIndex--;
            Image cartaAnterior = cartasMostradas.get(currentIndex);
            imvMazo.setImage(cartaAnterior);
        }
    }

    private void mostrarCartaSiguiente() {
        if (currentIndex < cartasMostradas.size() - 1) {
            currentIndex++;
            Image cartaSiguiente = cartasMostradas.get(currentIndex);
            imvMazo.setImage(cartaSiguiente);
        }
    }

    private void marcarCarta(int x, int y) {
        // Cambiar el estado de la carta a marcada/no marcada
        marcadas[x][y] = !marcadas[x][y];
        arBtnTab[x][y].setStyle(marcadas[x][y] ? "-fx-background-color: green;" : "");
        verificarVictoria();
    }

    private void verificarVictoria() {
        // Verifica si hay una línea completa de cartas marcadas
        for (int i = 0; i < 4; i++) {
            boolean lineaCompleta = true;
            for (int j = 0; j < 4; j++) {
                if (!marcadas[i][j]) {
                    lineaCompleta = false;
                    break;
                }
            }
            if (lineaCompleta) {
                timeline.stop(); // Detener el temporizador
                System.out.println("¡Victoria! Línea completa.");
                // Mostrar algún mensaje de victoria, abrir un cuadro de diálogo, etc.
                return;
            }
        }
    }
}
