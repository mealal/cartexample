package net.mealal.cartexample.service;

import net.mealal.cartexample.entity.Item;
import java.util.List;

public interface BasketService {

    List<Item> listItems();

    void add(Item item);

    void delete(String id);
}
