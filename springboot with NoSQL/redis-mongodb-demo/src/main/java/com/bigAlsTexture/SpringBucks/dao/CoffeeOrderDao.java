package com.bigAlsTexture.SpringBucks.dao;

import com.bigAlsTexture.SpringBucks.entity.CoffeeOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoffeeOrderDao extends MongoRepository<CoffeeOrder, String> {
}
