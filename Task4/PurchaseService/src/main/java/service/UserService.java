package service;

import entity.Basket;
import entity.Order;
import entity.Product;
import entity.User;

public class UserService {
    private User user;
    private BasketService basketService;

    public UserService(User user) {
        this.user = user;
        basketService = new BasketService(user.getBasket());
    }

    public void addProduct(Product product) {
        basketService.addProduct(product);
    }

    public void removeProduct(Product product) {
        basketService.removeProduct(product);
    }

    public void submitOrder() {
        if (basketService.isEmpty()) {
            System.out.println("Корзина пуста!");
            return;
        }

        Order newOrder = new Order(user.getOrders().size(), user.getBasket(), user.getDiscount());
        user.getOrders().add(newOrder);
        Basket basket = new Basket();
        user.setBasket(basket);
        basketService.setBasket(basket);
    }

    public void clearBasket() {
        basketService.clearBasket();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BasketService getBasketService() {
        return basketService;
    }

    public void setBasketService(BasketService basketService) {
        this.basketService = basketService;
    }
}
