package com.bigAlsTexture.SpringBucks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value="springbucks-coffee", timeToLive = 1)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeCache {
    @Id
    private Long id;
    @Indexed
    private String name;
    private Money money;
}
