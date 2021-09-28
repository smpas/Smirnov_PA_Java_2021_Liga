import entity.Basket;
import entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.BasketService;

import java.math.BigDecimal;

public class BasketServiceTest {
    private BasketService basketService;
    private Product product;

    @BeforeEach
    public void createNewBasket() {
        basketService = new BasketService(new Basket());
        product = new Product("Book", BigDecimal.valueOf(1000));
    }

    @Test
    public void addProductNotExistsAdded() {
        basketService.addProduct(product);
        Assertions.assertEquals(1, basketService.getBasket().getProducts().get(product));
    }

    @Test
    public void addProductExistsAdded() {
        basketService.addProduct(product);
        basketService.addProduct(product);
        Assertions.assertEquals(2, basketService.getBasket().getProducts().get(product));
    }

    @Test
    public void removeProductNotExists() {
        basketService.removeProduct(product);
    }

    @Test
    public void removeProductLastRemoved() {
        basketService.addProduct(product);
        basketService.removeProduct(product);
        Assertions.assertNull(basketService.getBasket().getProducts().get(product));
    }

    @Test
    public void removeProductNotLastRemoved() {
        basketService.addProduct(product);
        basketService.addProduct(product);
        basketService.removeProduct(product);
        Assertions.assertEquals(1, basketService.getBasket().getProducts().get(product));
    }

    @Test
    public void clearBasket() {
        basketService.addProduct(product);
        basketService.addProduct(product);
        basketService.clearBasket();
        Assertions.assertTrue(basketService.getBasket().getProducts().isEmpty());
    }

    @Test
    public void isEmptyTrue() {
        Assertions.assertTrue(basketService.isEmpty());
    }

    @Test
    public void isEmptyFalse() {
        basketService.addProduct(product);
        Assertions.assertFalse(basketService.isEmpty());
    }
}
