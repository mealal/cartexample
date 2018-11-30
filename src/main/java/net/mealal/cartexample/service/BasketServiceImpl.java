package net.mealal.cartexample.service;

import net.mealal.cartexample.dao.BasketDAO;
import net.mealal.cartexample.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketDAO basketDAO;

    @Override
    public List<Item> listItems() {
        return basketDAO.listItems();
    }

    @Override
    public void add(Item item) {
        basketDAO.addItem(item);
    }

    @Override
    public void delete(Long id) {
        basketDAO.deleteItem(id);
    }
}
