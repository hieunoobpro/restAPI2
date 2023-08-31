package com.example.restAPI.database;


import com.example.restAPI.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    public static List<User> userList = new ArrayList<>(List.of(
            new User(1, "An", "abcxyz@gmail.com", "0123","Ha Noi", "avar1","111" ),
            new User(2, "Binh", "bbcxyz@gmail.com", "2345","Hai Phong", "avar2" ,"111"),
            new User(3, "Chi", "cbcxyz@gmail.com", "3456","Hai Duong", "avar3" ,"111"),
            new User(4, "Duong", "dbcxyz@gmail.com", "4567","Binh Dong", "avar4" ,"111"),
            new User(5, "Mai", "ebcxyz@gmail.com", "5678","Ha Noi", "avar5" ,"111"),
            new User(6, "Hai", "fbcxyz@gmail.com", "6789","Nam Dinh", "avar6" ,"111"),
            new User(7, "Giang", "gbcxyz@gmail.com", "7890","Ha Noi", "avar7" ,"111")
    ));
}
