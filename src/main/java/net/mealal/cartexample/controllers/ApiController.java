package net.mealal.cartexample.controllers;

import net.mealal.cartexample.entity.Item;
import net.mealal.cartexample.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    public static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    BasketService basket;

    @RequestMapping("/login")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/items")
    public List<Item> items(Map<String, Object> map) {
        return basket.listItems();
    }

    @RequestMapping(value = "/items/add", method = RequestMethod.POST)
    public ResponseEntity addItem(@RequestBody Item item) {
        logger.debug("Adding new item: {}", item);
        basket.add(item);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/items/delete/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") String id) {
        basket.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
