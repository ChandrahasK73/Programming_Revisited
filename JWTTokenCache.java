import java.util.HashMap;

class LRUCache {
    private final int capacity;
    private final HashMap<String, Node> map;
    private final Node head, tail;

    private static class Node {
        String token;
        Node prev, next;

        Node(String token) {
            this.token = token;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(null);  // dummy head
        this.tail = new Node(null);  // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public boolean put(String token) {
        if (map.containsKey(token)) {
            remove(map.get(token));
        } else if (map.size() == capacity) {
            map.remove(tail.prev.token);
            remove(tail.prev);
        }
        Node node = new Node(token);
        insert(node);
        map.put(token, node);
        return true;
    }

    public boolean contains(String token) {
        if (!map.containsKey(token)) return false;
        Node node = map.get(token);
        remove(node);
        insert(node);  // Move to the front as it's recently used
        return true;
    }

    private void insert(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

public class JWTTokenCache {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);

        // Simulate adding JWT tokens post-validation
        System.out.println("Put token1: "+cache.put("token1"));
        System.out.println("Put token2: "+cache.put("token2"));
        System.out.println("Put token3: "+cache.put("token3"));
        System.out.println("Put token4: "+cache.put("token4"));
        System.out.println("Put token5: "+cache.put("token5"));

        System.out.println("cache contains token3? "+cache.contains("token3"));  // true, recently used
        System.out.println("cache contains token1? "+cache.contains("token1"));  // true, still in cache

        // Adding a new token, evict the least recently used one
        System.out.println("Put token6: "+cache.put("token6"));
        System.out.println("cache contains token2? "+cache.contains("token2"));  // false, evicted
    }
}
