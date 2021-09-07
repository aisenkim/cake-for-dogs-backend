package com.cakefordogs.cakefordogs.user.dto;

import lombok.Data;

/**
 * For adding new role to a user
 */
@Data
public class RoleToUserDto {
    private final String username;
    private final String roleName;
}
