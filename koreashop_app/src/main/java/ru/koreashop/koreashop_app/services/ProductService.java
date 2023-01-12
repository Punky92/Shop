package ru.koreashop.koreashop_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.koreashop.koreashop_app.models.Image;
import ru.koreashop.koreashop_app.models.Product;
import ru.koreashop.koreashop_app.repositories.ProductRepository;

import java.io.IOException;
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
    //Список всех продуктов
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    //Конктретный продукт по id
    public Product findById(int id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.orElse(null);
    }

    //Сохранение нового продукта
    @Transactional
    public void save(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        // TODO придумать как сократить код
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            // TODO возможен код для сжатия файла
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId()); //Устанавливаем id для превьюшной картинки
        productRepository.save(product);
    }

    //Обновление продукта(редактирование)
    @Transactional
    public void update(int id, Product updatedProduct) {
        Product product = productRepository.findById(id).get();
        updatedProduct.setId(id);
        updatedProduct.setDateOfCreated(product.getDateOfCreated());  // Пересохраняю дату создания, так как не передаю ее в форме обновления (без этого, поле стало бы null)
        updatedProduct.setPreviewImageId(product.getPreviewImageId()); // Пересохраняю id превью-картинки, так как не передаю ее в форме обновления (без этого, поле стало бы null)
        productRepository.save(updatedProduct);
    }

    //Удаление продукта
    @Transactional
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    //Поиск продукта по названию
    public List<Product> searchByTitle(String query) {
        return productRepository.findByTitleContainingIgnoreCase(query);
    }

    //Метод для преобразование картинки к модели Image
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
}
