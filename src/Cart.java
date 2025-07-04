import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    Map<Product, Integer> products = new HashMap<>();
    public void addItem(Product product, int quantity) {
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Out of stock, the available quantity of " + product.getName() + " is " + product.getQuantity());
        }
        if (product.isExpired()) {
            throw new IllegalArgumentException(product.getName()+" is expired");
        }
        products.put(product,products.getOrDefault(product,0) + quantity);
    }
}

