import java.util.*;

// Master Node: Central controller for the POS system
class MasterNode {
    private Map<String, Component> components;

    public MasterNode() {
        components = new HashMap<>();
    }

    public void addComponent(String name, Component component) {
        components.put(name, component);
    }

    // Sync data across all components
    public void syncData() {
        System.out.println("Syncing data across all components...");
        for (Component component : components.values()) {
            component.sync();
        }
        System.out.println("Data sync complete!\n");
    }

    // Apply discounts to the basket
    public void applyDiscounts(Basket basket) {
        DiscountManager discountManager = (DiscountManager) components.get("DiscountManager");
        discountManager.applyDiscounts(basket);
    }

    // Add products to the basket
    public void addProductsToBasket(Basket basket, List<Product> products) {
        InventoryManager inventoryManager = (InventoryManager) components.get("InventoryManager");
        inventoryManager.addProductsToBasket(basket, products);
    }

    // Process the basket and calculate the final price
    public void processBasket(Basket basket) {
        System.out.println("Processing basket...");
        applyDiscounts(basket);
        System.out.println("Final basket price: $" + basket.calculateTotalPrice() + "\n");
    }
}

// Interface for all components
interface Component {
    void sync();
}

// Discount Manager: Applies discounts to the basket
class DiscountManager implements Component {
    private PriorityQueue<Discount> discounts;

    public DiscountManager() {
        discounts = new PriorityQueue<>(Comparator.comparingInt(Discount::getPriority).reversed());
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public void applyDiscounts(Basket basket) {
        System.out.println("Applying discounts to the basket...");
        while (!discounts.isEmpty()) {
            Discount discount = discounts.poll();
            discount.apply(basket);
        }
    }

    @Override
    public void sync() {
        System.out.println("DiscountManager: Syncing discounts with the central database...");
    }
}

// Inventory Manager: Manages products and adds them to the basket
class InventoryManager implements Component {
    private Map<String, Product> products;

    public InventoryManager() {
        products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void addProductsToBasket(Basket basket, List<Product> productsToAdd) {
        System.out.println("Adding products to the basket...");
        for (Product product : productsToAdd) {
            if (products.containsKey(product.getId())) {
                basket.addProduct(product);
            } else {
                System.out.println("Product " + product.getId() + " not found in the inventory.");
            }
        }
    }

    @Override
    public void sync() {
        System.out.println("InventoryManager: Syncing product data with the central database...");
    }
}

// Basket: Represents the customer's shopping basket
class Basket {
    private List<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculateTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public List<Product> getProducts() {
        return products;
    }
}

// Product: Represents a product in the store
class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Discount: Represents a discount that can be applied to the basket
class Discount {
    private int priority;
    private String description;
    private double discountRate;

    public Discount(int priority, String description, double discountRate) {
        this.priority = priority;
        this.description = description;
        this.discountRate = discountRate;
    }

    public int getPriority() {
        return priority;
    }

    public void apply(Basket basket) {
        double totalPrice = basket.calculateTotalPrice();
        double discountedPrice = totalPrice * (1 - discountRate);
        System.out.println("Applied discount: " + description + " (Discount: " + (discountRate * 100) + "%)");
        System.out.println("Price before discount: $" + totalPrice);
        System.out.println("Price after discount: $" + discountedPrice + "\n");
    }
}

// Main class to demonstrate the POS system
class POSSystem {
    public static void main(String[] args) {
        // Create the Master Node
        MasterNode masterNode = new MasterNode();

        // Create and add components to the Master Node
        DiscountManager discountManager = new DiscountManager();
        InventoryManager inventoryManager = new InventoryManager();

        masterNode.addComponent("DiscountManager", discountManager);
        masterNode.addComponent("InventoryManager", inventoryManager);

        // Add products to the inventory
        inventoryManager.addProduct(new Product("P1", "Apple", 1.50));
        inventoryManager.addProduct(new Product("P2", "Banana", 0.75));
        inventoryManager.addProduct(new Product("P3", "Orange", 1.00));

        // Add discounts to the discount manager
        discountManager.addDiscount(new Discount(1, "Summer Sale", 0.10)); // 10% discount
        discountManager.addDiscount(new Discount(2, "Holiday Special", 0.20)); // 20% discount

        // Sync data across all components
        masterNode.syncData();

        // Create a basket and add products to it
        Basket basket = new Basket();
        List<Product> productsToAdd = Arrays.asList(
            new Product("P1", "Apple", 1.50),
            new Product("P2", "Banana", 0.75),
            new Product("P3", "Orange", 1.00)
        );
        masterNode.addProductsToBasket(basket, productsToAdd);

        // Process the basket and apply discounts
        masterNode.processBasket(basket);
    }
}
