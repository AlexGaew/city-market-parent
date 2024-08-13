package ru.gaew.springcourse.managerapp.service;


import org.springframework.stereotype.Service;
import ru.gaew.springcourse.managerapp.entity.Product;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    List<Product> findAllProducts();
    Product createProduct(String title, String details);
    Optional<Product> findProduct(int productId);
    void updateProduct(Integer id, String title, String details);

    void deleteProduct(int productId);
}
