import java.time.LocalDate;
import java.util.Date;

public class ShippableExpirableProduct extends ShippableProduct{
    private LocalDate expiryDate;
    public ShippableExpirableProduct(String name, double price, int quantity, double weight, LocalDate expiryDate) {
        super(name, price, quantity, weight);
        this.expiryDate = expiryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
