package com.sephora.happyshop.data;

public class Product {

    public int id;
    public String name;
    public String category;
    public double price;
    public String imgUrl;
    public String description;
    public boolean underSale;

    public Product(int id, String name, String category, double price, String imgUrl, String description, boolean underSale) {
        this.id             = id;
        this.name           = name;
        this.category       = category;
        this.price          = price;
        this.imgUrl         = imgUrl;
        this.description    = description;
        this.underSale      = underSale;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", category='" + category + '\'' +
            ", price=" + price +
            ", imgUrl='" + imgUrl + '\'' +
            ", description='" + description + '\'' +
            ", underSale=" + underSale +
            '}';
    }
}
