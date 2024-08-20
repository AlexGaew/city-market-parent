package ru.gaew.springcourse.managerapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gaew.springcourse.managerapp.client.ProductsRestClient;
import ru.gaew.springcourse.managerapp.controller.payload.NewProductPayload;
import ru.gaew.springcourse.managerapp.entity.Product;
import ru.gaew.springcourse.managerapp.myException.BadRequestException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

    private final ProductsRestClient productsRestClient;


    @GetMapping("list")
    public String getProductsList(Model model, @RequestParam(value = "filter", required = false) String filter) {
        List<Product> products = this.productsRestClient.findAllProducts(filter);
        model.addAttribute("products", products);
        model.addAttribute("filter", filter);
        return "catalogue/products/productList";
    }

    @GetMapping("create")
    public String getNewProductsPage() {
        return "catalogue/products/newProduct";
    }

    @PostMapping("create")
    public String createProduct(NewProductPayload newProductPayload, Model model) {
        try {
            Product product = this.productsRestClient.createProduct(newProductPayload.title(), newProductPayload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id()); // редирект на страницу описания товара после создания товара
        } catch (BadRequestException exception) {
            model.addAttribute("payload", newProductPayload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/newProduct";
        }

    }

}
