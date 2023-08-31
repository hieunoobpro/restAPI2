package com.example.restAPI.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Page<T> {
    private List<T> data;
    private int currentPage;
    private int size = 10;
    private int totalPage;
}
