
import com.google.gson.Gson;
import domain.Item;
import domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    private RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------


    public void printOrders() {
        orders.forEach(System.out::println);
    }

    public List<Order> topMostExpensive(int n) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal).reversed())
                .limit(n)
                .toList();
    }

    public List<Order> topCheapest(int n) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal))
                .limit(n)
                .toList();
    }

    public List<Order> homeDeliveryOrders() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .toList();
    }

    public Optional<Order> mostProfitableHomeDelivery() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .max(Comparator.comparingDouble(Order::getTotal));
    }

    public Optional<Order> leastProfitableHomeDelivery() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .min(Comparator.comparingDouble(Order::getTotal));
    }

    public List<Order> ordersBetween(double min, double max) {
        return orders.stream()
                .filter(o -> o.getTotal() > min && o.getTotal() < max)
                .toList();
    }

    public double totalRevenue() {
        return orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    public List<String> uniqueSortedEmails() {
        return orders.stream()
                .map(o -> o.getCustomer().getEmail())
                .collect(Collectors.toCollection(TreeSet::new))
                .stream()
                .toList();
    }


    public Map<String, List<Order>> ordersByCustomer() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCustomer().getFullName()
                ));
    }

    public Map<String, Double> totalByCustomer() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCustomer().getFullName(),
                        Collectors.summingDouble(Order::getTotal)
                ));
    }

    public Optional<Map.Entry<String, Double>> bestCustomer() {
        return totalByCustomer().entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    public Optional<Map.Entry<String, Double>> worstCustomer() {
        return totalByCustomer().entrySet().stream()
                .min(Map.Entry.comparingByValue());
    }

    public Map<String, Integer> soldItemsCount() {
        return orders.stream()
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.groupingBy(
                        Item::getName,
                        Collectors.summingInt(Item::getAmount)
                ));
    }
}
