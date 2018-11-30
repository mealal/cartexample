package net.mealal.cartexample.dao;

import net.mealal.cartexample.entity.Item;

import java.util.List;

public interface BasketDAO {

    public void addItem(Item item);

    public void deleteItem(Long id);

    public List<Item> listItems();
}
