package com.cakefordogs.cakefordogs.cartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column()
    @CreationTimestamp
    private Timestamp created_at;

    @Column()
    @CreationTimestamp
    private Timestamp modified_at;

    @Builder
    public CartItem(int quantity) {
        this.quantity = quantity;
    }
}
