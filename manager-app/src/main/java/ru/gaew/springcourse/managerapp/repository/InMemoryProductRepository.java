package ru.gaew.springcourse.managerapp.repository;


import org.springframework.stereotype.Repository;
import ru.gaew.springcourse.managerapp.entity.Product;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());


    @Override
    public List<Product> findAll() {

        return Collections.unmodifiableList(this.products); //возврат немодифицировааного списка
    }

    @Override
    public Product save(Product product) {
        product.setId(this.products.stream()
                .max(Comparator.comparingInt(Product::getId))
                .map(Product::getId)
                .orElse(0) + 1); //генерация id
        this.products.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(int productId) {
        //сравнение приходящего ID с ID продукта
        return products.stream().filter(product -> Objects.equals(product.getId(), productId)).findFirst();
    }

    @Override
    public void delete(int productId) {
        products.removeIf(product -> Objects.equals(product.getId(), productId));
    }


}
