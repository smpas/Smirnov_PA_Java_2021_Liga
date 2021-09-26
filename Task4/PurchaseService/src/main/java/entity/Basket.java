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

    public void addProduct(Product product) {
        Integer quantity = products.get(product);
        if (quantity == null) {
            products.put(product, 1);
        } else {
            products.put(product, quantity + 1);
        }

        price = price.add(product.getPrice());
    }

    public void removeProduct(Product product) {
        Integer quantity = products.get(product);
        if (quantity == null) {
            System.out.println("This product is not in the basket");
        } else {
            extractProduct(product, quantity);
        }

        price = price.subtract(product.getPrice());
    }

    private void extractProduct(Product product, Integer quantity) {
        if (quantity.equals(1)) {
            products.remove(product);
        } else {
            products.put(product, quantity - 1);
        }
    }

    public void clearBasket() {
        products.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            stringBuilder.append(entry.getKey().getName());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n");
        }
        return new String(stringBuilder);
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
