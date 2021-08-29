//package com.cakefordogs.cakefordogs.product;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Configuration
//public class ProductConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(ProductRepository repository) {
//        return args -> {
//            Product cake1 = new Product(
//                    "cake1",
//                    "This is a cake that is made of chicken base",
//                    new BigDecimal("39.00")
//            );
//            Product cake2 = new Product(
//                    "cake2",
//                    "This is a cake that is made of beef base",
//                    new BigDecimal("43.99")
//            );
//
//            repository.saveAll(List.of(cake1, cake2));
//        };
//    }
//}
