public class Main {

    public static void main(String[] args) {

        var orders = RestaurantOrders.read("orders_100.json");
        //var orders = RestaurantOrders.read("orders_1000.json").getOrders();
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();

        System.out.println("===== ЗАДАНИЕ 1 =====");

        System.out.println("Общая сумма всех заказов:");
        System.out.println(orders.totalRevenue());

        System.out.println("\nТоп 3 самых дорогих заказов:");
        orders.topMostExpensive(3)
                .forEach(o -> System.out.println(o.getTotal()));

        System.out.println("\nТоп 3 самых дешёвых заказов:");
        orders.topCheapest(3)
                .forEach(o -> System.out.println(o.getTotal()));

        System.out.println("\nКоличество заказов с доставкой на дом:");
        System.out.println(orders.homeDeliveryOrders().size());

        System.out.println("\nСамый прибыльный заказ на дом:");
        orders.mostProfitableHomeDelivery()
                .ifPresent(o -> System.out.println(o.getTotal()));

        System.out.println("\nСамый убыточный заказ на дом:");
        orders.leastProfitableHomeDelivery()
                .ifPresent(o -> System.out.println(o.getTotal()));

        System.out.println("\nЗаказы с суммой от 50 до 100:");
        orders.ordersBetween(50, 100)
                .forEach(o -> System.out.println(o.getTotal()));

        System.out.println("\nУникальные email клиентов:");
        orders.uniqueSortedEmails()
                .forEach(System.out::println);

    }
}
