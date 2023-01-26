package ru.koreashop.koreashop_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.koreashop.koreashop_app.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
