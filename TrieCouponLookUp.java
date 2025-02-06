import java.util.*;

class CouponLookup {

    // Node class for the Trie
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfCoupon = false;
        String couponDescription = null;
    }

    // Trie class for managing coupon codes
    static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // Insert a coupon into the Trie
        public void insert(String coupon, String description) {
            TrieNode current = root;
            for (char ch : coupon.toCharArray()) {
                current.children.putIfAbsent(ch, new TrieNode());
                current = current.children.get(ch);
            }
            current.isEndOfCoupon = true;
            current.couponDescription = description;
        }

        // Search for a coupon in the Trie
        public boolean search(String coupon) {
            TrieNode current = root;
            for (char ch : coupon.toCharArray()) {
                current = current.children.get(ch);
                if (current == null) {
                    return false; // Coupon not found
                }
            }
            return current.isEndOfCoupon;
        }

        // Search for a coupon and return its description
        public String getCouponDescription(String coupon) {
            TrieNode current = root;
            for (char ch : coupon.toCharArray()) {
                current = current.children.get(ch);
                if (current == null) {
                    return null; // Coupon not found
                }
            }
            return current.isEndOfCoupon ? current.couponDescription : null;
        }

        // Get all coupons with a specific prefix
        public List<String> getCouponsWithPrefix(String prefix) {
            TrieNode current = root;
            for (char ch : prefix.toCharArray()) {
                current = current.children.get(ch);
                if (current == null) {
                    return Collections.emptyList(); // No coupons with the prefix
                }
            }
            List<String> results = new ArrayList<>();
            collectCoupons(current, new StringBuilder(prefix), results);
            return results;
        }

        // Helper method to collect coupons
        private void collectCoupons(TrieNode node, StringBuilder prefix, List<String> results) {
            if (node.isEndOfCoupon) {
                results.add(prefix.toString() + " (" + node.couponDescription + ")");
            }
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                prefix.append(entry.getKey());
                collectCoupons(entry.getValue(), prefix, results);
                prefix.deleteCharAt(prefix.length() - 1); // Backtrack
            }
        }
    }

    public static void main(String[] args) {
        Trie couponTrie = new Trie();

        // Insert coupons into the Trie
        couponTrie.insert("SUMMER2025", "10% off on summer collection");
        couponTrie.insert("WINTER2025", "15% off on winter collection");
        couponTrie.insert("FESTIVE50", "Flat $50 off on orders above $500");
        couponTrie.insert("SPRINGSALE", "20% off on spring sale items");

        // Search for specific coupons
        System.out.println("Coupon 'SUMMER2025' exists: " + couponTrie.search("SUMMER2025"));
        System.out.println("Description of 'WINTER2025': " + couponTrie.getCouponDescription("WINTER2025"));

        // Get coupons with a specific prefix
        System.out.println("Coupons with prefix 'SPR': " + couponTrie.getCouponsWithPrefix("SPR"));
        System.out.println("Coupons with prefix 'F': " + couponTrie.getCouponsWithPrefix("F"));

        // Try searching for a non-existent coupon
        System.out.println("Coupon 'AUTUMN2025' exists: " + couponTrie.search("AUTUMN2025"));
    }
}