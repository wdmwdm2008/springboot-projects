package com.example.demo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coffee {
    private String id;
    private String name;
    private Long price;
}
