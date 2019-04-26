package com.bigAlsTexture.SpringBucks.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(collection = "t_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CoffeeOrder implements Serializable {
    @Id
    private String id;
    private Date create_time;
    private Date update_time;
    private String customer;
    private List<Coffee> items;
    private OrderState state;
}
