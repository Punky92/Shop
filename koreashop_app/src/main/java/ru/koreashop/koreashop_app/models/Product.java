package ru.koreashop.koreashop_app.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    private int previewImageId;

    private LocalDateTime dateOfCreated;

    public Product() {
    }

    public Product(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.images = new ArrayList<>();  //инициализация списка картинок в конструкторе, иначе при попытке добавить в список будет NullPointerException
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(int previewImageId) {
        this.previewImageId = previewImageId;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    @PrePersist  //@PrePersist — аннотация используется для указания метода обратного вызова, который срабатывает до того, как объект будет сохранен.
    private void init() { //значит этот метод выполнится перед созданием объекта автоматически
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToProduct(Image image) { //Добавляем картинку к продукту(сокращаем код сохранения продукта в сервисе)
        image.setProduct(this);
        images.add(image);
    }
}
