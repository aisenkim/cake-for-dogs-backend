package com.cakefordogs.cakefordogs.cartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Aisen Kim
 *
 * Entity class for items in cart
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp modified_at;

    @Builder
    public CartItem(int quantity, Timestamp created_at, Timestamp modified_at) {
        this.quantity = quantity;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }
}
