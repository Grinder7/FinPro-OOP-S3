package app.models;

// Interface(s)
import app.interfaces.DBMethods;

// Database
import database.DBConnection;

// Java connector lib(s)
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

// Javafx lib(s)
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Donation implements DBMethods<Donation> {
    protected int _id;
    protected String donatorName;
    protected String itemName;
    protected int itemQuantity;
    protected Date date;

    public Donation(int id, String donatorName, String itemName, int itemQuantity, Date date) {
        _id = id;
        this.donatorName = donatorName;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.date = date;
    }

    public Donation(String donatorName, String itemName, int itemQuantity, Date date) {
        this.donatorName = donatorName;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.date = date;
    }

    public void setDonatorName(String donatorName) {this.donatorName = donatorName;}

    public void setItemName(String itemName) {this.itemName = itemName;}

    public void setItemQuantity(int itemQuantity) {this.itemQuantity = itemQuantity;}

    public int getId() {return _id;}

    public String getDonatorName() {return donatorName;}

    public String getItemName() {return itemName;}

    public int getItemQuantity() {return itemQuantity;}

    public Date getDate() {return date;}

    public static ObservableList<Donation> fetch() {
        ObservableList<Donation> list = FXCollections.observableArrayList();

        try (PreparedStatement pstmt = DBConnection.getConnection()
            .prepareStatement("SELECT * FROM `donation` ORDER BY donationDate DESC LIMIT 30;");) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Donation(rs.getInt("donationId"), 
                    rs.getString("donatorName"), 
                    rs.getString("donationItem"),
                    rs.getInt("donationQuantity"),
                    rs.getDate("donationDate")));
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
            .prepareStatement("INSERT INTO `donation` VALUES(0, ?, ?, ?, ?);", 
            Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, donatorName);
            stmt.setString(2, itemName);
            stmt.setInt(3, itemQuantity);
            stmt.setDate(4, date);

            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {_id = rs.getInt(1);}
            else {
                throw new SQLException("Creating donation failed, no ID obtained");
            }

            rs.close();

            // Update item quantity in supply table if item is available
            try (PreparedStatement _stmt = DBConnection.getConnection()
                .prepareStatement("UPDATE `supply` SET itemQuantity = itemQuantity + ? WHERE REPLACE(itemName, ' ', '') = ?;");) {
                _stmt.setInt(1, itemQuantity);
                _stmt.setString(2, itemName.replace(" ", ""));

                _stmt.execute();
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
    public void delete() {}

    @Override
    public void update(Donation newObj) {}

    public static ObservableList<Donation> search(String searchName) {
        ObservableList<Donation> list = FXCollections.observableArrayList();

        try (PreparedStatement stmt = DBConnection.getConnection()
            .prepareStatement("SELECT * FROM `donation` WHERE donatorName LIKE ?")) {
            stmt.setString(1, 
                String.format("%%%s%%", searchName));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Donation(rs.getInt("donationId"), 
                    rs.getString("donatorName"), 
                    rs.getString("donationItem"), 
                    rs.getInt("donationQuantity"),
                    rs.getDate("donationDate")));
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
