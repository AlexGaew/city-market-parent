package ru.gaew.springcourse.managerapp.client;

import ru.gaew.springcourse.managerapp.entity.Product;

import java.util.List;
import java.util.Optional;



public interface ProductsRestClient {

    List<Product> findAllProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(int productId);

    void updateProduct(Integer id, String title, String details);

    void deleteProduct(int productId);


}
