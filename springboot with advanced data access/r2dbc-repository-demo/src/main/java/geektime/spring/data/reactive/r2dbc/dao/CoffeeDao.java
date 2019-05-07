package geektime.spring.data.reactive.r2dbc.dao;

import geektime.spring.data.reactive.r2dbc.model.Coffee;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

public interface CoffeeDao extends ReactiveCrudRepository<Coffee, Long> {
    @Query("select * from t_coffee where name = $1")
    Flux<Coffee> findByName(String name);
}
