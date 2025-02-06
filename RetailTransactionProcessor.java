import java.util.*;

public class RetailTransactionProcessor {

    // Simulated customer database with loyalty status and total spend
    private static final Map<String, Customer> customerDatabase = new HashMap<>();

    static {
        customerDatabase.put("12345", new Customer("12345", "John Doe", 200.0));
        customerDatabase.put("67890", new Customer("67890", "Jane Smith", 50.0));
    }

    // Simulated offers based on basket total
    private static final Map<Double, Double> discountOffers = new HashMap<>();

    static {
        discountOffers.put(100.0, 10.0); // 10% discount for baskets >= $100
        discountOffers.put(200.0, 20.0); // 20% discount for baskets >= $200
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 1 for Loyalty Transaction, 2 for Non-Loyalty Transaction, or 0 to Exit:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                System.out.println("Exiting system. Thank you!");
                break;
            }

            System.out.println("Enter basket total amount:");
            double basketTotal = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("Enter Customer ID:");
                String customerId = scanner.nextLine();
                processLoyaltyTransaction(customerId, basketTotal);
            } else if (choice == 2) {
                processNonLoyaltyTransaction(basketTotal);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void processLoyaltyTransaction(String customerId, double basketTotal) {
        Customer customer = customerDatabase.get(customerId);
        if (customer != null) {
            System.out.println("Welcome back, " + customer.getName() + "!");

            double discount = calculateDiscount(basketTotal);
            double finalAmount = basketTotal - discount;

            System.out.println("Basket Total: $" + basketTotal);
            System.out.println("Discount Applied: $" + discount);
            System.out.println("Final Amount to Pay: $" + finalAmount);

            customer.addToTotalSpend(finalAmount);
            updateCustomerSegment(customer);
            System.out.println("Your updated total spend: $" + customer.getTotalSpend());
            System.out.println("Your updated segment: " + customer.getSegment());
        } else {
            System.out.println("Customer ID not found. Please check and try again.");
        }
    }

    private static void processNonLoyaltyTransaction(double basketTotal) {
        System.out.println("Processing non-loyalty transaction...");

        double discount = calculateDiscount(basketTotal);
        double finalAmount = basketTotal - discount;

        System.out.println("Basket Total: $" + basketTotal);
        System.out.println("Discount Applied: $" + discount);
        System.out.println("Final Amount to Pay: $" + finalAmount);

        System.out.println("Thank you for shopping with us!");
    }

    private static double calculateDiscount(double basketTotal) {
        double maxDiscount = 0.0;
        for (Map.Entry<Double, Double> offer : discountOffers.entrySet()) {
            if (basketTotal >= offer.getKey()) {
                maxDiscount = Math.max(maxDiscount, offer.getValue());
            }
        }
        return (maxDiscount / 100) * basketTotal;
    }

    private static void updateCustomerSegment(Customer customer) {
        double totalSpend = customer.getTotalSpend();
        if (totalSpend >= 500) {
            customer.setSegment("Gold");
        } else if (totalSpend >= 200) {
            customer.setSegment("Silver");
        } else {
            customer.setSegment("Bronze");
        }
    }

    // Customer class representing loyalty customers
    static class Customer {
        private final String id;
        private final String name;
        private double totalSpend;
        private String segment;

        public Customer(String id, String name, double totalSpend) {
            this.id = id;
            this.name = name;
            this.totalSpend = totalSpend;
            updateSegment();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getTotalSpend() {
            return totalSpend;
        }

        public String getSegment() {
            return segment;
        }

        public void addToTotalSpend(double amount) {
            this.totalSpend += amount;
            updateSegment();
        }

        public void setSegment(String segment) {
            this.segment = segment;
        }

        private void updateSegment() {
            if (totalSpend >= 500) {
                this.segment = "Gold";
            } else if (totalSpend >= 200) {
                this.segment = "Silver";
            } else {
                this.segment = "Bronze";
            }
        }
    }
}
