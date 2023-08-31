package com.example.restAPI.service;

import com.example.restAPI.model.Page;
import com.example.restAPI.model.User;
import com.example.restAPI.response.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public Page<User> getUsers(int page, int limit) {
        int offset = (page - 1) * limit;

        List<User> users = userRepository.getUsers(offset, limit);
        int totalUsers = userRepository.getTotalUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / limit);

        Page<User> usersPage = new Page<>();
        usersPage.setData(users);
        usersPage.setCurrentPage(page);
        usersPage.setSize(limit);
        usersPage.setTotalPage(totalPages);

        return usersPage;
    }
    public List<User> searchUsersByName(String name) {
        return userRepository.searchUsersByName(name);
    }
    public User getUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }
    public User createUser(User user) {
        // Sinh ngẫu nhiên ID
        user.setId(generateRandomId());

        return userRepository.save(user);
    }
    public User save(User user) {

        return userRepository.save(user);
    }

    private int generateRandomId() {
        int minId = 1;
        int maxId = 1000000;
        int randomId;

        do {
            randomId = (int) (Math.random() * (maxId - minId + 1)) + minId;
        } while (userRepository.existsById(randomId));

        return randomId;
    }

}
