package database;

import java.sql.SQLException;
import java.sql.Statement;

public class DBFactories {
    private static Statement _statement;

    public static void createTables() {
        try {
            _statement = DBConnection.getConnection().createStatement();

            createPatientTable();
            createCaretakerTable();
            createSupplyTable();
            createDonationTable();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void createPatientTable() {
        String query = "CREATE TABLE `patient`(" + 
            "patientId INT PRIMARY KEY AUTO_INCREMENT," +
            "patientName VARCHAR(255) NOT NULL," + 
            "patientAge INT NOT NULL," + 
            "patientGender VARCHAR(1) NOT NULL," + 
            "disabilityDetail TEXT NOT NULL" + 
        ");";

        try {_statement.executeUpdate(query);}
        catch (SQLException e) {e.printStackTrace();}
    }

    public static void createCaretakerTable() {
        String query = "CREATE TABLE `caretaker`(" +
            "caretakerId INT PRIMARY KEY AUTO_INCREMENT," + 
            "caretakerName VARCHAR(255) NOT NULL," + 
            "caretakerPhoneNum VARCHAR(13) NOT NULL," + 
            "caretakerAge INT NOT NULL," + 
            "caretakerGender VARCHAR(1) NOT NULL" + 
        ");";
        
        try {_statement.executeUpdate(query);}
        catch (SQLException e) {e.printStackTrace();}
    }

    public static void createSupplyTable() {
        String query = "CREATE TABLE `supply`(" + 
            "itemId INT PRIMARY KEY AUTO_INCREMENT," + 
            "itemName VARCHAR(255) NOT NULL," + 
            "itemQuantity INT NOT NULL" + 
        ");";
        
        try {_statement.executeUpdate(query);}
        catch (SQLException e) {e.printStackTrace();}
    }

    public static void createDonationTable() {
        String query = "CREATE TABLE `donation`(" + 
            "donationId INT PRIMARY KEY AUTO_INCREMENT," + 
            "donatorName VARCHAR(255) NOT NULL," + 
            "donationItems VARCHAR(255) NOT NULL," + 
            "donationQuantity INT NOT NULL," + 
            "donationDate DATE NOT NULL" + 
        ");";

        try {_statement.executeUpdate(query);}
        catch (SQLException e) {e.printStackTrace();}
    }
}
