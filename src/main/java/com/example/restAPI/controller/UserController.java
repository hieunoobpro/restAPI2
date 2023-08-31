package com.example.restAPI.controller;

import com.example.restAPI.model.Page;
import com.example.restAPI.model.User;
import com.example.restAPI.response.AvatarRequest;
import com.example.restAPI.response.PasswordRequest;
import com.example.restAPI.response.UserRepository;
import com.example.restAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping
        public ResponseEntity<Page<User>> getUsers(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int limit) {
            Page<User> usersPage = userService.getUsers(page, limit);
            return ResponseEntity.ok(usersPage);
        }
    @GetMapping("/api/v1/search")
    public List<User> searchUsersByName(@RequestParam("name") String name) {
        return userService.searchUsersByName(name);
    }
    @GetMapping("/api/v1/users/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }
    @PostMapping("/api/v1/users")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable("id") Integer id, @ModelAttribute("course") User updatedUser) {
        User user1 = userService.getUserById(id);
        if (user1 == null) {
            return "/api/v1/users";
        }
        user1.setName(updatedUser.getName());
        user1.setPhone(updatedUser.getPhone());
        user1.setAddress(updatedUser.getAddress());

        userService.save(user1);
        return "redirect:/api/v1/users" + id; // Chuyển hướng về trang chi tiết khóa học sau khi cập nhật thành công
    }
    @DeleteMapping("/api/v1/users/{id}")
    public void deleteUser(@PathVariable int id) {
    }
    @PutMapping("/api/v1/users/{id}/update-avatar")
    public void updateAvatar(@PathVariable int id, @RequestBody AvatarRequest avatarRequest) {
    }
    @PutMapping("/api/v1/users/{id}/update-password")
    public void updatePassword(@PathVariable int id, @RequestBody PasswordRequest passwordRequest) {
            UserService sv = new UserService();
        String oldPassword = passwordRequest.getOldPassword();
        String newPassword = passwordRequest.getNewPassword();
        boolean isPasswordValid = userService.verifyOldPassword(id, oldPassword);
        if (!isPasswordValid) {
            throw new IllegalArgumentException("Mật khẩu cũ không chính xác");
        }
        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("Mật khẩu mới không được giống mật khẩu cũ");
        }
        userService.updatePasswordInDatabase(id, newPassword);
    }

    @PostMapping("/{id}/forgot-password")
    public ResponseEntity<String> forgotPassword(@PathVariable int id) {
        String newPassword = userService.generateNewPassword(id);
        userService.updatePasswordInDatabase(id, newPassword);
        return ResponseEntity.ok(newPassword);
    }
}

