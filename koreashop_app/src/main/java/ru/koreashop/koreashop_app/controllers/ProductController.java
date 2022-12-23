package ru.koreashop.koreashop_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.koreashop.koreashop_app.models.Product;
import ru.koreashop.koreashop_app.services.ProductService;

import java.io.IOException;

@Controller
@RequestMapping("/prod")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Получаем все продукты и передаём на представление
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("products", productService.findAll());
        return "prod/index";
    }

    //Получаем один продукт по id и передаем в представление
    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "prod/show";
    }

    //Метод на форму добавления нового продукта
    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Product product) {
        return "prod/new";
    }

    //Метод создания нового продукта
    @PostMapping()
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Product product) throws IOException {
        productService.save(product, file1, file2, file3);
        return "redirect:/prod/index";
    }

    //Метод на форму редактирования продукта
    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "prod/edit";
    }

    //Метод редактирования продукта
    @PatchMapping("/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute("product") Product product) {
        productService.update(id, product);
        return "redirect:/prod/index";
    }

    //Метод удаления продукта
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
        return "redirect:/prod/index";
    }

    //Метод на форму поиска продукта
    @GetMapping("/search")
    public String searchProduct() {
        return "prod/search";
    }

    //Метод на поиск продукта по заголовку
    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("products", productService.searchByTitle(query));
        return "prod/search";
    }
}
