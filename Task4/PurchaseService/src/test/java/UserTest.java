import entity.Product;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class UserTest {
    private User user;
    private Product product;

    @BeforeEach
    public void createNewUserAndProduct() {
        user = new User(3, 10);
        product = new Product("book", BigDecimal.valueOf(1000));
    }

    @Test
    public void addProductNotExistsAdded() {
        user.addProduct(product);
        Assertions.assertEquals(1, user.getBasket().getProducts().get(product));
    }

    @Test
    public void addProductExistsAdded() {
        user.addProduct(product);
        user.addProduct(product);
        Assertions.assertEquals(2, user.getBasket().getProducts().get(product));
    }

    @Test
    public void removeProductNotExists() {
        user.removeProduct(product);
    }

    @Test
    public void removeProductLastRemoved() {
        user.addProduct(product);
        user.removeProduct(product);
        Assertions.assertNull(user.getBasket().getProducts().get(product));
    }

    @Test
    public void removeProductNotLastRemoved() {
        user.addProduct(product);
        user.addProduct(product);
        user.removeProduct(product);
        Assertions.assertEquals(1, user.getBasket().getProducts().get(product));
    }

    @Test
    public void submitOrderEmptyNotSubmitted() {
        user.submitOrder();
        Assertions.assertEquals(0, user.getOrders().size());
    }

    @Test
    public void submitOrderNotEmptySubmitted() {
        user.addProduct(product);
        user.addProduct(product);
        user.submitOrder();
        Assertions.assertTrue(user.getBasket().isEmpty());
        Assertions.assertEquals(1, user.getOrders().size());
    }
}
