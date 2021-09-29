package com.cakefordogs.cakefordogs.product;

import com.cakefordogs.cakefordogs.discount.Discount;
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

    // TODO - Create [Many To One] relationship -> Many product , One discount
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "discount_id")
    private Discount discount;


    // TODO - Add timestamps
//   @CreatedDate
//   @Column(name = "createdAt", nullable = false, updatable = false)
//   private Date createdAt;
//
//   @LastModifiedDate
//   @Column(name = "modifiedAt")
//   private LocalDateTime modifiedAt;

    @Builder
    public Product(String name, String description, BigDecimal price, Discount discount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

}
