package entity;

import java.math.BigDecimal;

public class Order {
    private Integer id;
    private Basket basket;
    private BigDecimal price;

    public Order(Integer id, Basket basket, Integer discount) {
        this.id = id;
        this.basket = basket;
        this.price = calculatePrice(basket, discount);
    }

    public BigDecimal calculatePrice(Basket basket, Integer discount) {
        BigDecimal coefficient = BigDecimal.valueOf((100.0 - discount) / 100.0);
        return basket.getPrice().multiply(coefficient);
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
