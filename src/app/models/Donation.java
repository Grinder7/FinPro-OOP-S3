package app.models;

// Interface(s)
import app.interfaces.DBMethods;

public class Donation implements DBMethods<Donation> {
    protected int _id;
    protected String donatorName;
    protected String itemName;
    protected int itemQuantity;

    public Donation(int id, String donatorName, String itemName, int itemQuantity) {
        _id = id;
        this.donatorName = donatorName;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public Donation(String donatorName, String itemName, int itemQuantity) {
        this.donatorName = donatorName;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public void setDonatorName(String donatorName) {this.donatorName = donatorName;}

    public void setItemName(String itemName) {this.itemName = itemName;}

    public void setItemQuantity(int itemQuantity) {this.itemQuantity = itemQuantity;}

    public int getId() {return _id;}

    public String getDonatorName() {return donatorName;}

    public String getItemName() {return itemName;}

    public int getItemQuantity() {return itemQuantity;}

    @Override
    public void insert() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void update(Donation newObj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
