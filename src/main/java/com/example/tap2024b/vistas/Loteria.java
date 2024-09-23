package com.example.tap2024b.vistas;

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
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Loteria extends Stage {

    private HBox hBoxMain, hBoxButtons;
    private VBox vbxTablilla, vbxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTimer;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private List<String[]> tableros; // Lista de tableros con diferentes combinaciones
    private int currentTablero = 0; // Indica el tablero actual
    private Button[][] arBtnTab;

    private Panel pnlPrincipal;

    public Loteria() {
        CrearTableros(); // Inicializar los tableros
        CrearUI();
        this.setTitle("Loteria Mexicana :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvAnt, imvSig;
        imvAnt = new ImageView(new Image(getClass().getResource("/images/prev.png").toString()));
        imvAnt.setFitHeight(50);
        imvAnt.setFitWidth(50);
        imvSig = new ImageView(new Image(getClass().getResource("/images/next.png").toString()));
        imvSig.setFitWidth(50);
        imvSig.setFitHeight(50);

        gdpTablilla = new GridPane();
        CrearTablilla();

        btnAnterior = new Button();
        btnAnterior.setGraphic(imvAnt);
        btnAnterior.setOnAction(e -> CambiarTablero(-1)); // Acción para cambiar al tablero anterior

        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imvSig);
        btnSiguiente.setOnAction(e -> CambiarTablero(1)); // Acción para cambiar al siguiente tablero

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
        imvMazo = new ImageView(new Image(getClass().getResource("/images/dorso.jpeg").toString()));
        imvMazo.setFitHeight(450);
        imvMazo.setFitWidth(300);
        btnIniciar = new Button("Iniciar Juego");
        btnIniciar.getStyleClass().setAll("btn-sm", "btn-danger");
        vbxMazo = new VBox(lblTimer, imvMazo, btnIniciar);
        vbxMazo.setSpacing(10);
    }

    private void CrearTablilla() {
        arBtnTab = new Button[4][4];
        ActualizarTablilla(); // Llena la tablilla con imágenes
    }

    // Cambiar el tablero de lotería según la dirección (1 para siguiente, -1 para anterior)
    private void CambiarTablero(int direccion) {
        currentTablero = (currentTablero + direccion + tableros.size()) % tableros.size();
        ActualizarTablilla();
    }

    // Actualizar la tablilla con imágenes del tablero actual
    private void ActualizarTablilla() {
        gdpTablilla.getChildren().clear(); // Limpiar el gridpane
        String[] tableroActual = tableros.get(currentTablero);
        Image img;
        ImageView imv;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                img = cargarImagen(tableroActual[(i * 4 + j) % tableroActual.length]);
                imv = new ImageView(img);
                imv.setFitWidth(70);
                imv.setFitHeight(120);
                arBtnTab[j][i] = new Button();
                arBtnTab[j][i].setGraphic(imv);
                gdpTablilla.add(arBtnTab[j][i], j, i);
            }
        }
    }

    // Cargar la imagen, probando diferentes extensiones de archivo
    private Image cargarImagen(String nombre) {
        String[] extensiones = {".png", ".jpg", ".jpeg"};
        for (String extension : extensiones) {
            InputStream is = getClass().getResourceAsStream("/images/" + nombre + extension);
            if (is != null) {
                return new Image(is);
            }
        }
        // Si no se encuentra la imagen en ningún formato, devuelve una imagen por defecto
        return new Image(getClass().getResource("/images/default.png").toString());
    }

    // Crear los diferentes tableros con combinaciones de cartas
    private void CrearTableros() {
        tableros = new ArrayList<>();
        tableros.add(new String[]{"pescado", "musico", "valiente", "calavera", "negrito", "paraguas", "chalupa", "sandia",
                "rana", "barril", "arpa", "cotorro", "botella", "violoncello", "corona", "árbol"});
        tableros.add(new String[]{"alacran", "apache", "cazo", "diablito", "cantarito", "jaras", "palma", "arana",
                "bota", "escalera", "muerte", "cotorro", "luna", "nopal", "pera", "bandolón"});
        tableros.add(new String[]{"maceta", "camaron", "mano", "pajaro", "garza", "estrella", "pescado", "musico",
                "valiente", "calavera", "negrito", "paraguas", "chalupa", "sandia", "rana", "barril"});
        tableros.add(new String[]{"arpa", "cotorro", "botella", "violoncello", "corona", "árbol", "alacran", "apache",
                "cazo", "diablito", "cantarito", "jaras", "palma", "arana", "bota", "escalera"});
        tableros.add(new String[]{"muerte", "cotorro", "luna", "nopal", "pera", "bandolón", "maceta", "camaron",
                "mano", "pajaro", "garza", "estrella", "pescado", "musico", "valiente", "calavera"});
    }
}
