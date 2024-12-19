import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class Candidate {
    String name;
    int pollPercentage;
    int actualPercentage;

    Candidate(String name, int pollPercentage) {
        this.name = name;
        this.pollPercentage = pollPercentage;
        this.actualPercentage = pollPercentage;  // Initialize to poll value; will adjust later
    }
}

class ElectionPrediction {
    private PriorityQueue<Candidate> pollQueue;
    private List<Candidate> finalResults;
    private Random random;

    public ElectionPrediction() {
        // Max-heap based on poll percentage
        pollQueue = new PriorityQueue<>(Comparator.comparingInt((Candidate c) -> -c.pollPercentage));
        finalResults = new ArrayList<>();
        random = new Random();
    }

    public void addPrediction(String candidateName, int pollPercentage) {
        pollQueue.add(new Candidate(candidateName, pollPercentage));
    }

    public void simulateVotes() {
        System.out.println("Election Results (Predicted vs Actual):");

        // Process each candidate, simulate fluctuation, and add to final results
        while (!pollQueue.isEmpty()) {
            Candidate candidate = pollQueue.poll();
            candidate.actualPercentage = applyFluctuation(candidate.pollPercentage);
            finalResults.add(candidate);
        }

        // Sort final results by actual vote percentage (descending)
        finalResults.sort((c1, c2) -> Integer.compare(c2.actualPercentage, c1.actualPercentage));

        // Display results
        for (Candidate candidate : finalResults) {
            System.out.printf("%s: Predicted %d%%, Actual %d%%%n",
                    candidate.name, candidate.pollPercentage, candidate.actualPercentage);
        }
    }

    private int applyFluctuation(int predictedPercentage) {
        // Simulate an election fluctuation of ±10% to +20%
        int fluctuation = random.nextInt(31) - 10;  // Range: [-10, +20]
        return Math.max(0, predictedPercentage + fluctuation);  // Ensure non-negative
    }

    public void showRanking() {
        System.out.println("\nFinal Ranking by Actual Vote Percentage:");
        int rank = 1;
        for (Candidate candidate : finalResults) {
            System.out.printf("Rank %d: %s with %d%%%n", rank++, candidate.name, candidate.actualPercentage);
        }
    }

    public void resetPredictions() {
        pollQueue.clear();
        finalResults.clear();
    }

    public static void main(String[] args) {
        ElectionPrediction election = new ElectionPrediction();

        // First round of predictions
        System.out.println("### First Round of Predictions ###");
        election.addPrediction("Candidate A", 52);
        election.addPrediction("Candidate B", 48);
        election.addPrediction("Candidate C", 35);
        election.simulateVotes();
        election.showRanking();

        // Reset and add new predictions with more candidates
        System.out.println("\n### Second Round of Predictions with Additional Candidates ###");
        election.resetPredictions();
        election.addPrediction("Candidate A", 51);
        election.addPrediction("Candidate B", 47);
        election.addPrediction("Candidate C", 39);
        election.addPrediction("Candidate D", 29);
        election.addPrediction("Candidate E", 26);
        election.simulateVotes();
        election.showRanking();

        // Reset and simulate a close race
        System.out.println("\n### Third Round with a Close Race ###");
        election.resetPredictions();
        election.addPrediction("Candidate A", 50);
        election.addPrediction("Candidate B", 49);
        election.addPrediction("Candidate C", 48);
        election.simulateVotes();
        election.showRanking();
    }
}
