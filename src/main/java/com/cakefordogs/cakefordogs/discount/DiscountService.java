package com.cakefordogs.cakefordogs.discount;

import com.cakefordogs.cakefordogs.discount.dto.SaveDiscountDto;
import com.cakefordogs.cakefordogs.discount.exception.DiscountNotFoundException;
import com.cakefordogs.cakefordogs.product.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

    public void addDiscount(SaveDiscountDto saveDiscountDto) {
        Optional<Discount> discountOptional = discountRepository.findDiscountByName(saveDiscountDto.getName());

        if (discountOptional.isPresent()) {
            throw new BadRequestException("Discount already exists");
        }

        discountRepository.save(saveDiscountDto.toEntity());
    }

    @Transactional
    public void updateDiscount(Long id, String name, String description, BigDecimal discountPercent) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new DiscountNotFoundException("Discount of id: " + id + " does not exist"));

        System.out.println("id: " + id + " name: " + name);

        if (discountPercent != null && discountPercent.compareTo(new BigDecimal("0.00")) > 0 && !Objects.equals(discountPercent, discount.getDiscountPercent())) {
            discount.setDiscountPercent(discountPercent);
        }

        if (description != null && description.length() > 0 && !Objects.equals(discount.getDescription(), description)) {
            discount.setDescription(description);
        }

        if (name != null && name.length() > 0 && !Objects.equals(discount.getName(), name)) {
            discount.setName(name);
        }

        // set modified_date
        discount.setModified_at(new Timestamp(System.currentTimeMillis()));
    }

    public void deleteDiscount(Long id) {
        Optional<Discount> discountToDelete = discountRepository.findById(id);

        if(discountToDelete.isEmpty()) {
            throw new DiscountNotFoundException("Discount does not exist");
        }

        discountRepository.deleteById(id);
    }
}
