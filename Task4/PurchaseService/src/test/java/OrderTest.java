import entity.Basket;
import entity.Order;
import entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderTest {
    private Order order;

    @BeforeEach
    public void createNewOrder() {
        Product product1 = new Product("book", BigDecimal.valueOf(1000));
        Product product2 = new Product("journal", BigDecimal.valueOf(300));
        Basket basket = new Basket();
        basket.addProduct(product1);
        basket.addProduct(product2);
        basket.addProduct(product2);
        order = new Order(23, basket, 10);
    }

    @Test
    public void calculatePriceThreeProducts() {
        BigDecimal price = order.calculatePrice(order.getBasket(), 10);
        Assertions.assertEquals(1440, price.doubleValue(), 1e-10);
    }
}
