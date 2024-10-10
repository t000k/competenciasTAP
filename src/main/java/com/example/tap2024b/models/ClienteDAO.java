package com.example.tap2024b.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    // Atributos privados para los datos del cliente
    private int idCte;         // Identificador del cliente
    private String nomCte;     // Nombre del cliente
    private String telCte;     // Teléfono del cliente
    private String emailCte;   // Correo electrónico del cliente

    // Métodos getter y setter para acceder y modificar los atributos privados

    public int getIdCte() {
        return idCte;
    }

    public void setIdCte(int idCte) {
        this.idCte = idCte;
    }

    public String getNomCte() {
        return nomCte;
    }

    public void setNomCte(String nomCte) {
        this.nomCte = nomCte;
    }

    public String getTelCte() {
        return telCte;
    }

    public void setTelCte(String telCte) {
        this.telCte = telCte;
    }

    public String getEmailCte() {
        return emailCte;
    }

    public void setEmailCte(String emailCte) {
        this.emailCte = emailCte;
    }

    // Método para insertar un nuevo cliente en la base de datos
    public void INSERT() {
        String query = "INSERT INTO tbcliente(nomCte, telCte, emailCte) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            // Si la conexión aún no está establecida, se crea una nueva conexión
            if (Conexion.connection == null) {
                Conexion.CrearConexion();
            }
            conn = Conexion.connection;   // Se obtiene la conexión actual
            PreparedStatement pstmt = conn.prepareStatement(query);   // Se prepara la consulta SQL
            pstmt.setString(1, this.nomCte);  // Se establecen los valores en la consulta: nombre del cliente
            pstmt.setString(2, this.telCte);  // Teléfono del cliente
            pstmt.setString(3, this.emailCte); // Correo electrónico del cliente

            pstmt.executeUpdate();   // Se ejecuta la inserción en la base de datos
            pstmt.close();   // Se cierra el PreparedStatement
            //System.out.println("Cliente insertado exitosamente.");
            System.out.println("\033[0;32mCliente insertado exitosamente.\033[0m");

        } catch (Exception e) {
            // Si ocurre un error, se imprime el error en la consola
            e.printStackTrace();
        }
    }

    // Método para actualizar los datos de un cliente existente en la base de datos
    public void UPDATE() {
        String query = "UPDATE tbcliente SET nomCte = ?, telCte = ?, emailCte = ? WHERE idCte = ?";
        Connection conn = null;

        try {
            if (Conexion.connection == null) {
                Conexion.CrearConexion();
            }
            conn = Conexion.connection;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.nomCte);  // Establecer nuevo nombre del cliente
            pstmt.setString(2, this.telCte);  // Establecer nuevo teléfono del cliente
            pstmt.setString(3, this.emailCte);  // Establecer nuevo correo del cliente
            pstmt.setInt(4, this.idCte);  // Establecer el ID del cliente a actualizar

            pstmt.executeUpdate();   // Ejecutar la actualización
            pstmt.close();
            //System.out.println("Cliente actualizado exitosamente.");
            System.out.println("\033[0;34mCliente actualizado exitosamente.\033[0m");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un cliente de la base de datos
    public void DELETE() {
        String query = "DELETE FROM tbcliente WHERE idCte = ?";
        Connection conn = null;

        try {
            if (Conexion.connection == null) {
                Conexion.CrearConexion();
            }
            conn = Conexion.connection;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, this.idCte);  // Establecer el ID del cliente a eliminar

            pstmt.executeUpdate();   // Ejecutar la eliminación
            pstmt.close();
            //System.out.println("Cliente eliminado exitosamente.");
            System.out.println("\033[0;31mCliente eliminado exitosamente.\033[0m");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener un cliente específico por su ID
    public ClienteDAO SELECT(int idCte) {
        String query = "SELECT * FROM tbcliente WHERE idCte = ?";
        ClienteDAO cliente = null;   // Objeto cliente donde se almacenarán los datos
        Connection conn = null;

        try {
            if (Conexion.connection == null) {
                Conexion.CrearConexion();
            }
            conn = Conexion.connection;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idCte);   // Establecer el ID del cliente a buscar

            ResultSet rs = pstmt.executeQuery();   // Ejecutar la consulta
            if (rs.next()) {   // Si se encuentra un cliente con el ID proporcionado
                cliente = new ClienteDAO();   // Crear un nuevo objeto ClienteDAO con los datos obtenidos
                cliente.setIdCte(rs.getInt("idCte"));
                cliente.setNomCte(rs.getString("nomCte"));
                cliente.setTelCte(rs.getString("telCte"));
                cliente.setEmailCte(rs.getString("emailCte"));
            }

            rs.close();   // Cerrar el ResultSet
            pstmt.close();   // Cerrar el PreparedStatement
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;   // Retornar el cliente encontrado o null si no existe
    }

    // Método para obtener una lista de todos los clientes en la base de datos
    public List<ClienteDAO> SELECT_ALL() {
        String query = "SELECT * FROM tbcliente";
        List<ClienteDAO> clientes = new ArrayList<>();  // Lista donde se almacenarán todos los clientes
        Connection conn = null;

        try {
            if (Conexion.connection == null) {
                Conexion.CrearConexion();
            }
            conn = Conexion.connection;
            Statement stmt = conn.createStatement();   // Crear un Statement para ejecutar la consulta
            ResultSet rs = stmt.executeQuery(query);   // Ejecutar la consulta

            // Iterar sobre el resultado y agregar cada cliente a la lista
            while (rs.next()) {
                ClienteDAO cliente = new ClienteDAO();
                cliente.setIdCte(rs.getInt("idCte"));
                cliente.setNomCte(rs.getString("nomCte"));
                cliente.setTelCte(rs.getString("telCte"));
                cliente.setEmailCte(rs.getString("emailCte"));
                clientes.add(cliente);   // Agregar el cliente a la lista
            }

            rs.close();   // Cerrar el ResultSet
            stmt.close();   // Cerrar el Statement
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientes;   // Retornar la lista de clientes
    }
}
