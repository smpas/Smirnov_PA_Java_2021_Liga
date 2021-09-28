package service;

import entity.Basket;
import entity.Product;

public class BasketService {
    private Basket basket;

    public BasketService(Basket basket) {
        this.basket = basket;
    }

    public void addProduct(Product product) {
        Integer quantity = basket.getProducts().get(product);
        if (quantity == null) {
            basket.getProducts().put(product, 1);
        } else {
            basket.getProducts().put(product, quantity + 1);
        }

        basket.setPrice(basket.getPrice().add(product.getPrice()));
    }

    public void removeProduct(Product product) {
        Integer quantity = basket.getProducts().get(product);
        if (quantity == null) {
            System.out.println("This product is not in the basket");
        } else {
            extractProduct(product, quantity);
        }

        basket.setPrice(basket.getPrice().subtract(product.getPrice()));
    }

    private void extractProduct(Product product, Integer quantity) {
        if (quantity.equals(1)) {
            basket.getProducts().remove(product);
        } else {
            basket.getProducts().put(product, quantity - 1);
        }
    }

    public void clearBasket() {
        basket.getProducts().clear();
    }

    public boolean isEmpty() {
        return basket.getProducts().isEmpty();
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
