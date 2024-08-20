package ru.gaew.springcourse.catalogueservice.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gaew.springcourse.catalogueservice.entity.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {


    //фильтрация объекта

    //Iterable<Product> findByTitleLikeIgnoreCase(String filter); // select * from catalogue.t_product where c_title ilike :filter

    //jpql
//    @Query(value = "select p from Product p where p.title ilike :filter")
    // для инициализации filter добавляем @Param('filter')
//    Iterable<Product> findByTitleLikeIgnoreCase(@Param("filter") String filter);

//    @Query(value = "select * from catalogue.t_product where c_title ilike :filter", nativeQuery = true) //нативный sql запрос
//    Iterable<Product> findByTitleLikeIgnoreCase(@Param("filter") String filter);

    @Query(name = "Product.findAllByTitle", nativeQuery = true)
        //сам запрос находится в entity @NamedQueries написан на jpql
    Iterable<Product> findByTitleLikeIgnoreCase(@Param("filter") String filter);


}
