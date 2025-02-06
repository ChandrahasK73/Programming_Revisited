import java.util.*;

class DynamicPricingOfferSystem {

    // Trie Node Definition
    static class TrieNode {
        String name;
        double minValueForOffer;
        String offerDescription;
        Map<String, TrieNode> children;

        public TrieNode(String name) {
            this.name = name;
            this.minValueForOffer = 0.0;
            this.offerDescription = null;
            this.children = new HashMap<>();
        }
    }

    // Trie Structure for Offers
    static class OfferTrie {
        TrieNode root;

        public OfferTrie() {
            root = new TrieNode("root");
        }

        public void addOffer(String[] groupPath, double minValue, String description) {
            TrieNode current = root;
            for (String group : groupPath) {
                current.children.putIfAbsent(group, new TrieNode(group));
                current = current.children.get(group);
            }
            current.minValueForOffer = minValue;
            current.offerDescription = description;
        }

        public TrieNode findGroup(String[] groupPath) {
            TrieNode current = root;
            for (String group : groupPath) {
                current = current.children.get(group);
                if (current == null) {
                    return null;
                }
            }
            return current;
        }
    }

    // Basket Item Definition
    static class BasketItem {
        String name;
        String[] groupPath;
        double price;

        public BasketItem(String name, String[] groupPath, double price) {
            this.name = name;
            this.groupPath = groupPath;
            this.price = price;
        }
    }

    // Method to calculate offers on a basket
    public static void calculateOffers(OfferTrie offerTrie, List<BasketItem> basket) {
        Map<String, Double> groupTotals = new HashMap<>();
        Map<String, String> appliedOffers = new HashMap<>();

        for (BasketItem item : basket) {
            TrieNode groupNode = offerTrie.findGroup(item.groupPath);
            if (groupNode != null) {
                String groupKey = String.join("/", item.groupPath);
                groupTotals.put(groupKey, groupTotals.getOrDefault(groupKey, 0.0) + item.price);

                if (groupNode.minValueForOffer > 0 && groupTotals.get(groupKey) >= groupNode.minValueForOffer) {
                    appliedOffers.put(groupKey, groupNode.offerDescription);
                }
            }
        }

        System.out.println("--- Basket Summary ---");
        groupTotals.forEach((group, total) -> {
            System.out.printf("Group: %s, Total: $%.2f\n", group, total);
            if (appliedOffers.containsKey(group)) {
                System.out.println("  Offer Applied: " + appliedOffers.get(group));
            }
        });
    }

    public static void main(String[] args) {
        // Initialize Offer Trie
        OfferTrie offerTrie = new OfferTrie();

        // Adding Offers
        offerTrie.addOffer(new String[]{"Snacks"}, 50.0, "10% off");
        offerTrie.addOffer(new String[]{"Beverages"}, 30.0, "$5 off");
        offerTrie.addOffer(new String[]{"Electronics"}, 100.0, "15% off");

        // Basket Initialization
        List<BasketItem> basket = new ArrayList<>();
        basket.add(new BasketItem("Chips", new String[]{"Snacks"}, 20.0));
        basket.add(new BasketItem("Soda", new String[]{"Beverages"}, 15.0));
        basket.add(new BasketItem("Chocolate", new String[]{"Snacks"}, 15.0));
        basket.add(new BasketItem("Smartphone", new String[]{"Electronics"}, 120.0));

        // Calculate Offers
        calculateOffers(offerTrie, basket);
    }
}
