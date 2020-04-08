// Ratchanon Bualeesonsakun 5910401149 Section 2
// ratchanon.bua@ku.th
package com.example.merchandise_manager;

public class Merchandise {
    private String name;
    private double price;

    Merchandise(String name, double price) {
        this.name = name;
        this.price = price;
    }

    Merchandise() {
        this.name = "No Merchandise's Name";
        this.price = 0;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
