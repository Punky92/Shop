package ru.koreashop.koreashop_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.koreashop.koreashop_app.models.Product;
import ru.koreashop.koreashop_app.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // TODO возможно нужна будет сортировка и пагинация(если товара будет много)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.orElse(null);
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void update(int id, Product updatedProduct) {
        updatedProduct.setId(id);
        productRepository.save(updatedProduct);
    }

    @Transactional
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchByTitle(String query) {
        return productRepository.findByTitleContainingIgnoreCase(query);
    }
}
