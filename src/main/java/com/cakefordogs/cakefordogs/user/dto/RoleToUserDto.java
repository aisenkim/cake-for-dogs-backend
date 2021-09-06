package com.cakefordogs.cakefordogs.user.dto;

import lombok.Data;

/**
 * For adding new role to a user
 */
@Data
public class RoleToUserDto {
    private String username;
    private String roleName;
}
