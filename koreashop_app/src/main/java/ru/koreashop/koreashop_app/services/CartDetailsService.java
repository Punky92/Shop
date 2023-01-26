package ru.koreashop.koreashop_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.koreashop.koreashop_app.models.Cart;
import ru.koreashop.koreashop_app.models.CartDetail;
import ru.koreashop.koreashop_app.models.Person;
import ru.koreashop.koreashop_app.models.Product;
import ru.koreashop.koreashop_app.repositories.CartDetailsRepository;
import ru.koreashop.koreashop_app.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartDetailsService {

    private final CartDetailsRepository cartDetailsRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public CartDetailsService(CartDetailsRepository cartDetailsRepository, PeopleRepository peopleRepository) {
        this.cartDetailsRepository = cartDetailsRepository;
        this.peopleRepository = peopleRepository;
    }

    //Добавление продукта в корзину
    @Transactional
    public void save(Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Person person = peopleRepository.findByEmail(username).get();

        Cart cart = person.getCart();
        List<CartDetail> cartDetails = cart.getCartDetails();

        for (int i = 0; i < cartDetails.size(); i++) {
            CartDetail cartDetail = cartDetails.get(i);
            if (product.getId() == (cartDetail.getProduct().getId())) {
                cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                cartDetail.setPrice(cartDetail.getQuantity() * cartDetail.getProduct().getPrice());
                cart.setSum(cart.getSum() + cartDetail.getProduct().getPrice());
                cartDetailsRepository.save(cartDetail);
                return;
            }
        }
        CartDetail cartDetail1 = new CartDetail();
        cartDetail1.setQuantity(1);
        cartDetail1.setProduct(product);
        cartDetail1.setPrice(product.getPrice());
        cart.setSum(cart.getSum() + cartDetail1.getPrice());
        cartDetail1.setCart(cart);
        cartDetailsRepository.save(cartDetail1);
    }

    //Удаление продукта из корзины
    @Transactional
    public void delete(Long id) {
        Optional<CartDetail> byId = cartDetailsRepository.findById(id);
        if (byId.isPresent()) {
            Cart cart = byId.get().getCart();
            double price = byId.get().getPrice();
            cart.setSum(cart.getSum() - price);
        }
        cartDetailsRepository.deleteById(id);
    }

    //Очистка корзины
    @Transactional
    public void deleteAll(Cart cart) {
        cart.setSum(0);
        cartDetailsRepository.deleteAll(cart.getCartDetails());
    }

    //Увеличение кол-ва товара в корзине
    @Transactional
    public void cartDetailPlus(Long id) {
        Optional<CartDetail> cartDetailsRepositoryById = cartDetailsRepository.findById(id);
        if (cartDetailsRepositoryById.isPresent()) {
            CartDetail cartDetail = cartDetailsRepositoryById.get();
            Cart cart = cartDetail.getCart();
            cartDetail.setQuantity(cartDetail.getQuantity() + 1);
            cartDetail.setPrice(cartDetail.getQuantity() * cartDetail.getProduct().getPrice());
            cart.setSum(cart.getSum() + cartDetail.getProduct().getPrice());
            cartDetailsRepository.save(cartDetail);
        }
    }

    //Уменьшение кол-ва товара в корзине
    @Transactional
    public void cartDetailMinus(Long id) {
        Optional<CartDetail> cartDetailsRepositoryById = cartDetailsRepository.findById(id);
        if (cartDetailsRepositoryById.isPresent()) {
            CartDetail cartDetail = cartDetailsRepositoryById.get();
            Cart cart = cartDetail.getCart();
            cartDetail.setQuantity(cartDetail.getQuantity() - 1);
            cartDetail.setPrice(cartDetail.getQuantity() * cartDetail.getProduct().getPrice());
            cart.setSum(cart.getSum() - cartDetail.getProduct().getPrice());
            cartDetailsRepository.save(cartDetail);
        }
    }
}
