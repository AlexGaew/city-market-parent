package ru.gaew.springcourse.managerapp.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gaew.springcourse.managerapp.controller.payload.NewProductPayload;
import ru.gaew.springcourse.managerapp.entity.Product;
import ru.gaew.springcourse.managerapp.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

    private final ProductService productService;


    @GetMapping("list")
    public String getProductsList(Model model) {
        List<Product> products = this.productService.findAllProducts();
        model.addAttribute("products", products);
        return "catalogue/products/productList";
    }

    @GetMapping("create")
    public String getNewProductsPage() {
        return "catalogue/products/newProduct";
    }

    @PostMapping("create")
    public String createProduct(@Valid NewProductPayload newProductPayload, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", newProductPayload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "catalogue/products/newProduct";

        } else {

            Product product = this.productService.createProduct(newProductPayload.title(), newProductPayload.details());
            return "redirect:/catalogue/products/%d".formatted(product.getId()); // редирект на страницу описания товара после создания товара
        }
    }


}
