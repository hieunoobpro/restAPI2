package com.example.restAPI.service;

import com.example.restAPI.model.Page;
import com.example.restAPI.model.User;
import com.example.restAPI.response.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public boolean verifyOldPassword(int id, String oldPassword) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }
        User user = userOptional.get();
        String storedPassword = user.getPassword();

        return storedPassword.equals(oldPassword);
    }


    public void updatePasswordInDatabase(int id, String newPassword) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }

        User user = userOptional.get();
        user.setPassword(newPassword);

        // Lưu người dùng đã cập nhật vào cơ sở dữ liệu hoặc nguồn dữ liệu tương ứng
        userRepository.save(user);
    }

    public String generateNewPassword(int id) {
        String newPassword = generateRandomPassword();
        // Lưu mật khẩu mới vào cơ sở dữ liệu hoặc nguồn dữ liệu tương ứng
        updatePasswordInDatabase(id, newPassword);
        return newPassword;
    }
    public String generateRandomPassword() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            newPassword.append(characters.charAt(index));
        }

        return newPassword.toString();
    }

    public void changePassword(int id, String oldPassword, String newPassword) {
        User user = getUserById(id);
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Mật khẩu cũ không chính xác");
        }

        if (user.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("Mật khẩu mới không được giống mật khẩu cũ");
        }
        updatePasswordInDatabase(id, newPassword);
    }

}
