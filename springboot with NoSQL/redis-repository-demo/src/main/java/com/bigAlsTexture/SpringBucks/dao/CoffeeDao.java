package com.bigAlsTexture.SpringBucks.dao;

import com.bigAlsTexture.SpringBucks.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeDao extends JpaRepository<Coffee, Long> {
}
