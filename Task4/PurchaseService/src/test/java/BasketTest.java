import entity.Basket;
import entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BasketTest {
    private Basket basket;
    private Product product;

    @BeforeEach
    public void createNewBasket() {
        basket = new Basket();
        product = new Product("Book", BigDecimal.valueOf(1000));
    }

    @Test
    public void addProductNotExistsAdded() {
        basket.addProduct(product);
        Assertions.assertEquals(1, basket.getProducts().get(product));
    }

    @Test
    public void addProductExistsAdded() {
        basket.addProduct(product);
        basket.addProduct(product);
        Assertions.assertEquals(2, basket.getProducts().get(product));
    }

    @Test
    public void removeProductNotExists() {
        basket.removeProduct(product);
    }

    @Test
    public void removeProductLastRemoved() {
        basket.addProduct(product);
        basket.removeProduct(product);
        Assertions.assertNull(basket.getProducts().get(product));
    }

    @Test
    public void removeProductNotLastRemoved() {
        basket.addProduct(product);
        basket.addProduct(product);
        basket.removeProduct(product);
        Assertions.assertEquals(1, basket.getProducts().get(product));
    }

    @Test
    public void clearBasket() {
        basket.addProduct(product);
        basket.addProduct(product);
        basket.clearBasket();
        Assertions.assertTrue(basket.getProducts().isEmpty());
    }

    @Test
    public void isEmptyTrue() {
        Assertions.assertTrue(basket.isEmpty());
    }

    @Test
    public void isEmptyFalse() {
        basket.addProduct(product);
        Assertions.assertFalse(basket.isEmpty());
    }
}
