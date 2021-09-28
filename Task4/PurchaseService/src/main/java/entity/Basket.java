package entity;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Basket {
    private Map<Product, Integer> products;
    private BigDecimal price;

    public Basket() {
        this.products = new LinkedHashMap<>();
        this.price = BigDecimal.valueOf(0);
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
