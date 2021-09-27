package com.cakefordogs.cakefordogs.shoppingSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class ShoppingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private BigDecimal total;

    @Column()
    @CreationTimestamp
    private Timestamp created_at;

    @Column()
    @CreationTimestamp
    private Timestamp modified_at;

}
