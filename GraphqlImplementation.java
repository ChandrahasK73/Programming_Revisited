import java.util.*;

// Define GraphQLField for representing fields and subfields
class GraphQLField {
    String name;
    Map<String, GraphQLField> subFields;

    public GraphQLField(String name) {
        this.name = name;
        this.subFields = new HashMap<>();
    }

    public void addSubField(GraphQLField field) {
        subFields.put(field.name, field);
    }
}

// Define GraphQLSchema for holding the schema structure
class GraphQLSchema {
    GraphQLField root;

    public GraphQLSchema() {
        root = new GraphQLField("Query");
    }

    public void addField(GraphQLField field) {
        root.addSubField(field);
    }
}

// Simulated Data Store
class DataStore {
    Map<String, Object> data;

    public DataStore() {
        data = new HashMap<>();
        loadData();
    }

    private void loadData() {
        data.put("user", Map.of(
            "id", 1,
            "name", "Alice",
            "friends", List.of(
                Map.of("id", 2, "name", "Bob"),
                Map.of("id", 3, "name", "Charlie")
            ),
            "posts", List.of(
                Map.of("id", 101, "title", "GraphQL Introduction", "comments", List.of(
                    Map.of("id", 201, "content", "Great post!", "user", "David"),
                    Map.of("id", 202, "content", "Very helpful, thanks!", "user", "Emma")
                )),
                Map.of("id", 102, "title", "Advanced GraphQL", "comments", List.of(
                    Map.of("id", 203, "content", "Loved it!", "user", "Frank")
                ))
            )
        ));
    }

    public Object query(String fieldName) {
        return data.getOrDefault(fieldName, null);
    }
}

// GraphQL Engine to process queries
class GraphQLEngine {
    GraphQLSchema schema;
    DataStore dataStore;

    public GraphQLEngine(GraphQLSchema schema, DataStore dataStore) {
        this.schema = schema;
        this.dataStore = dataStore;
    }

    public Object executeQuery(Map<String, GraphQLField> query) {
        return processQuery(query, dataStore.data);
    }

    private Object processQuery(Map<String, GraphQLField> query, Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, GraphQLField> entry : query.entrySet()) {
            String key = entry.getKey();
            GraphQLField field = entry.getValue();
            Object value = data.get(key);

            if (field.subFields.isEmpty()) {
                result.put(key, value);
            } else if (value instanceof Map) {
                result.put(key, processQuery(field.subFields, (Map<String, Object>) value));
            } else if (value instanceof List) {
                List<Object> listResult = new ArrayList<>();
                for (Object item : (List<Object>) value) {
                    if (item instanceof Map) {
                        listResult.add(processQuery(field.subFields, (Map<String, Object>) item));
                    }
                }
                result.put(key, listResult);
            }
        }
        return result;
    }
}

// Main class to demonstrate the implementation
public class GraphqlImplementation {
    public static void main(String[] args) {
        // Define schema
        GraphQLSchema schema = new GraphQLSchema();

        // User and subfields
        GraphQLField userField = new GraphQLField("user");
        userField.addSubField(new GraphQLField("name"));

        // Friends subfield
        GraphQLField friendsField = new GraphQLField("friends");
        friendsField.addSubField(new GraphQLField("name"));
        userField.addSubField(friendsField);

        // Posts and comments subfields
        GraphQLField postsField = new GraphQLField("posts");
        postsField.addSubField(new GraphQLField("title"));

        GraphQLField commentsField = new GraphQLField("comments");
        commentsField.addSubField(new GraphQLField("content"));
        commentsField.addSubField(new GraphQLField("user"));
        postsField.addSubField(commentsField);

        userField.addSubField(postsField);

        schema.addField(userField);

        // Create datastore
        DataStore dataStore = new DataStore();

        // Create engine
        GraphQLEngine engine = new GraphQLEngine(schema, dataStore);

        // Define and execute queries
        Map<String, GraphQLField> query = Map.of(
            "user", userField
        );

        Object result = engine.executeQuery(query);
        System.out.println("Query Result:");
        System.out.println(result);

        // Additional query to fetch specific fields
        GraphQLField postsOnlyField = new GraphQLField("user");
        postsOnlyField.addSubField(postsField);

        query = Map.of("user", postsOnlyField);
        result = engine.executeQuery(query);
        System.out.println("\nPosts Query Result:");
        System.out.println(result);
    }
}
