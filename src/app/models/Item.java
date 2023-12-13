package app.models;

// Interface(s)
import app.interfaces.DBMethods;

// Database
import database.DBConnection;

import java.sql.PreparedStatement;
// Java connector lib
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Javafx lib
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Item implements DBMethods<Item> {
    protected int _id;
    protected String name;
    protected int quantity;

    public Item(int id, String name, int quantity) {
        _id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {return _id;}

    public String getName() {return name;}

    public int getQuantity() {return quantity;}

    public static ObservableList<Item> fetch() {
        ObservableList<Item> list = FXCollections.observableArrayList();

        try {
            Statement stmt = DBConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `supply`");

            while (rs.next()) {
                list.add(new Item(rs.getInt("itemId"), rs.getString("itemName"), 
                    rs.getInt("itemQuantity")));
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
            .prepareStatement("INSERT INTO `supply` VALUES(0, ?, ?);", 
            Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setInt(2, quantity);

            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {_id = rs.getInt(1);}
            else {
                throw new SQLException("Creating item failed, no ID obtained");
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void delete() {
        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("DELETE FROM `supply` WHERE itemId = ?;")) {
            stmt.setInt(1, _id);

            stmt.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void update(Item newObj) {
        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("UPDATE `supply` SET itemName = ?, itemQuantity = ? WHERE itemId = ?;")) {
            stmt.setString(1, newObj.getName());
            stmt.setInt(2, newObj.getQuantity());
            stmt.setInt(3, _id);

            stmt.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static ObservableList<Item> search(String searchName) {
        ObservableList<Item> list = FXCollections.observableArrayList();

        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("SELECT * FROM `supply` WHERE itemName LIKE ?")) {
            stmt.setString(1, 
                String.format("%%%s%%", searchName));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Item(rs.getInt("itemId"), 
                    rs.getString("itemName"), 
                    rs.getInt("itemQuantity")));
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
