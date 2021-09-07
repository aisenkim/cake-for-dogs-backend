package com.cakefordogs.cakefordogs.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.*;

/**
 * @author Aisen Kim
 *
 * Different Roles
 * 1. SUPER_ADMIN
 * 2. ADMIN
 * 3. USER
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String name;
}

