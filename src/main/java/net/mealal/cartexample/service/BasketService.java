package net.mealal.cartexample.service;

import net.mealal.cartexample.entity.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketService {

    List<Item> listItems();

    @Transactional
    void add(Item item);

    @Transactional
    void delete(String id);
}
