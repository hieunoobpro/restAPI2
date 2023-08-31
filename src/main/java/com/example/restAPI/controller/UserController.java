package com.example.restAPI.controller;
import com.example.restAPI.model.Pages;
import com.example.restAPI.model.User;
import com.example.restAPI.response.AvatarRequest;
import com.example.restAPI.response.PasswordRequest;
import com.example.restAPI.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/v1/users")
public class UserController {
        private UserService userService;


    @GetMapping
    public ResponseEntity<Pages> getUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        List<User> userList = userService.getUsers(page, limit);
        int totalUsers = userService.getTotalUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / limit);

        Pages response = new Pages(userList, page, limit, totalPages);
        return ResponseEntity.ok(response);
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
    @PutMapping("/{id}/update-password")
    public ResponseEntity<Void> updatePassword(@PathVariable int id, @RequestBody PasswordRequest request) {
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();

        userService.changePassword(id, oldPassword, newPassword);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/forgot-password")
    public ResponseEntity<String> forgotPassword(@PathVariable int id) {
        String newPassword = userService.generateNewPassword(id);
        userService.updatePasswordInDatabase(id, newPassword);
        return ResponseEntity.ok(newPassword);
    }
}

