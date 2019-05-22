package com.bigAlsTexture.SpringBucks.entity;

import com.bigAlsTexture.SpringBucks.serializer.MoneyDeserializer;
import com.bigAlsTexture.SpringBucks.serializer.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "t_coffee")
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
@Builder
public class Coffee extends BaseEntity {

    private String name;

    @JsonDeserialize(using = MoneyDeserializer.class)
    @JsonSerialize(using = MoneySerializer.class)
    private Money price;
}
