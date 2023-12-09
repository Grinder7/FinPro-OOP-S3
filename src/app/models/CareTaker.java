package app.models;

import app.interfaces.DBActions;
import database.DBConnection;

// Java connector lib(s)
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Javafx lib(s)
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Caretaker extends Person implements DBActions {
    public String phoneNum;

    public Caretaker(int id, String name, String phoneNum, int age, String gender) {
        super(id, name, age, gender);

        this.phoneNum = phoneNum;
    }
    
    public String getPhoneNum() {return phoneNum;}

    public static ObservableList<Caretaker> fetch() {
        ObservableList<Caretaker> list = FXCollections.observableArrayList();

        try {
            Statement stmt = DBConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `caretaker`");

            while (rs.next()) {
                list.add(new Caretaker(rs.getInt("caretakerId"), 
                    rs.getString("caretakerName"), rs.getString("caretakerPhoneNum"), 
                    rs.getInt("caretakerAge"), rs.getString("caretakerGender")));
            }

            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return list;
    }

    public static void insert(String name, String phoneNum, int age, String gender) {
        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("INSERT INTO `caretaker` VALUES(0, ?, ?, ?, ?)");) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNum);
            stmt.setInt(3, age);
            stmt.setString(4, gender);

            stmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}