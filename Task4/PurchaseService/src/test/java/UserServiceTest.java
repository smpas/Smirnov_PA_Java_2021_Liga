import entity.Product;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.math.BigDecimal;

public class UserServiceTest {
    private UserService userService;
    private Product product;

    @BeforeEach
    public void createNewUserAndProduct() {
        userService = new UserService(new User(3, 10));
        product = new Product("book", BigDecimal.valueOf(1000));
    }

    @Test
    public void addProductNotExistsAdded() {
        userService.addProduct(product);
        Assertions.assertEquals(1, userService.getUser().getBasket().getProducts().get(product));
    }

    @Test
    public void addProductExistsAdded() {
        userService.addProduct(product);
        userService.addProduct(product);
        Assertions.assertEquals(2, userService.getUser().getBasket().getProducts().get(product));
    }

    @Test
    public void removeProductNotExists() {
        userService.removeProduct(product);
    }

    @Test
    public void removeProductLastRemoved() {
        userService.addProduct(product);
        userService.removeProduct(product);
        Assertions.assertNull(userService.getUser().getBasket().getProducts().get(product));
    }

    @Test
    public void removeProductNotLastRemoved() {
        userService.addProduct(product);
        userService.addProduct(product);
        userService.removeProduct(product);
        Assertions.assertEquals(1, userService.getUser().getBasket().getProducts().get(product));
    }

    @Test
    public void submitOrderEmptyNotSubmitted() {
        userService.submitOrder();
        Assertions.assertEquals(0, userService.getUser().getOrders().size());
    }

    @Test
    public void submitOrderNotEmptySubmitted() {
        userService.addProduct(product);
        userService.addProduct(product);
        userService.submitOrder();
        Assertions.assertTrue(userService.getBasketService().isEmpty());
        Assertions.assertEquals(1, userService.getUser().getOrders().size());
    }

    @Test
    public void clearBasket() {
        userService.addProduct(product);
        userService.clearBasket();
        Assertions.assertTrue(userService.getBasketService().isEmpty());
    }
}
