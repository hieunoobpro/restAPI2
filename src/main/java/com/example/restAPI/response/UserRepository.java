package com.example.restAPI.response;

import com.example.restAPI.database.UserDB;
import com.example.restAPI.model.Page;
import com.example.restAPI.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class UserRepository {
    private User user;
    private Page page;
    private List<User> userList;

    public UserRepository() {
        this.userList = new ArrayList<>();
    }

    public List<User> getUsers(int offset, int limit) {
            List<User> userList = UserDB.userList;
            int startIndex = offset;
            int endIndex = Math.min(offset + limit, userList.size());
            return userList.subList(startIndex, endIndex);
        }

        public int getTotalUsers() {
            return UserDB.userList.size();
    }
    public List<User> searchUsersByName(String name) {
        List<User> matchedUsers = new ArrayList<>();
        for (User user : UserDB.userList) {
            if (user.getName().equalsIgnoreCase(name)) {
                matchedUsers.add(user);
            }
        }
        return matchedUsers;
    }
    public static Optional<User> findById(int id) {
        return UserDB.userList.stream().filter(user1 -> user1.getId().equals(id)).findFirst() ;
    }

    public User save(User user) {
        UserDB.userList.add(user);
        return user;
    }
    public boolean existsById(int id) {
        Optional<User> foundUser = userList.stream().filter(user -> user.getId() == id).findFirst();
        return foundUser.isPresent();
    }
    public static void delete(User course) {
        UserDB.userList.removeIf(c -> c.getId().equals(course.getId()));
    }
    
}
