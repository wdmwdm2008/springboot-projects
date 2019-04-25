package geektime.spring.data.mongodemo.dao;

import geektime.spring.data.mongodemo.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoffeeDao extends MongoRepository<Coffee, String> {
    List<Coffee> findByName(String name);
}
