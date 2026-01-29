package kg.attractor.java.homework.domain;

import java.util.*;
import java.util.stream.Collectors;

import kg.attractor.java.homework.util.NotImplementedException;

public class Order {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private final Customer customer;
    private final List<Item> items;
    private final boolean homeDelivery;
    private transient double total = 0.0d;

    public Order(Customer customer, List<Item> orderedItems, boolean homeDelivery) {
        this.customer = customer;
        this.items = List.copyOf(orderedItems);
        this.homeDelivery = homeDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return homeDelivery == order.homeDelivery &&
                Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, homeDelivery);
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isHomeDelivery() {
        return homeDelivery;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotal() {
        return total;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    public double calculateTotal() {
         return total = items.stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }

    public void printOrders(List<Order> orders){
        orders.forEach(System.out::println);
    }

    public static List<Order> getTopNOrders(List<Order> orders, int n) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::calculateTotal).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public static List<Order> getLowestNOrders(List<Order> orders, int n){
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::calculateTotal))
                .limit(n)
                .toList();
    }

    public static List<Order> getHomeOrders(List<Order> orders) {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .toList();
    }

    public static Order getMaxHomeOrder(List<Order> orders) {
        return getHomeOrders(orders).stream()
                .max(Comparator.comparingDouble(Order::calculateTotal))
                .orElse(null);
    }

    public static Order getMinHomeOrder(List<Order> orders) {
        return getHomeOrders(orders).stream()
                .min(Comparator.comparingDouble(Order::calculateTotal))
                .orElse(null);
    }

    public static List<Order> getOrdersBetween(List<Order> orders, double minOrderTotal, double maxOrderTotal) {
        return orders.stream()
                .filter(o -> {
                double total = o.calculateTotal();
                return total>minOrderTotal && total<maxOrderTotal;
                }).toList();
    }

    public static double totalSumOrders(List<Order> orders){
        return orders.stream()
                .mapToDouble(Order::calculateTotal)
                .sum();
    }

    public static List<String> getSortedUniqueEmails(List<Order> orders) {
        return new ArrayList<>(orders.stream()
                .map(Order::getCustomer)
                .map(Customer::getEmail)
                .collect(Collectors.toCollection(TreeSet::new)));
    }
}
