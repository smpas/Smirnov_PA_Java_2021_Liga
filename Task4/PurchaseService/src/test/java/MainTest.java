import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.math.BigDecimal;

public class MainTest {
    private Main main;
    private UserService userService;

    @BeforeEach
    public void createAndStartMain() {
        main = new Main();
        Main.main(new String[] {"Main.java"});
        userService = Main.userService;
    }

    @Test
    public void createUserCreated() {
        Assertions.assertNotNull(userService.getUser());
    }

    @Test
    public void addFirstOrderAdded() {
        Assertions.assertEquals(380, userService.getUser().getOrders().get(0).getPrice().doubleValue(), 1e-10);
    }

    @Test
    public void addSecondOrderAdded() {
        Assertions.assertEquals(190, userService.getUser().getOrders().get(1).getPrice().doubleValue(), 1e-10);
    }

    @Test
    public void addThirdOrderAdded() {
        Assertions.assertEquals(95, userService.getUser().getOrders().get(2).getPrice().doubleValue(), 1e-10);
    }


}
