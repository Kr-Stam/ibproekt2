package com.example.ibproekt2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shopping_cart")
@Data
public class ShoppingCart {
    @Column(name = "product_id")
    private long productId;
    @Column(name = "user_id")
    private long userId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
