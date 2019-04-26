package com.bigAlsTexture.SpringBucks.dao;

import com.bigAlsTexture.SpringBucks.entity.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoffeeDao extends MongoRepository<Coffee, String> {
}
