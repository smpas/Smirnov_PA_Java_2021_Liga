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

    private BigDecimal calculatePrice(Basket basket, Integer discount) {
        BigDecimal coefficient = BigDecimal.valueOf((100.0 - discount) / 100.0);
        return basket.getPrice().multiply(coefficient);
    }

    public BigDecimal getPrice() {
        return price;
    }
}
