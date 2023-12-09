package database;

public class DBFactories {
    public static void createPatientTable() {
        String query = "CREATE TABLE `patient`(" + 
            "patientId INT PRIMARY KEY AUTO_INCREMENT" +
            "patientName VARCHAR(255) NOT NULL" + 
            "patientAge INT NOT NULL" + 
            "patientGender CHAR(1) NOT NULL";
    }

    public static void createCaretakerTable() {
        String query = "CREATE TABLE `caretaker` (" +
            "caretakerId INT PRIMARY KEY AUTO_INCREMENT" + 
            "caretakerName VARCHAR(255) NOT NULL" + 
            "caretakerAge INT NOT NULL" + 
            "caretakerGender CHAR(1) NOT NULL);";
        
        
    }
}
