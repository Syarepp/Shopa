package com.example.shoppa;

public class CartItem {
    private int id;
    private String watchName;
    private double watchPrice;
    private String watchDescription;
    private int watchImage;
    private int quantity;

    public CartItem(int id, String watchName, double watchPrice, String watchDescription, int watchImage, int quantity) {
        this.id = id;
        this.watchName = watchName;
        this.watchPrice = watchPrice;
        this.watchDescription = watchDescription;
        this.watchImage = watchImage;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getWatchName() { return watchName; }
    public double getWatchPrice() { return watchPrice; }
    public String getWatchDescription() { return watchDescription; }
    public int getWatchImage() { return watchImage; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() {
        return watchPrice * quantity;
    }
}