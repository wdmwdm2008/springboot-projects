package com.bigAlsTexture.SpringBucks.dao;

import com.bigAlsTexture.SpringBucks.entity.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderDao extends JpaRepository<CoffeeOrder, Long> {
}
