package com.bigAlsTexture.SpringBucks.dao;

import com.bigAlsTexture.SpringBucks.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeDao extends JpaRepository<Coffee, Long> {
    List<Coffee> findByNameInOrderById(List<String> list);
}
