package net.mealal.cartexample.entity;

import javax.persistence.*;

@Entity
@Table(name = "basket")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "ITEM_NAME", nullable = false)
    private String name;

    @Column(name = "ITEM_PRICE", nullable = false)
    private Double price = Double.valueOf(0);

    @Column(name = "ITEMS_COUNT", nullable = false)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
