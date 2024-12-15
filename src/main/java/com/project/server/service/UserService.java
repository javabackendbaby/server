package com.project.server.service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.project.server.model.User;
import com.project.server.dto.UserResponse;
import com.project.server.model.Role;
import com.project.server.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (repository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("User with this phone number exists");
        }
        return save(user);
    }

    /**
     * Получение пользователя по номеру телефона
     *
     * @return пользователь
     */
    public User getByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

    }

    /**
     * Получение пользователя по номеру телефона
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * для тестов
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    public List<UserResponse> getPhoneBook(List<String> phoneNumbers) {
        List<UserResponse> phoneBook = new ArrayList<UserResponse>();
        User user;
        UserResponse userResponse;
        for(String number : phoneNumbers){
            try {
                user = getByPhoneNumber(number);
                userResponse = UserResponse.builder()
                    .phoneNumber(number)
                    .username(user.getUsername())
                    .build();
                phoneBook.add(userResponse);
            }
            catch(UsernameNotFoundException e) {}           
        }
        return phoneBook;
    }
}