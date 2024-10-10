package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class UsuariosInterface {
    private Stage stage; // Ventana principal de la interfaz
    private TextField nombreField; // Campo de texto para el nombre del cliente
    private TextField telefonoField; // Campo de texto para el teléfono del cliente
    private TextField emailField; // Campo de texto para el email del cliente
    private TableView<ClienteDAO> tablaClientes; // Tabla para mostrar los clientes
    private ClienteDAO clienteDAO; // Objeto de la clase ClienteDAO para manejar las operaciones CRUD

    // Constructor de la interfaz de usuario
    public UsuariosInterface() {
        this.clienteDAO = new ClienteDAO(); // Inicializa el objeto ClienteDAO
        this.stage = new Stage(); // Crea la nueva ventana de la interfaz
        inicializarUI(); // Inicializa la interfaz gráfica
    }

    // Método para configurar la interfaz gráfica
    private void inicializarUI() {
        // Inicializa los campos de texto con los respectivos "placeholders" o sugerencias
        nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        telefonoField = new TextField();
        telefonoField.setPromptText("Teléfono");

        emailField = new TextField();
        emailField.setPromptText("Email");

        // Botón para agregar un cliente
        Button agregarButton = new Button("Agregar Cliente");
        agregarButton.setOnAction(e -> agregarCliente()); // Acción cuando se presiona el botón

        // Botón para eliminar un cliente
        Button eliminarButton = new Button("Eliminar Cliente");
        eliminarButton.setOnAction(e -> eliminarCliente()); // Acción cuando se presiona el botón

        // Botón para editar un cliente
        Button editarButton = new Button("Editar Cliente");
        editarButton.setOnAction(e -> editarCliente()); // Acción cuando se presiona el botón

        // Inicializa la tabla donde se mostrarán los clientes
        tablaClientes = new TableView<>();
        configurarTabla(); // Configura las columnas de la tabla

        // Layout que contiene los campos de texto, botones y la tabla
        VBox layout = new VBox(10, nombreField, telefonoField, emailField, agregarButton, eliminarButton, editarButton, tablaClientes);

        // Define la escena y la muestra en la ventana (stage)
        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Gestión de Usuarios");
        stage.show();

        // Actualiza la tabla para mostrar los clientes existentes al abrir la interfaz
        actualizarTabla();
    }

    // Configura las columnas de la tabla de clientes
    private void configurarTabla() {
        // Columna de ID
        TableColumn<ClienteDAO, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idCte"));

        // Columna de nombre
        TableColumn<ClienteDAO, String> nombreColumn = new TableColumn<>("Nombre");
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        // Columna de teléfono
        TableColumn<ClienteDAO, String> telefonoColumn = new TableColumn<>("Teléfono");
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        // Columna de email
        TableColumn<ClienteDAO, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        // Añade las columnas a la tabla
        tablaClientes.getColumns().addAll(idColumn, nombreColumn, telefonoColumn, emailColumn);

        // Permite seleccionar un solo cliente a la vez en la tabla
        tablaClientes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    // Método para agregar un nuevo cliente
    private void agregarCliente() {
        // Obtiene los datos ingresados en los campos de texto
        String nombre = nombreField.getText();
        String telefono = telefonoField.getText();
        String email = emailField.getText();

        // Verifica que los campos no estén vacíos
        if (!nombre.isEmpty() && !telefono.isEmpty() && !email.isEmpty()) {
            // Asigna los datos al objeto clienteDAO
            clienteDAO.setNomCte(nombre);
            clienteDAO.setTelCte(telefono);
            clienteDAO.setEmailCte(email);
            clienteDAO.INSERT(); // Inserta el cliente en la base de datos

            // Actualiza la tabla para reflejar los cambios
            actualizarTabla();

            // Limpia los campos de texto
            nombreField.clear();
            telefonoField.clear();
            emailField.clear();
        } else {
            System.out.println("Por favor completa todos los campos."); // Mensaje en caso de campos vacíos
        }
    }

    // Método para eliminar un cliente
    private void eliminarCliente() {
        // Obtiene el cliente seleccionado en la tabla
        ClienteDAO clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            // Asigna el ID del cliente seleccionado al objeto clienteDAO
            clienteDAO.setIdCte(clienteSeleccionado.getIdCte());
            clienteDAO.DELETE(); // Elimina el cliente de la base de datos

            // Actualiza la tabla para reflejar los cambios
            actualizarTabla();
        } else {
            System.out.println("Por favor selecciona un cliente para eliminar."); // Mensaje si no se seleccionó un cliente
        }
    }

    // Método para editar un cliente
    private void editarCliente() {
        // Obtiene el cliente seleccionado en la tabla
        ClienteDAO clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            // Rellena los campos de texto con los datos del cliente seleccionado
            nombreField.setText(clienteSeleccionado.getNomCte());
            telefonoField.setText(clienteSeleccionado.getTelCte());
            emailField.setText(clienteSeleccionado.getEmailCte());

            // Asigna el ID del cliente seleccionado al objeto clienteDAO para la actualización
            clienteDAO.setIdCte(clienteSeleccionado.getIdCte());

            // Cambia el botón de agregar cliente a un botón de actualizar cliente
            Button editarButton = new Button("Actualizar Cliente");
            editarButton.setOnAction(e -> {
                // Actualiza los datos del cliente con los valores de los campos de texto
                clienteDAO.setNomCte(nombreField.getText());
                clienteDAO.setTelCte(telefonoField.getText());
                clienteDAO.setEmailCte(emailField.getText());
                clienteDAO.UPDATE(); // Actualiza el cliente en la base de datos

                // Actualiza la tabla para reflejar los cambios
                actualizarTabla();

                // Limpia los campos de texto
                nombreField.clear();
                telefonoField.clear();
                emailField.clear();

                // Cambia de nuevo el botón a "Agregar Cliente"
                Button agregarButton = new Button("Agregar Cliente");
                agregarButton.setOnAction(event -> agregarCliente());
            });
        } else {
            System.out.println("Por favor selecciona un cliente para editar."); // Mensaje si no se seleccionó un cliente
        }
    }

    // Método para actualizar la tabla con los clientes de la base de datos
    private void actualizarTabla() {
        tablaClientes.getItems().clear(); // Limpia la tabla
        List<ClienteDAO> clientes = clienteDAO.SELECT_ALL(); // Obtiene todos los clientes de la base de datos
        tablaClientes.getItems().addAll(clientes); // Añade los clientes obtenidos a la tabla
    }
}
