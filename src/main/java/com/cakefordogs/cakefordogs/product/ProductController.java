package com.cakefordogs.cakefordogs.product;

import com.cakefordogs.cakefordogs.product.dto.ProductSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public void registerNewProduct(@RequestBody ProductSaveRequestDto productDto) {
        productService.addNewProduct(productDto);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping(path = "{productId}")
    public void updateProduct(
            @PathVariable("productId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false)BigDecimal price
            ) {
      productService.updateProduct(id, name, description, price);
    }

}
