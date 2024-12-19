import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

class DreamNode {
    String name;  // Name of the space, e.g., "DarkForest", "WhitePlain"
    String description;  // Description or "sense" of the space
    int auraStrength;  // Aura strength, for spaces like "AncientTree"
    List<DreamNode> connections;  // Connections to other spaces (edges)

    public DreamNode(String name, String description, int auraStrength) {
        this.name = name;
        this.description = description;
        this.auraStrength = auraStrength;
        this.connections = new ArrayList<>();
    }

    public void connect(DreamNode otherNode) {
        connections.add(otherNode);
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}

class DreamGraph {
    private HashMap<String, DreamNode> spaces;
    private List<String> journeyPath;

    public DreamGraph() {
        this.spaces = new HashMap<>();
        this.journeyPath = new ArrayList<>();
    }

    public void addSpace(String name, String description, int auraStrength) {
        DreamNode newSpace = new DreamNode(name, description, auraStrength);
        spaces.put(name, newSpace);
    }

    public void connectSpaces(String from, String to) {
        DreamNode fromNode = spaces.get(from);
        DreamNode toNode = spaces.get(to);
        if (fromNode != null && toNode != null) {
            fromNode.connect(toNode);
        }
    }

    // Depth-First Search with backtracking to explore the dream
    public void exploreDream(String start, String goal) {
        Stack<DreamNode> stack = new Stack<>();
        DreamNode startNode = spaces.get(start);
        DreamNode goalNode = spaces.get(goal);

        if (startNode == null || goalNode == null) {
            System.out.println("One or both of the specified nodes do not exist.");
            return;
        }

        stack.push(startNode);

        while (!stack.isEmpty()) {
            DreamNode currentSpace = stack.pop();

            if (journeyPath.contains(currentSpace.name)) {
                continue;
            }

            journeyPath.add(currentSpace.name);
            System.out.println("Visiting: " + currentSpace);

            if (currentSpace == goalNode) {
                System.out.println("Reached the source of truth: " + currentSpace);
                break;
            }

            for (DreamNode nextSpace : currentSpace.connections) {
                if (!journeyPath.contains(nextSpace.name)) {
                    stack.push(nextSpace);
                }
            }
        }

        System.out.println("\nJourney Path Taken:");
        for (String path : journeyPath) {
            System.out.println("-> " + path);
        }
    }

    // Describe the path narratively
    public void describePathToGoal(String start, String goal) {
        if (!journeyPath.contains(goal)) {
            exploreDream(start, goal); // Populate journeyPath if not done
        }
        System.out.println("\nNarrative of the Journey:");
        for (String space : journeyPath) {
            DreamNode node = spaces.get(space);
            System.out.println("Passing through " + node.name + ": " + node.description);
        }
    }

    // Find all paths from start to goal
    public void findPaths(String start, String goal, List<String> path) {
        DreamNode startNode = spaces.get(start);
        path.add(start);

        if (start.equals(goal)) {
            System.out.println("Path found: " + path);
        } else {
            for (DreamNode nextSpace : startNode.connections) {
                if (!path.contains(nextSpace.name)) {
                    findPaths(nextSpace.name, goal, new ArrayList<>(path));
                }
            }
        }
    }

    // Check if graph has cycles
    public boolean isCyclicUtil(DreamNode node, List<String> visited, String parent) {
        visited.add(node.name);

        for (DreamNode neighbor : node.connections) {
            if (!visited.contains(neighbor.name)) {
                if (isCyclicUtil(neighbor, visited, node.name)) return true;
            } else if (!neighbor.name.equals(parent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCyclic() {
        List<String> visited = new ArrayList<>();
        for (DreamNode node : spaces.values()) {
            if (!visited.contains(node.name)) {
                if (isCyclicUtil(node, visited, null)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Explore using aura strength as a hint
    public void exploreWithHints(String start, String goal) {
        Stack<DreamNode> stack = new Stack<>();
        DreamNode startNode = spaces.get(start);
        DreamNode goalNode = spaces.get(goal);

        if (startNode == null || goalNode == null) {
            System.out.println("One or both of the specified nodes do not exist.");
            return;
        }

        stack.push(startNode);

        while (!stack.isEmpty()) {
            DreamNode currentSpace = stack.pop();

            if (journeyPath.contains(currentSpace.name)) {
                continue;
            }

            journeyPath.add(currentSpace.name);
            System.out.println("Exploring space: " + currentSpace + " with aura strength: " + currentSpace.auraStrength);

            if (currentSpace == goalNode) {
                System.out.println("Found the source of truth at: " + currentSpace);
                break;
            }

            currentSpace.connections.stream()
                .sorted((a, b) -> Integer.compare(b.auraStrength, a.auraStrength))  // Prioritize by aura strength
                .forEach(stack::push);
        }
    }

    public static void main(String[] args) {
        DreamGraph dreamGraph = new DreamGraph();

        dreamGraph.addSpace("DarkForest", "A dense forest where only thin rays of light pass", 2);
        dreamGraph.addSpace("WhitePlain", "An endless plain covered with a white sheet", 0);
        dreamGraph.addSpace("SelfDrivingBoat", "A boat moving on its own with no visible navigator", 1);
        dreamGraph.addSpace("AncientTree", "The first tree with radiating aura and sensing energy", 10);
        dreamGraph.addSpace("Goal", "The source of truth, a bright light", 0);

        dreamGraph.connectSpaces("DarkForest", "WhitePlain");
        dreamGraph.connectSpaces("WhitePlain", "SelfDrivingBoat");
        dreamGraph.connectSpaces("SelfDrivingBoat", "DarkForest");  // Cycle edge
        dreamGraph.connectSpaces("DarkForest", "AncientTree");
        dreamGraph.connectSpaces("AncientTree", "Goal");

        // Explore and narrate the dream journey
        dreamGraph.exploreDream("DarkForest", "Goal");
        dreamGraph.describePathToGoal("DarkForest", "Goal");

        // Find all paths from DarkForest to Goal
        System.out.println("\nAll possible paths from DarkForest to Goal:");
        dreamGraph.findPaths("DarkForest", "Goal", new ArrayList<>());

        // Check if there is a cycle
        System.out.println("\nGraph contains cycle: " + dreamGraph.isCyclic());

        // Explore dream using aura strength as a hint
        System.out.println("\nExploring dream journey with aura hints:");
        dreamGraph.exploreWithHints("DarkForest", "Goal");
    }
}
