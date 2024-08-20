package ru.gaew.springcourse.catalogueservice.service;


import org.springframework.stereotype.Service;
import ru.gaew.springcourse.catalogueservice.entity.Product;

import java.util.Optional;

@Service
public interface ProductService {


    Iterable<Product> findAllProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(int productId);

    void updateProduct(Integer id, String title, String details);

    void deleteProduct(int productId);
}
