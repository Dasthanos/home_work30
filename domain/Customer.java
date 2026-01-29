package kg.attractor.java.homework.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Customer {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private final String fullName;
    private final String email;

    public Customer(String fullName, String domain) {
        this.fullName = fullName;
        this.email = fullName.toLowerCase().replace(' ', '.') + domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(fullName, customer.fullName) &&
                Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email);
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    public Map<String, List<Order>> groupOrdersByCustomerName(List<Order> orders){
         return orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getFullName()));
    }

    public static Map<String, Double> getTotalByCustomerName(List<Order> orders){
        return orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getFullName(),
                        Collectors.summingDouble(Order::calculateTotal)));
    }

    public static String getMaxCustomer(List<Order> orders) {
        return getTotalByCustomerName(orders).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static String getMinCustomer(List<Order> orders){
        return getTotalByCustomerName(orders).entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static Map<String, Integer> groupOrdersByCount(List<Order> orders){
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        Item::getName,
                        Collectors.summingInt(Item::getAmount)));
    }















}
