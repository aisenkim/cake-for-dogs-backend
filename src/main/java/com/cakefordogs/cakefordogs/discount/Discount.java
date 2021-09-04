package com.cakefordogs.cakefordogs.discount;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private BigDecimal discountPercent;


    // TODO - Add timestamps

    @Builder
    public Discount(String name, String description, BigDecimal discountPercent) {
        this.name = name;
        this.description = description;
        this.discountPercent = discountPercent;
    }

}
