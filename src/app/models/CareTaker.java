package app.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.interfaces.DBActions;
import database.DBConnection;
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
            Statement stm = DBConnection.getStatement();

            if (stm == null) System.out.println("null");

            try (ResultSet rs = stm.executeQuery("SELECT * FROM `caretaker`");) {
                while (rs.next()) {
                    list.add(new Caretaker(rs.getInt("caretakerId"), 
                        rs.getString("caretakerName"), rs.getString("caretakerPhoneNum"), 
                        rs.getInt("caretakerAge"), rs.getString("caretakerGender")));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {}

        return list;
    }
}