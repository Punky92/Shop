package ru.koreashop.koreashop_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.koreashop.koreashop_app.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByTitleStartingWith(String title);
    List<Product> findByTitleContainingIgnoreCase(String title);
}
