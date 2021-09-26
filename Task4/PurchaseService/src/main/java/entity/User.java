package entity;

import java.util.LinkedList;
import java.util.List;

public class User {
    Integer id;
    Integer discount;
    Basket basket;
    List<Order> orders;

    public User(Integer id, Integer discount) {
        this.id = id;
        this.discount = discount;
        basket = new Basket();
        orders = new LinkedList<Order>();
    }

    public void addProduct(Product product) {
        basket.addProduct(product);
    }

    public void removeProduct(Product product) {
        basket.removeProduct(product);
    }

    public void submitOrder() {
        if (basket.isEmpty()) {
            System.out.println("Корзина пуста!");
            return;
        }

        Order newOrder = new Order(orders.size(), basket, discount);
        orders.add(newOrder);
        basket = new Basket();
    }

    public void clearBasket() {
        basket.clearBasket();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
