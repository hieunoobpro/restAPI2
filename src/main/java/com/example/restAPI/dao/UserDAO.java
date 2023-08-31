package com.example.restAPI.dao;


import com.example.restAPI.database.UserDB;
import com.example.restAPI.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    public List<User> getUsers() {
        return UserDB.userList;
    }
}
