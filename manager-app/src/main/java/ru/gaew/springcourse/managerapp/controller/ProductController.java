package ru.gaew.springcourse.managerapp.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gaew.springcourse.managerapp.client.ProductsRestClient;
import ru.gaew.springcourse.managerapp.controller.payload.UpdateProductPayload;
import ru.gaew.springcourse.managerapp.entity.Product;
import ru.gaew.springcourse.managerapp.myException.BadRequestException;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("catalogue/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsRestClient productsRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product getProduct(@PathVariable("productId") int productId) {
        return this.productsRestClient.findProduct(productId).orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not.found"));
    }

    @GetMapping
    public String getProduct() {
        return "catalogue/products/productDetail";
    }

    @GetMapping("edit")
    public String getProductEditPage() {
        return "catalogue/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute("product") Product product,
                                UpdateProductPayload updateProductPayload,
                                Model model) {
        try {
            this.productsRestClient.updateProduct(product.id(), updateProductPayload.title(), updateProductPayload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", updateProductPayload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/edit";
        }
    }


    @PostMapping("delete")
    public String deleteProduct(@PathVariable("productId") int productId) {
        this.productsRestClient.deleteProduct(productId);
        return "redirect:/catalogue/products/list";
    }

    //обработка ошибок
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model, HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", this.messageSource.getMessage(e.getMessage(), new Object[0], e.getMessage(), locale));
        return "errors/404";
    }
}
