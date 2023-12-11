package app.models;

// Interface
import app.interfaces.DBMethods;

// Database
import database.DBConnection;

// Java connector lib(s)
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Javafx lib(s)
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Caretaker extends Person implements DBMethods<Caretaker> {
    private String phoneNum;

    public Caretaker(int id, String name, String phoneNum, int age, String gender) {
        super(id, name, age, gender);

        this.phoneNum = phoneNum;
    }

    public Caretaker(String name, String phoneNum, int age, String gender) {
        super(name, age, gender);

        this.phoneNum = phoneNum;
    }

    public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}

    public String getPhoneNum() {return phoneNum;}

    public static ObservableList<Caretaker> fetch() {
        ObservableList<Caretaker> list = FXCollections.observableArrayList();

        try {
            Statement stmt = DBConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `caretaker`");

            while (rs.next()) {
                list.add(new Caretaker(rs.getInt("caretakerId"), 
                    rs.getString("caretakerName"), 
                    rs.getString("caretakerPhoneNum"), 
                    rs.getInt("caretakerAge"), 
                    rs.getString("caretakerGender")));
            }

            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return list;
    }

    @Override
    public void insert() {
        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("INSERT INTO `caretaker` VALUES(0, ?, ?, ?, ?);", 
            Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNum);
            stmt.setInt(3, age);
            stmt.setString(4, gender);

            stmt.execute();

            try (ResultSet rs = stmt.getGeneratedKeys();) {
                if (rs.next()) {_id = rs.getInt(1);}
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void delete() {
        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("DELETE FROM `caretaker` WHERE caretakerId = ?;")) {
            stmt.setInt(1, _id);

            stmt.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void update(Caretaker newObj) {
        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("UPDATE `caretaker` SET caretakerName = ?, caretakerPhoneNum = ?, caretakerAge = ?, caretakerGender = ? WHERE caretakerId = ?;")) {
            stmt.setString(1, newObj.name);
            stmt.setString(2, newObj.phoneNum);
            stmt.setInt(3, newObj.age);
            stmt.setString(4, newObj.gender);
            stmt.setInt(5, _id);

            stmt.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static ObservableList<Caretaker> search(String searchName) {
        ObservableList<Caretaker> list = FXCollections.observableArrayList();

        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("SELECT * FROM `caretaker` WHERE caretakerName LIKE ?")) {
            stmt.setString(1, 
                String.format("%%%s%%", searchName));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Caretaker(rs.getInt("caretakerId"), 
                    rs.getString("caretakerName"), 
                    rs.getString("caretakerPhoneNum"), 
                    rs.getInt("caretakerAge"), 
                    rs.getString("caretakerGender")));
            }

            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return list;
    }
}
