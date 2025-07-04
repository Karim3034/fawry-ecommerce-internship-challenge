import java.time.LocalDate;
public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Karim",10000);

        Product cheese = new ShippableExpirableProduct("Cheese",100,2,0.2,LocalDate.of(2026,7,5));
        Product biscuits = new ShippableExpirableProduct("Biscuits",150,1,0.7,LocalDate.of(2027,8,6));
        Product matchTicket = new ExpirableProduct("Match Ticket",500,10,LocalDate.of(2025,9,5));
        Product tv = new ShippableProduct("Tv",5000,3,5);
        Product scratchCard = new NonShippableNonExpirableProduct("Scratch Card",70,10);

        Cart cart = new Cart();

        cart.addItem(cheese,2);
        cart.addItem(biscuits,1);
        cart.addItem(tv,1);
        cart.addItem(scratchCard,1);
        cart.addItem(matchTicket,3);
        Checkout.checkout(customer,cart);
    }
}