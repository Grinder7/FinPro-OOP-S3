package app.models;

// Database
import database.DBConnection;

// Interface(s)
import app.interfaces.DBMethods;

// Java Connector lib
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Javafx lib
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Patient extends Person implements DBMethods<Patient> {
    protected String _disabilityDetail;

    public Patient(int id, String name, int age, String gender, String detail) {
        super(id, name, age, gender);

        _disabilityDetail = detail;
    }

    public Patient(String name, int age, String gender, String detail) {
        super(name, age, gender);

        _disabilityDetail = detail;
    }

    public void setDisabilityDetail(String disabilitDet) {
        _disabilityDetail = disabilitDet;
    }

    public String getDisabilityDetail() {
        return _disabilityDetail;
    }

    public static ObservableList<Patient> fetch() {
        ObservableList<Patient> list = javafx.collections.FXCollections.observableArrayList();

        try (PreparedStatement pstmt = DBConnection.getConnection()
            .prepareStatement("SELECT * FROM `patient`")) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Patient(rs.getInt("patientId"), 
                    rs.getString("patientName"), 
                    rs.getInt("patientAge"),
                    rs.getString("patientGender"), 
                    rs.getString("disabilityDetail")));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return list;
    }

    @Override
    public void insert() {
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
            "INSERT INTO `patient` VALUES (0, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, _disabilityDetail);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            
            if (rs.next()) {_id = rs.getInt(1);} 
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void delete() {
        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("DELETE FROM `patient` WHERE `patientId` = ?")) {
            stmt.setInt(1, _id);

            stmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void update(Patient newObj) {
        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("UPDATE `patient` SET `patientName` = ?, `patientAge` = ?, `patientGender` = ?, `disabilityDetail` = ? WHERE `patientId` = ?")) {
            stmt.setString(1, newObj.getName());
            stmt.setInt(2, newObj.getAge());
            stmt.setString(3, newObj.getGender());
            stmt.setString(4, newObj.getDisabilityDetail());
            stmt.setInt(5, _id);

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static ObservableList<Patient> search(String searchName) {
        ObservableList<Patient> list = FXCollections.observableArrayList();

        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("SELECT * FROM `patient` WHERE patientName LIKE ?")) {
            stmt.setString(1, 
                String.format("%%%s%%", searchName));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Patient(rs.getInt("patientId"), 
                    rs.getString("patientName"), 
                    rs.getInt("patientAge"),
                    rs.getString("patientGender"),
                    rs.getString("disabilityDetail")));
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