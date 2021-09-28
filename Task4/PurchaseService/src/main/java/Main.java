import entity.Order;
import entity.Product;
import entity.User;
import service.UserService;

import java.math.BigDecimal;

public class Main {
    public static UserService userService;

    public static void main(String[] args) {
        Product cat = new Product("cat", new BigDecimal(100));
        Product dog = new Product("dog", new BigDecimal(200));
        userService = new UserService(new User(12, 5));

        userService.addProduct(cat);
        userService.addProduct(cat);
        userService.addProduct(dog);
        userService.submitOrder();

        userService.addProduct(dog);
        userService.addProduct(dog);
        userService.removeProduct(dog);
        userService.submitOrder();

        userService.addProduct(cat);
        userService.submitOrder();
    }
}
