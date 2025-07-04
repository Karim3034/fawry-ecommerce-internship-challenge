import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingService {
    public void sendShipmentItems(List<Shippable> shippables){
        Map<String,Integer> itemCount = new HashMap<>();
        Map<String,Double> itemWeight = new HashMap<>();
        double totalWeight = getTotalWeight(shippables, itemCount, itemWeight);
        printShipmentDetails(itemCount, itemWeight, totalWeight);
    }
    private double getTotalWeight(List<Shippable> shippables, Map<String, Integer> itemCount, Map<String, Double> itemWeight) {
        double totalWeight = 0.0;
        for(Shippable item: shippables){
            String name = item.getName();
            double weight = item.getWeight();
            itemCount.put(name, itemCount.getOrDefault(name,0) + 1);
            itemWeight.put(name, itemWeight.getOrDefault(name,0.0) + weight);
            totalWeight += weight;
        }
        return totalWeight;
    }

    private  void printShipmentDetails(Map<String, Integer> itemCount, Map<String, Double> itemWeight, double totalWeight) {
        System.out.println("** Shipment notice **");
        for(String name: itemCount.keySet()){
            int count = itemCount.get(name);
            double weight = itemWeight.get(name);
            System.out.printf("%dx %-13s %.0fg\n",count,name,weight*1000);
        }
        System.out.printf("%-13s %.1fkg\n\n", "Total package weight", totalWeight);
    }
}
