package ru.koreashop.koreashop_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.koreashop.koreashop_app.models.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
