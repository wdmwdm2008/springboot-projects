package com.bigAlsTexture.SpringBucks.entity;

import lombok.*;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "t_coffee")
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
@Builder
public class Coffee implements Serializable {
    @Id
    private String id;
    private Date create_time;
    private Date update_time;
    private String name;
    private Money price;
}
