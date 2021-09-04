package com.cakefordogs.cakefordogs.product;

import com.cakefordogs.cakefordogs.product.dto.ProductSaveRequestDto;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void productGetsAdded() throws Exception {
        // given
        String name = "Pork Cake";
        String description = "New port cake is on sale";
        ProductSaveRequestDto requestDto = ProductSaveRequestDto.builder()
                .name(name)
                .description(description)
                .price(new BigDecimal("15.00"))
                .build();

        String url = "http://localhost:" + port + "/api/v1/products";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Product> all = productRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getDescription()).isEqualTo(description);

    }

    @Test
    public void itShouldUpdateProducts() throws Exception {
        // given
        String name = "Pork Cake";
        String description = "New port cake is on sale";
        ProductSaveRequestDto requestDto = ProductSaveRequestDto.builder()
                .name(name)
                .description(description)
                .price(new BigDecimal("15.00"))
                .build();

        Product savedProduct = productRepository.save(requestDto.toEntity());

        Long productToUpdateId = savedProduct.getId();

        String url = "http://localhost:" + port + "/api/v1/products/" + productToUpdateId + "?name=Updated Name&description=Updated Description&price=99.99";

        // when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        List<Product> all = productRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo("Updated Name");
        assertThat(all.get(0).getDescription()).isEqualTo("Updated Description");

    }

    @Test
    public void itShouldDeleteProduct() throws Exception {
        System.out.println("Product Repository: " + productRepository.findAll());
        // given
        ProductSaveRequestDto productSaveRequestDto = ProductSaveRequestDto.builder()
                .name("Broccoli Cake")
                .description("New Cake")
                .price(new BigDecimal("23.32"))
                .build();
        Product product = productSaveRequestDto.toEntity();
        productRepository.save(product);

        String url = "http://localhost:" + port + "/api/v1/products/" + product.getId();

        // when
        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getAllProductsSample() throws Exception {
        // given
        String url = "http://localhost:" + port + "/api/v1/products";
        Product product = Product.builder()
                .name("Pork Cake")
                .description("New pork cake is on sale")
                .price(new BigDecimal("13.58"))
                .build();

        productRepository.save(product);

        // when
        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Pork Cake"));

        List<Product> all = productRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo("Pork Cake");
    }


}