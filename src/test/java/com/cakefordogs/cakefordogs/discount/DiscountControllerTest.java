package com.cakefordogs.cakefordogs.discount;

import com.cakefordogs.cakefordogs.discount.dto.SaveDiscountDto;
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
class DiscountControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() {
        discountRepository.deleteAll();
    }


    @Test
    public void itShouldGetAllDiscounts() throws Exception {
        // given
        String url = "http://localhost" + port + "/api/v1/discounts";
        Discount discount = Discount.builder()
                .name("New Discount")
                .description("New Customer")
                .discountPercent(new BigDecimal("0.30"))
                .build();
        discountRepository.save(discount);

        // then
        mvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(discount.getName()));


    }

    @Test
    public void itShouldAddDiscount() throws Exception {
        // given
        SaveDiscountDto discountDto = SaveDiscountDto.builder()
                .name("New Discount")
                .description("New User")
                .discountPercent(new BigDecimal("0.30"))
                .build();
        String url = "http://localhost" + port + "/api/v1/discounts";

        // when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(discountDto)))
                .andExpect(status().isOk());

        // then
        List<Discount> all = discountRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(discountDto.getName());
        assertThat(all.get(0).getDescription()).isEqualTo(discountDto.getDescription());
        assertThat(all.get(0).getDiscountPercent()).isEqualTo(discountDto.getDiscountPercent());
    }


    @Test
    public void itShouldUpdateDiscount() throws Exception{
        // given
        Discount discount = Discount.builder()
                .name("New Discount")
                .description("New Customer")
                .discountPercent(new BigDecimal("0.30"))
                .build();
        discountRepository.save(discount);
        String url = "http://localhost" + port + "/api/v1/discounts/" + discount.getId() + "?name=updated name&description=updated description&discountPercent=0.90";

        // when
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        List<Discount> all = discountRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo("updated name");
        assertThat(all.get(0).getDescription()).isEqualTo("updated description");
        assertThat(all.get(0).getDiscountPercent()).isEqualTo(new BigDecimal("0.90"));

    }

    @Test
    public void itShouldDeleteDiscount() throws Exception{
        // given
        Discount discount = Discount.builder()
                .name("New Discount")
                .description("New Customer")
                .discountPercent(new BigDecimal("0.30"))
                .build();
        discountRepository.save(discount);
        String url = "http://localhost" + port + "/api/v1/discounts/" + discount.getId();

        // when
        mvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        List<Discount> all = discountRepository.findAll();
        assertThat(all.size()).isEqualTo(0);

    }

}