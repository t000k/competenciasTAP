package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Button[][] arrBtns; // Matriz de botones de la calculadora
    private TextField txtPantalla; // Campo de texto que actúa como pantalla de la calculadora
    private GridPane gdpTeclado; // GridPane para organizar los botones de la calculadora
    private VBox vBox; // Contenedor principal que incluye pantalla y teclado
    private Scene escena; // Escena de la aplicación
    private String[] strTeclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "0", ".", "=", "-"}; // Arreglo de las teclas de la calculadora
    private Button btnClear; // Botón para limpiar la pantalla

    // Variables de control para manejar errores y estados de la calculadora
    private boolean esperandoOperador = false; // Control para evitar operadores consecutivos
    private boolean operacionCompleta = false; // Control para saber si la operación ha finalizado
    private boolean divisionPorCero = false;   // Control para manejar división por cero
    private boolean errorSintaxis = false;     // Control para errores de sintaxis

    // Método para configurar la interfaz de usuario
    private void CreateUI() {
        arrBtns = new Button[4][4];
        txtPantalla = new TextField("0"); // Pantalla inicial con "0"
        txtPantalla.setAlignment(Pos.CENTER_RIGHT); // Alineación de texto a la derecha
        gdpTeclado = new GridPane(); // Inicialización del teclado
        CrearTeclado(); // Llamada para crear el teclado de botones
        btnClear = new Button("CE"); // Botón de limpiar pantalla
        btnClear.setOnAction(event -> reiniciarCalculadora()); // Acción para limpiar la pantalla cuando se presiona el botón
        vBox = new VBox(txtPantalla, gdpTeclado, btnClear); // Contenedor que agrupa la pantalla, el teclado y el botón Clear
        btnClear.setId("font-button"); // Estilo del botón Clear (si hay estilos CSS)
        escena = new Scene(vBox, 300, 300); // Creación de la escena de la calculadora
        escena.getStylesheets().add(getClass().getResource("/styles/cal.css").toString()); // Estilos externos
    }

    // Método para crear el teclado de la calculadora
    private void CrearTeclado() {
        for (int i = 0; i < arrBtns.length; i++) {
            for (int j = 0; j < arrBtns.length; j++) {
                arrBtns[j][i] = new Button(strTeclas[4 * i + j]); // Crear botones con los valores en strTeclas
                arrBtns[j][i].setPrefSize(75, 75); // Establecer tamaño de los botones
                String tecla = strTeclas[4 * i + j]; // Asignar el valor de la tecla correspondiente
                arrBtns[j][i].setOnAction(event -> DetectarTecla(tecla)); // Asignar acción al presionar cada tecla
                gdpTeclado.add(arrBtns[j][i], j, i); // Añadir el botón al GridPane en su posición
            }
        }
    }

    // Método para manejar qué hacer cuando se presiona una tecla
    private void DetectarTecla(String tecla) {
        // Si hay división por cero o error de sintaxis, reiniciar la calculadora
        if (divisionPorCero || errorSintaxis) {
            reiniciarCalculadora();
        }

        // Si es un número o un punto decimal, manejar la entrada
        if ("0123456789.".contains(tecla)) {
            manejarEntradaNumero(tecla);
        }
        // Si es un operador, manejar la lógica de operadores
        else if ("+-*/".contains(tecla)) {
            manejarOperador(tecla);
        }
        // Si es el igual, realizar la operación
        else if ("=".equals(tecla)) {
            manejarIgual();
        }
    }

    // Método para manejar la entrada de números y decimales
    private void manejarEntradaNumero(String tecla) {
        // Si la operación ha terminado o la pantalla está en "0", reemplazar el contenido
        if (operacionCompleta || txtPantalla.getText().equals("0")) {
            txtPantalla.setText(tecla); // Reemplaza el contenido de la pantalla
            operacionCompleta = false; // Indica que se ha comenzado una nueva operación
        } else {
            txtPantalla.appendText(tecla); // Agrega el número o decimal al final
        }
        esperandoOperador = true;  // Después de un número, esperar un operador
    }

    // Método para manejar los operadores matemáticos
    private void manejarOperador(String operador) {
        String text = txtPantalla.getText();

        // Si no se espera un operador (evitar operadores consecutivos), generar error de sintaxis
        if (!esperandoOperador) {
            txtPantalla.setText("Error de sintaxis");
            errorSintaxis = true;
            return;
        }

        // Si no hay operador previo, agregar el operador
        if (!text.endsWith(" ") && !text.equals("Error")) {
            txtPantalla.appendText(" " + operador + " ");
            esperandoOperador = false; // Ahora se espera un número después del operador
        }
    }

    // Método para manejar el botón "="
    private void manejarIgual() {
        String expresion = txtPantalla.getText();
        String[] tokens = expresion.split(" ");

        // Verifica si la expresión está completa (debe tener al menos número, operador, número)
        if (tokens.length < 3 || !esperandoOperador) {
            txtPantalla.setText("Error de sintaxis");
            errorSintaxis = true;
            return;
        }

        // Evaluar la expresión y mostrar el resultado
        String resultado = evaluarExpresion(expresion);
        if (divisionPorCero) {
            txtPantalla.setText("Error: Div. por 0"); // Mostrar error si hay división por cero
        } else {
            txtPantalla.setText(resultado); // Mostrar el resultado si es correcto
        }
        operacionCompleta = true; // Indica que la operación ha finalizado
    }

    // Método para evaluar la expresión matemática
    private String evaluarExpresion(String expresion) {
        String[] tokens = expresion.split(" ");

        double num1 = Double.parseDouble(tokens[0]); // Primer número
        double num2 = Double.parseDouble(tokens[2]); // Segundo número
        String operador = tokens[1]; // Operador entre ambos números
        double resultado = 0;

        // Manejo de la división por cero
        if (operador.equals("/") && num2 == 0) {
            divisionPorCero = true; // Marcar que hubo división por cero
            return ""; // Retornar vacío en caso de división por cero
        }

        // Realizar la operación matemática según el operador
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
                resultado = num1 / num2;
                break;
            default:
                errorSintaxis = true; // Si el operador no es válido, marcar error de sintaxis
                return "Error";
        }

        return String.valueOf(resultado); // Retornar el resultado como texto
    }

    // Método para reiniciar la calculadora
    private void reiniciarCalculadora() {
        txtPantalla.setText("0"); // Resetear la pantalla a "0"
        divisionPorCero = false; // Resetear estado de error de división por cero
        errorSintaxis = false; // Resetear estado de error de sintaxis
        esperandoOperador = false; // Resetear el estado de espera de operador
        operacionCompleta = false; // Indicar que se puede comenzar una nueva operación
    }

    // Constructor de la clase Calculadora
    public Calculadora() {
        CreateUI(); // Inicializa la interfaz de usuario
        this.setTitle("Calculadora"); // Establece el título de la ventana
        this.setScene(escena); // Asigna la escena a la ventana
        this.show(); // Muestra la ventana
    }
}
