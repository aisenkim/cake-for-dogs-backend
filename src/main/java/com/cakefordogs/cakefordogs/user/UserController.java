package com.cakefordogs.cakefordogs.user;

import com.cakefordogs.cakefordogs.user.domain.Role;
import com.cakefordogs.cakefordogs.user.domain.User;
import com.cakefordogs.cakefordogs.user.dto.RoleToUserDto;
import com.cakefordogs.cakefordogs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/add-role")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserDto dto) {
        userService.addRoleToUser(dto.getUsername(), dto.getRoleName());
        return ResponseEntity.ok().build();
    }

    // TODO - REFRESH TOKEN METHOD
}
