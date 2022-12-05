package ru.koreashop.koreashop_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.koreashop.koreashop_app.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
