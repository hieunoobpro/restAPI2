package com.example.restAPI.model;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private String password;
}
