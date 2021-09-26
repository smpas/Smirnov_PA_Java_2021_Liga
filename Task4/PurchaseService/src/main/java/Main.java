import entity.Order;
import entity.Product;
import entity.User;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Product cat = new Product("cat", new BigDecimal(100));
        Product dog = new Product("dog", new BigDecimal(200));

        User user = new User(12, 5);
        user.addProduct(cat);
        user.addProduct(cat);
        user.addProduct(dog);
        user.submitOrder();

        user.addProduct(dog);
        user.addProduct(dog);
        user.removeProduct(dog);
        user.submitOrder();

        user.addProduct(cat);
        user.submitOrder();

        for (Order order : user.getOrders()) {
            System.out.println(order);
        }
    }
}
