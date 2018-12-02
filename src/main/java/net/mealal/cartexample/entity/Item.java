package net.mealal.cartexample.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "basket")
public class Item {

    @Id
    private String id;

    private String name;

    private Double price = Double.valueOf(0);

    private Integer count = 0;

    private double totalPrice;

    public Integer getCount() {
        return count;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
        this.totalPrice = price * this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
        this.totalPrice = count * this.totalPrice;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }
}
