package ru.aikam.task.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Getter
    @Setter
    @Column(name = "product_name")
    private String productName;
    @Getter
    @Setter
    private int expense;
    @Getter
    @Setter
    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases;

    public Product(String productName, int expense) {
        this.productName = productName;
        this.expense = expense;
        purchases = new ArrayList<>();
    }

}
