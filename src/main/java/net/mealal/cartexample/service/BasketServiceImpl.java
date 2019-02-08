package net.mealal.cartexample.service;

import net.mealal.cartexample.dao.BasketDAO;
import net.mealal.cartexample.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketDAO basketDAO;

    @Override
    public List<Item> listItems() {
        return basketDAO.findAll();
    }

    @Override
    @Transactional
    public void add(Item item) {
        basketDAO.insert(item);
    }

    @Override
    @Transactional
    public void delete(String id) {
        basketDAO.deleteById(id);
    }
}
