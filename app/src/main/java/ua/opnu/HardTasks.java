package ua.opnu;

import ua.opnu.util.Customer;
import ua.opnu.util.DataProvider;
import ua.opnu.util.Order;
import ua.opnu.util.Product;
import java.util.stream.Collectors;

import java.util.*;

public class HardTasks {

    private final List<Customer> customers = DataProvider.customers;
    private final List<Order> orders = DataProvider.orders;
    private final List<Product> products = DataProvider.products;

    public static void main(String[] args) {
        HardTasks tasks = new HardTasks();

        // Для того, щоб побачити в консолі результат роботи методу, разкоментуйте відповідний рядок коду

        // Завдання 1
        Objects.requireNonNull(tasks.getBooksWithPrice(),"Method getBooksWithPrice() returns null").forEach(System.out::println);

        // Завдання 2
        Objects.requireNonNull(tasks.getOrdersWithBabyProducts(),"Method getOrdersWithBabyProducts() returns null").forEach(System.out::println);

        // Завдання 3
        // Objects.requireNonNull(tasks.applyDiscountToToys(),"Method applyDiscountToToys() returns null").forEach(System.out::println);

        // Завдання 4
        // System.out.println(Objects.requireNonNull(tasks.getCheapestBook(),"Method getCheapestBook() returns null").get());

        // Завдання 5
        // Objects.requireNonNull(tasks.getRecentOrders(),"Method getRecentOrders() returns null").forEach(System.out::println);

        // Завдання 6
        // DoubleSummaryStatistics statistics = Objects.requireNonNull(tasks.getBooksStats(), "Method getBooksStats() returns null");
        // System.out.printf("count = %1$d, average = %2$f, max = %3$f, min = %4$f, sum = %5$f%n", statistics.getCount(), statistics.getAverage(), statistics.getMax(), statistics.getMin(), statistics.getSum());

        // Завдання 7
        // Objects.requireNonNull(tasks.getOrdersProductsMap(),"Method getOrdersProductsMap() returns null").forEach((id, size) -> System.out.printf("%1$d : %2$d\n", id, size));

        // Завдання 8
        // Objects.requireNonNull(tasks.getProductsByCategory(), "Method getProductsByCategory() returns null").forEach((name, list) -> System.out.printf("%1$s : %2$s\n", name, Arrays.toString(list.toArray())));
    }

    public List<Product> getBooksWithPrice() {
        // Метод повинен повертати товари з id 7, 9, 16, 17, 24
        // TODO: напишіть вміст методу згідно умовам для того, щоб пройти тести
        return products.stream().filter(s -> s.getCategory().equals("Books")).filter(s-> s.getPrice() > 100).toList();
    }

    public List<Order> getOrdersWithBabyProducts() {

        // Метод повинен повертати замовлення з id 3, 4, 7, 8, 9, 10, 11, 14, 16, 17, 19,
        // 20, 27, 28, 29, 30, 32, 34, 37, 38, 40, 44, 45, 47, 48, 50

        return orders.stream()
                .filter(o -> o.getProducts().stream()
                        .anyMatch(p -> p.getCategory().equals("Baby")))
                .toList();
    }

    public List<Product> applyDiscountToToys() {

        // region Вірна відповідь
        // id та нова ціна товарів:
        // id=2 price=6.33
        // id=4 price=268.4
        // id=6 price=73.26
        // id=11 price=47.75
        // id=13 price=147.685
        // id=21 price=47.73
        // id=26 price=179.635
        // id=27 price=393.495
        // id=28 price=158.045
        // id=29 price=386.39
        // id=30 price=455.73
        // endregion

        return products.stream()
                .filter(p -> p.getCategory().equals("Toys"))
                .map(p -> {
                    p.setPrice(p.getPrice() * 0.5);
                    return p;
                })
                .toList();
    }

    public Optional<Product> getCheapestBook() {

        // товар з id = 17

        return products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                .findFirst();
    }

    public List<Order> getRecentOrders() {

        // id замовлень 23, 30, 33

        return orders.stream()
                .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()))
                .limit(3)
                .toList();
    }

    public DoubleSummaryStatistics getBooksStats() {

        // count = 5, average = 607.880000, max = 893.440000, min = 240.580000, sum = 3039.400000

        return products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .mapToDouble(p -> p.getPrice())
                .summaryStatistics();
    }

    public Map<Integer, Integer> getOrdersProductsMap() {
        return orders.stream()
                .collect(Collectors.toMap(
                        o -> o.getId(),
                        o -> o.getProducts().size()
                ));
    }

    public Map<String, List<Integer>> getProductsByCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCategory(),
                        Collectors.mapping(
                                p -> p.getId(),
                                Collectors.toList()
                        )
                ));
    }

}
