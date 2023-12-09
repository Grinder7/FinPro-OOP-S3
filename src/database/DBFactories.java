package database;

import java.sql.SQLException;
import java.sql.Statement;

public class DBFactories {
    private static Statement _statement = DBConnection.getStatement();

    public static void createPatientTable() {
        String query = "CREATE TABLE `patient`(" + 
            "patientId INT PRIMARY KEY AUTO_INCREMENT," +
            "patientName VARCHAR(255) NOT NULL," + 
            "patientAge INT NOT NULL," + 
            "patientGender CHAR(1) NOT NULL" + 
        ");";
    }

    public static void createCaretakerTable() {
        String query = "CREATE TABLE `caretaker`(" +
            "caretakerId INT PRIMARY KEY AUTO_INCREMENT," + 
            "caretakerName VARCHAR(255) NOT NULL," + 
            "caretakerPhoneNum VARCHAR(13) NOT NULL" + 
            "caretakerAge INT NOT NULL," + 
            "caretakerGender CHAR(1) NOT NULL," + 
        ");";
        
        try {_statement.executeUpdate(query);}
        catch (SQLException e) {e.printStackTrace();}
    }

    public static void createItemTable() {
        String query = "CREATE TABLE `item`(" + 
            "itemId INT PRIMARY KEY AUTO_INCREMENT," + 
            "itemName VARCHAR(255) NOT NULL," + 
            "itemQuantity INT NOT NULL" + 
        ");";
        
        try {_statement.executeUpdate(query);}
        catch (SQLException e) {e.printStackTrace();}
    }
}
