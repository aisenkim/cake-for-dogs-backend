package com.cakefordogs.cakefordogs.discount;

import com.cakefordogs.cakefordogs.product.Product;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author Aisen Kim
 *
 * Entity class for Discount
 */
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

    @Column()
    @CreationTimestamp
    private Timestamp created_at;

    @Column()
    @CreationTimestamp
    private Timestamp modified_at;

    // One to many relationship with product
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> products;

    // TODO - Add timestamps

    @Builder
    public Discount(String name, String description, BigDecimal discountPercent) {
        this.name = name;
        this.description = description;
        this.discountPercent = discountPercent;
    }

}
