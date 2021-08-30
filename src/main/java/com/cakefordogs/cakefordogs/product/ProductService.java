package com.cakefordogs.cakefordogs.product;

import com.cakefordogs.cakefordogs.product.dto.ProductSaveRequestDto;
import com.cakefordogs.cakefordogs.product.exception.BadRequestException;
import com.cakefordogs.cakefordogs.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addNewProduct(ProductSaveRequestDto saveRequestDto) {
        Optional<Product> productOptional = productRepository.findProductByName(saveRequestDto.getName());

        if (productOptional.isPresent()) {
            throw new BadRequestException("Product Already Exists");
        }

        productRepository.save(saveRequestDto.toEntity());
    }

    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product with id: " + id + " does not exist.");
        }

        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(Long id, String name, String description, BigDecimal price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(product.getName(), name)) {
            product.setName(name);
        }

        if (description != null && description.length() > 0 && !Objects.equals(product.getDescription(), description)) {
            product.setDescription(description);
        }

        if (price != null && price.compareTo(new BigDecimal("0.00")) > 0 && !Objects.equals(product.getPrice(), price)) {
            product.setPrice(price);
        }
    }
}
