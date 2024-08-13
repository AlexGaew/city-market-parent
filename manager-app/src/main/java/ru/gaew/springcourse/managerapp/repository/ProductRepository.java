package ru.gaew.springcourse.managerapp.repository;


import org.springframework.stereotype.Repository;
import ru.gaew.springcourse.managerapp.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {
    List<Product> findAll();
    Product save(Product product);

    Optional<Product> findById(int productId);

    void delete(int productId);
}
