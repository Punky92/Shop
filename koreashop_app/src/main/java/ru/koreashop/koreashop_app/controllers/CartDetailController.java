package ru.koreashop.koreashop_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.koreashop.koreashop_app.models.Cart;
import ru.koreashop.koreashop_app.models.Product;
import ru.koreashop.koreashop_app.services.CartDetailsService;
import ru.koreashop.koreashop_app.services.CartService;
import ru.koreashop.koreashop_app.services.ProductService;


@Controller
public class CartDetailController {

    private final CartDetailsService cartDetailsService;
    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public CartDetailController(CartDetailsService cartDetailsService, ProductService productService, CartService cartService) {
        this.cartDetailsService = cartDetailsService;
        this.productService = productService;
        this.cartService = cartService;
    }

    //Метод добавления продукта в корзину
    @PostMapping("/cartDetail/add/{id}")
    public String addCartDetail(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        cartDetailsService.save(product);
        return "redirect:/prod/index";
    }

    //Метод удаления продукта из корзины
    @DeleteMapping("/cartDetail/delete/{id}")
    public String deleteCartDetail(@PathVariable("id") Long id) {
        cartDetailsService.delete(id);
        return "redirect:/cart/getCartById";
    }

    //Метод очистки корзины
    @DeleteMapping("/cartDetail/deleteAll/{cartId}")
    public String deleteAllCartDetails(@PathVariable("cartId") Long id) {
        Cart cart = cartService.findById(id);
        cartDetailsService.deleteAll(cart);
        return "redirect:/cart/getCartById";
    }

    //Метод увеличение кол-ва товара в корзине
    @GetMapping("/cartDetail/plus/{id}")
    public String plus(@PathVariable("id") Long id) {
        cartDetailsService.cartDetailPlus(id);
        return "redirect:/cart/getCartById";
    }

    //Метод уменьшение кол-ва товара в корзине
    @GetMapping("/cartDetail/minus/{id}")
    public String minus(@PathVariable("id") Long id) {
        cartDetailsService.cartDetailMinus(id);
        return "redirect:/cart/getCartById";
    }
}
