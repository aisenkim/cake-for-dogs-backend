package com.cakefordogs.cakefordogs.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Aisen Kim
 *
 * Different Roles
 * 1. ROLE_SUPERADMIN
 * 2. ROLE_ADMIN
 * 3. ROLE_USER
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @Builder

    public Role(String name) {
        this.name = name;
    }
}

