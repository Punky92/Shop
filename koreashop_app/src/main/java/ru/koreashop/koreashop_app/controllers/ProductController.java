package ru.koreashop.koreashop_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.koreashop.koreashop_app.models.Product;
import ru.koreashop.koreashop_app.services.ProductService;

@Controller
@RequestMapping("/prod")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    //Получаем все продукты и передаём на представление
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("products", productService.findAll());
        return "prod/index";
    }

    //Получаем один продукт по id и передаем в представление
    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "prod/show";
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Product product) {
        return "prod/new";
    }

    @PostMapping()
    public String createProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/prod";
    }

    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "prod/edit";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute("product") Product product) {
        productService.update(id, product);
        return "redirect:/prod";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
        return "redirect:/prod";
    }

    @GetMapping("/search")
    public String searchProduct() {
        return "prod/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("products", productService.searchByTitle(query));
        return "prod/search";
    }
}
