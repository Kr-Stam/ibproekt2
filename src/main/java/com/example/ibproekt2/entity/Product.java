package com.example.ibproekt2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "product_manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private Category category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

//    public void setCategory(String category) {
//        Category cat = new Category();
//        String[] arr = category.split();
//    }
//
//
//    public void setManufacturer(String manufacturer) {
//        this.manufacturer = new Manufacturer();
//    }

    public Product() {
    }

    public Product(long id, String name, double price, Manufacturer manufacturer, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.category = category;
    }
}
