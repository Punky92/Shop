package ru.koreashop.koreashop_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.koreashop.koreashop_app.models.CartDetail;

public interface CartDetailsRepository extends JpaRepository<CartDetail, Long> {
}
