package com.cakefordogs.cakefordogs.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addNewProduct(Product product) {
        Optional<Product> productOptional = productRepository.findProductByName(product.getName());

        if(productOptional.isPresent()) {
            throw new IllegalStateException("Product Already Exists");
        }

        productRepository.save(product);
    }
}
