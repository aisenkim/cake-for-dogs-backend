package com.cakefordogs.cakefordogs.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(nullable = false)
    private String name;

   @Column(nullable = false)
    private String description;

   @Column(nullable = false)
    private BigDecimal price;

   @Builder
    public Product(String name, String description, BigDecimal price) {
       this.name = name;
       this.description = description;
       this.price = price;
   }

}
