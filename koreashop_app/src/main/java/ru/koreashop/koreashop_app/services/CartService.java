package ru.koreashop.koreashop_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.koreashop.koreashop_app.models.Cart;
import ru.koreashop.koreashop_app.repositories.CartRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    //Поиск корзины по id
    public Cart findById(Long id) {
        Optional<Cart> foundCart = cartRepository.findById(id);
        return foundCart.orElse(null);
    }
}
