package com.example.mongodbdemo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coffee {
    @Id
    private String id;
    private String name;
    private Money price;
    private Date create_time;
    private Date update_time;
}
