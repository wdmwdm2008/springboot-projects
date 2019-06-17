package com.bigAlsTexture.SpringBucks.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CoffeeOrder extends BaseEntity{

    private String customer;

    @ManyToMany
    @JoinTable(name = "t_order_coffee")
    @OrderBy("id")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}
