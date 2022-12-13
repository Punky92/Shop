package ru.koreashop.koreashop_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role")
    private String role;

    @NotEmpty(message = "Поле email не должно быть пустым")
    @Email(message = "Email адрес указан не корректно")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Поле 'пароль' не должно быть пустым")
    @Size(min = 6, message = "Пароль должен содержать не менее 6 символов")
    @Column(name = "password")
    private String password;

    @Size(min = 2, max = 30, message = "Имя должно быть от 2 до 30 символов длинной")
    @Column(name = "name")
    private String name;

    @Size(min = 2, max = 30, message = "Фамилия должна быть от 2 до 30 символов длинной")
    @Column(name = "surname")
    private String surname;

    // TODO Сделать выборку пола в форме?
    @Column(name = "gender")
    private String gender;

    // TODO Сделать выборку дня рождения из календаря?
    //@Temporal(TemporalType.DATE) Для отоброжения даты
    //@DateTimeFormat(pattern = "dd/MM/yyyy") - для нужного отображения даты
    //@Size(max = 4, message = "Год рождения должен содержать 4 цифры")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    // TODO Сделать валидацию для номера телефона (сделать в форме)
    @Column(name = "phone_number")
    private int phoneNumber;

    // TODO Сделать выборку городов в форме?
    @Size(min = 2, max = 30, message = "Название города должно быть от 2 до 30 символов длинной")
    @Column(name = "city")
    private String city;

    public Person() {
    }

    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
