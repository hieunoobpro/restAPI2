package com.example.restAPI.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pages {
    private List<User> data;
    private int currentPage;
    private int size = 10;
    private int totalPage;
}
