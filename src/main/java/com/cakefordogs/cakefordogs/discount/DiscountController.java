package com.cakefordogs.cakefordogs.discount;

import com.cakefordogs.cakefordogs.discount.dto.SaveDiscountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<Discount> getDiscounts() {
        return discountService.getDiscounts();
    }

    @PostMapping
    public void addDiscount(@RequestBody SaveDiscountDto saveDiscountDto) {
        discountService.addDiscount(saveDiscountDto);
    }

    @PutMapping(path="{discountId}")
    public void updateDiscount(
            @PathVariable("discountId") Long id,
            @RequestParam(required=false) String name,
            @RequestParam(required=false) String description,
            @RequestParam(required = false) BigDecimal discountPercent
    ) {
        discountService.updateDiscount(id, name, description, discountPercent);
    }

    @DeleteMapping(path = "{discountId}")
    public void deleteDiscount(@PathVariable("discountId") Long id) {
        discountService.deleteDiscount(id);
    }

}
