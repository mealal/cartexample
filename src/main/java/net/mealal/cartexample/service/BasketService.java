package net.mealal.cartexample.service;

import net.mealal.cartexample.entity.Item;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

public interface BasketService {

    @Transactional
    public List<Item> listItems();

    @Transactional
    public void add(Item item);

    @Transactional
    void delete(Long id);
}
