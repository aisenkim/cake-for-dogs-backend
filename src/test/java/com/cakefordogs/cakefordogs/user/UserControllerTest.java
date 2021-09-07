package com.cakefordogs.cakefordogs.user;

import com.cakefordogs.cakefordogs.product.Product;
import com.cakefordogs.cakefordogs.product.ProductRepository;
import com.cakefordogs.cakefordogs.user.domain.Role;
import com.cakefordogs.cakefordogs.user.domain.User;
import com.cakefordogs.cakefordogs.user.dto.RoleToUserDto;
import com.cakefordogs.cakefordogs.user.repository.RoleRepository;
import com.cakefordogs.cakefordogs.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void itShouldGetUsersAndStatusOfOK() throws Exception {
        // given
        User newUser = User.builder()
                .username("user1")
                .password("password")
                .name("User1")
                .email("user1@gmail.com")
                .build();

        userRepository.save(newUser);

        String url = "http://localhost:" + port + "/api/v1/users";

        // when
        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)));

        // then
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getUsername()).isEqualTo("user1");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void itShouldSaveUserAndGetStatusOfCreated() throws Exception {
        // given
        User newUser = User.builder()
                .username("user1")
                .password("password")
                .name("User1")
                .email("user1@gmail.com")
                .build();

        String url = "http://localhost:" + port + "/api/v1/user/save";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isCreated());

        // then
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getUsername()).isEqualTo("user1");
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void itShouldThrow403SavingUsersWithWrongAuthority() throws Exception {
        // given
        User newUser = User.builder()
                .username("user1")
                .password("password")
                .name("User1")
                .email("user1@gmail.com")
                .build();

        String url = "http://localhost:" + port + "/api/v1/user/save";

        // when
        // then
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void itShouldSaveRoleAndStatusOfCreated() throws Exception {
        // given
        Role role = new Role(1L, "ROLE_ADMIN");

        String url = "http://localhost:" + port + "/api/v1/role/save";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(role)))
                .andExpect(status().isCreated());

        // then
        List<Role> roles = roleRepository.findAll();
        assertThat(roles.get(0).getName()).isEqualTo("ROLE_ADMIN");

    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void itShouldAddRoleToUser() throws Exception {
        // given
        Role role = new Role(1L, "ROLE_ADMIN");

        User newUser = User.builder()
                .username("user1")
                .password("password")
                .name("User1")
                .email("user1@gmail.com")
                .build();

        RoleToUserDto roleToUserDto = new RoleToUserDto(newUser.getUsername(), role.getName());

        roleRepository.save(role);
        userRepository.save(newUser);

        String url = "http://localhost:" + port + "/api/v1/role/add-role";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(roleToUserDto)))
                .andExpect(status().isOk());

        User foundUser = userRepository.findByUsername(newUser.getUsername());
        assertThat(foundUser.getRoles()).contains(role);
    }
}