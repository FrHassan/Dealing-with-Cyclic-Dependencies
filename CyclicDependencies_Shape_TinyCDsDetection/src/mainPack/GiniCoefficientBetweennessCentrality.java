package mainPack;
import java.util.*;

public class GiniCoefficientBetweennessCentrality{

    public double calculateGiniCoefficient(int[][] adjacencyMatrix) {
        // Step 1: Compute betweenness centrality
        Map<Integer, Double> betweennessCentrality = computeBetweennessCentrality(adjacencyMatrix);
        // Step 2: Sort betweenness centrality values
       Double[] sortedBCValues = betweennessCentrality.values().stream()
               .sorted()
               .toArray(Double[]::new);
        
        System.out.println("betweenness Centrality values : "+betweennessCentrality.values());
        
        // Step 3: Calculate Lorenz curve
        int n = sortedBCValues.length;
        double totalBC = betweennessCentrality.values().stream().mapToDouble(Double::doubleValue).sum();
        double[] cumulativeBC = new double[n];
        double[] cumulativeNodes = new double[n];
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += sortedBCValues[i];
            cumulativeBC[i] = sum / totalBC;
            cumulativeNodes[i] = (double) (i + 1) / n;
        }

        // Step 4: Calculate area under Lorenz curve using Trapezoidal Rule
        double areaUnderCurve = 0;
        for (int i = 1; i < n; i++) {
            areaUnderCurve += (cumulativeNodes[i] - cumulativeNodes[i - 1]) * (cumulativeBC[i] + cumulativeBC[i - 1]) / 2;

        }
        
        // Step 5: Compute Gini coefficient
        return 1 - 2 * areaUnderCurve;
    }

    private Map<Integer, Double> computeBetweennessCentrality(int[][] adjacencyMatrix) {
    	int n = adjacencyMatrix.length;
        Map<Integer, Double> betweennessCentrality = new HashMap<>();
        
        for (int s = 0; s < n; s++) {
            Stack<Integer> stack = new Stack<>();
            LinkedList<Integer>[] predecessors = new LinkedList[n];
            double[] sigma = new double[n];
            double[] distance = new double[n];
            Arrays.fill(distance, -1.0);
            double[] dependency = new double[n];
            Arrays.fill(sigma, 0.0);
            sigma[s] = 1.0;
            distance[s] = 0.0;
            
            Queue<Integer> queue = new LinkedList<>();
            queue.add(s);
            while (!queue.isEmpty()) {
                int v = queue.poll();
                stack.push(v);
                for (int w = 0; w < n; w++) {
                    if (adjacencyMatrix[v][w] >= 1) { // If there's an edge from v to w
                        if (distance[w] < 0) {
                            queue.add(w);
                            distance[w] = distance[v] + 1;
                        }
                        if (distance[w] == distance[v] + 1) {
                            sigma[w] += sigma[v];
                            if (predecessors[w] == null) {
                                predecessors[w] = new LinkedList<>();
                            }
                            predecessors[w].add(v);
                        }
                    }
                }
            }
            
            Arrays.fill(dependency, 0.0);
            while (!stack.isEmpty()) {
                int w = stack.pop();
                if (predecessors[w] != null) {
                    for (int v : predecessors[w]) {
                        dependency[v] += (sigma[v] / sigma[w]) * (1 + dependency[w]);
                    }
                    if (w != s) {
                        betweennessCentrality.put(w, betweennessCentrality.getOrDefault(w, 0.0) + dependency[w]);
                    }
                }
            }
        }
        return betweennessCentrality;
    }
}
