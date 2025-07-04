import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Checkout {
    private static final int SHIPPING_RATE_PER_KG = 30;
    public static void checkout(Customer customer, Cart cart) {
        validateCart(cart);
        totals total = getTotals(cart);
        validateBalance(customer, total);
        updateProductQuantity(cart);
        updateCustomerBalance(customer, cart, total);
    }

    private static void updateCustomerBalance(Customer customer, Cart cart, totals total) {
        customer.setBalance(customer.getBalance()- total.totalAmount());
        ShippingService shippingService = new ShippingService();
        shippingService.sendShipmentItems(total.shippables());
        printCheckout(cart, customer, total.subTotal(), total.shippingCost(), total.totalAmount());
    }

    private static void updateProductQuantity(Cart cart) {
        for(Map.Entry<Product,Integer> entry: cart.products.entrySet()){
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantity(product.getQuantity()-quantity);
        }
    }

    private static void validateBalance(Customer customer, totals total) {
        if(total.totalAmount() > customer.getBalance()){
            throw new IllegalStateException("balance is insufficient");
        }
    }

    private static totals getTotals(Cart cart) {
        double subTotal = 0.0;
        double totalWeight = 0.0;
        List<Shippable> shippables = new ArrayList<>();
        for(Map.Entry<Product,Integer> entry: cart.products.entrySet()){
            Product product = entry.getKey();
            int quantity  = entry.getValue();
            subTotal +=quantity*product.getPrice();

            if(product instanceof Shippable shippable){
                totalWeight+= shippable.getWeight() * quantity;
                for(int i=0;i<quantity;i++){
                    shippables.add(shippable);
                }
            }
        }
        double shippingCost = totalWeight > 0 ? Math.floor(totalWeight) * SHIPPING_RATE_PER_KG : 0;
        double totalAmount = subTotal + shippingCost;
        return new totals(subTotal, shippables, shippingCost, totalAmount);
    }

    private record totals(double subTotal, List<Shippable> shippables, double shippingCost, double totalAmount) {
    }

    private static void validateCart(Cart cart) {
        if(cart.products.isEmpty()){
            throw new IllegalArgumentException("Cart is empty");
        }
        for(Map.Entry<Product,Integer> entry: cart.products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if(product.getQuantity() < quantity) {
                throw new IllegalArgumentException("Out of stock, the available quantity of " + product.getName() + " is " + product.getQuantity());
            }
            if(product.isExpired()){
                throw new IllegalArgumentException(product.getName()+" is expired");
            }
        }
    }


    public static void printCheckout(Cart cart,Customer customer,double subTotal,double shippingCost,double totalAmount){
        System.out.println("** Checkout receipt **");
        for(Map.Entry<Product,Integer> entry:cart.products.entrySet()){
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("%dx %-13s %.0f\n",quantity,product.getName(), (product.getPrice()*quantity));
        }
        System.out.println("----------------------");
        System.out.printf("%-16s %.0f\n", "Subtotal" ,subTotal);
        System.out.printf("%-16s %.0f\n", "Shipping" ,shippingCost);
        System.out.printf("%-16s %.0f\n", "Amount",totalAmount);
        System.out.printf("%-16s %.0f\n", "Customer current balance ",customer.getBalance());
    }

}
