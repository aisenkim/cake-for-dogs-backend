package com.cakefordogs.cakefordogs.product;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
