package com.cakefordogs.cakefordogs.user.repository;

import com.cakefordogs.cakefordogs.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
