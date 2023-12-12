package app.models;

public class Item {
    private int _id;
    private String name;
    private int quantity;
    private String donator;

    public Item(int itemId, String itemName, int itemQuantity, String itemDonator) {
        _id = itemId;
        name = itemName;
        quantity = itemQuantity;
        donator = itemDonator;
    }

    public Item(String itemName, int itemQuantity, String itemDonator) {
        name = itemName;
        quantity = itemQuantity;
        donator = itemDonator;
    }

    public void setName() {

    }
}
