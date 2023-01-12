package ru.koreashop.koreashop_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.koreashop.koreashop_app.services.PersonDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    //Настройки авторизации и аутентификации
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
// TODO Корректно настроить доступы по ролям (уменьшить код)
        http
                .authorizeHttpRequests() //Настройка авторизации
                .requestMatchers("/prod/new", "/prod/{id}/edit").hasRole("ADMIN") // На данные страницы могут войти только пользователи с ролью ADMIN
                .requestMatchers("/prod/index", "/prod/{id}", "/prod/search", "/images/**", "/css/**", "/auth/login", "/auth/registration", "/error").permitAll() //Запросы доступные для любого пользователя
                .anyRequest().hasAnyRole("USER", "ADMIN")//Остальные запросы доступны только для этих ролей
                .and()
                .formLogin().loginPage("/auth/login") //путь к нашей странице с аутентификацией
                .loginProcessingUrl("/process_login") //???Функция loginProcessingUrl(“/process-login”) указывает настраиваемый URL-адрес для обработки аутентификации вместо URL-адреса по умолчанию /login. Мы должны указать пользовательский URL-адрес в атрибуте действия(action="/process_login") элемента HTML-формы файла просмотра.
                .defaultSuccessUrl("/prod/index", true) //путь после успешной авторизации
                .failureUrl("/auth/login?error") //После неуспешной авторизации
                .and()
                .logout() //разлогирование (выход из профиля)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/prod/index"); //путь после успешного разлогирования
        return http.build();
    }

    // Настраивает аутентификацию
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(personDetailsService);
    }

    //Шифрование паролей
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
