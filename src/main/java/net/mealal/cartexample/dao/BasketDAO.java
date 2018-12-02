package net.mealal.cartexample.dao;

import net.mealal.cartexample.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BasketDAO extends MongoRepository<Item, String> {

}
