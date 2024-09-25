package com.example.tap2024b.models;


import java.sql.Statement;

public class ClienteDAO {
    private int idCte;
    private String nomCte;
    private String telCte;
    private String emailCte;

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

    public void INSERT(){
        String query = "INSERT INTO tblCliente(nomCte, telCte, emailCte)" + "VALUES('"+this.nomCte+"','"+this.telCte+"','"+this.emailCte+"')";

        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UPDATE(){
        //String query = "UPDATE tblCliente SET nomCte = ''" + "telCte = '' "

    }

    public void setEmailCte(String emailCte) {
        this.emailCte = emailCte;
    }
}
