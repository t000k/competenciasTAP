package com.example.tap2024b.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    // Variables estáticas para la configuración de la base de datos
    static private String DB = "spotify";    // Nombre de la base de datos a la que se conectará
    static private String USER = "root";     // Nombre de usuario de la base de datos
    static private String PASS = "sistemaPlan";  // Contraseña del usuario de la base de datos
    static private String HOST = "localhost";    // Dirección del host donde está corriendo la base de datos (localhost para el equipo local)
    static private String PORT = "3306";     // Puerto en el que la base de datos está escuchando (por defecto, MySQL usa el puerto 3306)

    public static Connection connection;     // Variable estática que almacenará la conexión a la base de datos

    // Método estático para crear la conexión a la base de datos
    public static void CrearConexion() {
        try {
            // Cargar el driver de MySQL (necesario para poder conectarse a una base de datos MySQL desde Java)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión utilizando la URL de la base de datos, el usuario y la contraseña
            // La URL tiene el formato: jdbc:mysql://HOST:PORT/DB
            connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB, USER, PASS);

            // Imprimir un mensaje si la conexión se establece correctamente
            //System.out.println("Conexión establecida a la base de datos");
            System.out.println("\033[0;33mConexión establecida con la base de datos :)\033[0m");

        } catch (Exception e) {
            // Capturar cualquier excepción que ocurra durante el intento de conexión
            e.printStackTrace();  // Imprimir el error en la consola para poder diagnosticar el problema
        }
    }
}
