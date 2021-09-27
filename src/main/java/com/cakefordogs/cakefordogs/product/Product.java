package com.cakefordogs.cakefordogs.product;

import lombok.*;

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

    // TODO - Create relationship with discount

    // TODO - Add timestamps
//   @CreatedDate
//   @Column(name = "createdAt", nullable = false, updatable = false)
//   private Date createdAt;
//
//   @LastModifiedDate
//   @Column(name = "modifiedAt")
//   private LocalDateTime modifiedAt;

    @Builder
    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
