import java.util.*;

class EnhancedPOSSlidingWindow {

    private static class Transaction {
        int id;
        double amount;
        String timestamp;
        String category;

        public Transaction(int id, double amount, String timestamp, String category) {
            this.id = id;
            this.amount = amount;
            this.timestamp = timestamp;
            this.category = category;
        }

        @Override
        public String toString() {
            return "Transaction{id=" + id + ", amount=" + amount + ", timestamp='" 
                + timestamp + "', category='" + category + "'}";
        }
    }

    // Sliding Window Data Structure
    private static class SlidingWindow {
        private final Deque<Transaction> window;
        private final int maxSize;
        private double totalAmount;

        public SlidingWindow(int maxSize) {
            this.window = new LinkedList<>();
            this.maxSize = maxSize;
            this.totalAmount = 0.0;
        }

        // Add transaction to the window
        public void addTransaction(Transaction transaction) {
            window.addLast(transaction);
            totalAmount += transaction.amount;

            if (window.size() > maxSize) {
                Transaction removed = window.removeFirst();
                totalAmount -= removed.amount;
            }
        }

        // Get average transaction amount in the current window
        public double getAverageAmount() {
            return window.isEmpty() ? 0.0 : totalAmount / window.size();
        }

        // Get transactions in the current window
        public List<Transaction> getTransactions() {
            return new ArrayList<>(window);
        }

        // Detect potential fraud based on high transaction frequency
        public boolean detectFraud(double threshold) {
            return totalAmount > threshold;
        }

        // Get high-value transactions
        public List<Transaction> getHighValueTransactions(double highValueThreshold) {
            List<Transaction> highValueTransactions = new ArrayList<>();
            for (Transaction transaction : window) {
                if (transaction.amount > highValueThreshold) {
                    highValueTransactions.add(transaction);
                }
            }
            return highValueTransactions;
        }

        // Get transaction count per category
        public Map<String, Integer> getCategoryAnalysis() {
            Map<String, Integer> categoryCount = new HashMap<>();
            for (Transaction transaction : window) {
                categoryCount.put(transaction.category, 
                    categoryCount.getOrDefault(transaction.category, 0) + 1);
            }
            return categoryCount;
        }

        // Calculate transaction frequency
        public double getTransactionFrequency() {
            return (double) window.size();
        }
    }

    public static void main(String[] args) {
        // Simulating a POS system with a sliding window of size 5
        SlidingWindow slidingWindow = new SlidingWindow(5);

        // Sample transactions
        List<Transaction> transactions = List.of(
                new Transaction(1, 100.0, "2024-11-21 10:00", "Groceries"),
                new Transaction(2, 200.0, "2024-11-21 10:01", "Electronics"),
                new Transaction(3, 150.0, "2024-11-21 10:02", "Groceries"),
                new Transaction(4, 50.0, "2024-11-21 10:03", "Clothing"),
                new Transaction(5, 300.0, "2024-11-21 10:04", "Electronics"),
                new Transaction(6, 120.0, "2024-11-21 10:05", "Groceries"),
                new Transaction(7, 180.0, "2024-11-21 10:06", "Electronics")
        );

        double fraudThreshold = 800.0; // Define fraud threshold for the sliding window
        double highValueThreshold = 200.0; // Define high-value transaction threshold

        for (Transaction transaction : transactions) {
            slidingWindow.addTransaction(transaction);

            // Current window status
            System.out.println("Current Transactions in Window: " + slidingWindow.getTransactions());
            System.out.printf("Average Transaction Amount: %.2f%n", slidingWindow.getAverageAmount());

            // Fraud detection
            if (slidingWindow.detectFraud(fraudThreshold)) {
                System.out.println(" Potential Fraud Detected:"+ 
                    "Total transactions in window exceeded threshold!");
            }

            // High-value transactions
            List<Transaction> highValueTransactions = 
                slidingWindow.getHighValueTransactions(highValueThreshold);
            if (!highValueTransactions.isEmpty()) {
                System.out.println("💰 High-Value Transactions Detected: " + highValueTransactions);
            }

            // Category analysis
            Map<String, Integer> categoryAnalysis = slidingWindow.getCategoryAnalysis();
            System.out.println("Category Analysis: " + categoryAnalysis);

            // Transaction frequency
            System.out.println("Transaction Frequency in Window: " + 
                slidingWindow.getTransactionFrequency() + " transactions");

            System.out.println("-------------------------");
        }
    }
}