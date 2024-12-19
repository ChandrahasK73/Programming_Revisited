import java.util.*;
import java.util.function.Function;

// Define a GraphQLField with validation and resolver logic
class GraphQLField {
    String name;
    Map<String, GraphQLField> subFields;
    Function<Map<String, Object>, Object> resolver;

    public GraphQLField(String name) {
        this.name = name;
        this.subFields = new HashMap<>();
    }

    public void addSubField(GraphQLField field) {
        subFields.put(field.name, field);
    }

    public void setResolver(Function<Map<String, Object>, Object> resolver) {
        this.resolver = resolver;
    }
}

// GraphQLSchema with validation logic
class GraphQLSchema {
    GraphQLField root;

    public GraphQLSchema() {
        root = new GraphQLField("Query");
    }

    public void addField(GraphQLField field) {
        root.addSubField(field);
    }

    public void validateSchema() {
        if (root.subFields.isEmpty()) {
            throw new RuntimeException("Schema validation failed: No root fields defined.");
        }
        validateFields(root);
    }

    private void validateFields(GraphQLField field) {
        if (field.name == null || field.name.isEmpty()) {
            throw new RuntimeException("Invalid field name detected.");
        }
        for (GraphQLField subField : field.subFields.values()) {
            validateFields(subField);
        }
    }
}

// Subscription Manager for handling subscriptions
class SubscriptionManager {
    private Map<String, List<Function<Object, Void>>> subscriptions = new HashMap<>();

    public void subscribe(String fieldName, Function<Object, Void> listener) {
        subscriptions.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(listener);
    }

    public void publish(String fieldName, Object event) {
        if (subscriptions.containsKey(fieldName)) {
            for (Function<Object, Void> listener : subscriptions.get(fieldName)) {
                listener.apply(event);
            }
        }
    }
}

// DataStore with batching support
class DataStore {
    Map<String, List<Object>> data;

    public DataStore() {
        data = new HashMap<>();
        loadData();
    }

    private void loadData() {
        data.put("users", List.of(
            Map.of("id", 1, "name", "Alice", "role", "Admin"),
            Map.of("id", 2, "name", "Bob", "role", "User"),
            Map.of("id", 3, "name", "Charlie", "role", "Moderator")
        ));
    }

    public List<Object> batchQuery(String fieldName, List<Integer> ids) {
        List<Object> results = new ArrayList<>();
        for (Object item : data.getOrDefault(fieldName, Collections.emptyList())) {
            if (item instanceof Map && ids.contains(((Map<String, Object>) item).get("id"))) {
                results.add(item);
            }
        }
        return results;
    }
}

// GraphQL Engine to handle queries and subscriptions
class GraphQLEngine {
    GraphQLSchema schema;
    DataStore dataStore;
    SubscriptionManager subscriptionManager;

    public GraphQLEngine(GraphQLSchema schema, DataStore dataStore, SubscriptionManager subscriptionManager) {
        this.schema = schema;
        this.dataStore = dataStore;
        this.subscriptionManager = subscriptionManager;
    }

    public Object executeQuery(Map<String, Object> query) {
        schema.validateSchema();
        return processQuery(query, schema.root);
    }

    private Object processQuery(Map<String, Object> query, GraphQLField field) {
        Map<String, Object> result = new HashMap<>();
        for (var entry : query.entrySet()) {
            String key = entry.getKey();
            Object params = entry.getValue();

            GraphQLField subField = field.subFields.get(key);
            if (subField == null) {
                throw new RuntimeException("Field " + key + " not defined in schema.");
            }

            if (subField.resolver != null) {
                result.put(key, subField.resolver.apply((Map<String, Object>) params));
            } else if (!subField.subFields.isEmpty()) {
                result.put(key, processQuery((Map<String, Object>) params, subField));
            }
        }
        return result;
    }

    public void subscribe(String fieldName, Function<Object, Void> listener) {
        subscriptionManager.subscribe(fieldName, listener);
    }

    public void publish(String fieldName, Object event) {
        subscriptionManager.publish(fieldName, event);
    }
}

// Main class for demonstration
class EnhancedGraphQL {
    public static void main(String[] args) {
        // Create schema
        GraphQLSchema schema = new GraphQLSchema();

        // User field
        GraphQLField userField = new GraphQLField("user");
        userField.setResolver(params -> {
            int id = (int) params.get("id");
            return Map.of("id", id, "name", "User" + id, "role", "Dynamic Role");
        });

        // Users field with batching
        GraphQLField usersField = new GraphQLField("users");
        usersField.setResolver(params -> {
            List<Integer> ids = (List<Integer>) params.get("ids");
            DataStore store = new DataStore();
            return store.batchQuery("users", ids);
        });

        // Add fields to schema
        schema.addField(userField);
        schema.addField(usersField);

        // Create datastore and subscription manager
        DataStore dataStore = new DataStore();
        SubscriptionManager subscriptionManager = new SubscriptionManager();

        // Create engine
        GraphQLEngine engine = new GraphQLEngine(schema, dataStore, subscriptionManager);

        // Subscribe to user changes
        engine.subscribe("userUpdate", event -> {
            System.out.println("User updated: " + event);
            return null;
        });

        // Execute query
        Map<String, Object> query = Map.of(
            "user", Map.of("id", 1),
            "users", Map.of("ids", List.of(1, 2))
        );

        System.out.println("Query Result:");
        System.out.println(engine.executeQuery(query));

        // Publish subscription event
        engine.publish("userUpdate", Map.of("id", 1, "name", "Alice Updated"));
    }
}