import entity.Basket;
import entity.Order;
import entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.BasketService;

import java.math.BigDecimal;

public class OrderTest {
    private Order order;
    private Basket basket;

    @BeforeEach
    public void createNewOrder() {
        Product product1 = new Product("book", BigDecimal.valueOf(1000));
        Product product2 = new Product("journal", BigDecimal.valueOf(300));
        basket = new Basket();
        BasketService basketService = new BasketService(basket);

        basketService.addProduct(product1);
        basketService.addProduct(product2);
        basketService.addProduct(product2);
    }

    @Test
    public void calculatePriceThreeProducts() {
        order = new Order(23, basket, 10);
        Assertions.assertEquals(1440, order.getPrice().doubleValue(), 1e-10);
    }
}
