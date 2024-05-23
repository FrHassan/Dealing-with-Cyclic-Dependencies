package mainPack;
import java.util.*;

public class CyclicDependencyHandler {
	
    public List<List<String>> tinyCyclicDependencies = new ArrayList<>();
    private int[][] dependencyMatrix;
    
    private int nbVertices = 0;
    private int nbEdges = 0;
    
    /*
     * Provide a graph and the services that provide the cyclic dependency
     * Output: Dependency Matrix
     * */
    public void generateDependencyMatrixForAcyclicDependency(Map<String, List<String>> graph, Set<String> selectedNodes) {
    	
    	if(selectedNodes.isEmpty()) {
    		return;
    	}
    	nbVertices = selectedNodes.size();
    	
    	Map<String, Integer> vertexIndexMap = new HashMap<>();
        int index = 0;
        
        // Create a map to assign indices to vertices
        for (String vertex : selectedNodes) {
            vertexIndexMap.put(vertex, index++);
        }

        // Initialize the matrix with 0s
        dependencyMatrix = new int[nbVertices][nbVertices];
        
        // Populate the matrix
        for (String vertex : selectedNodes) {
            int vertexIndex = vertexIndexMap.get(vertex);
            List<String> dependencies = graph.get(vertex);

            for (String dependency : dependencies) {
            	if(vertexIndexMap.containsKey(dependency)) {
                    int dependencyIndex = vertexIndexMap.get(dependency);
                    dependencyMatrix[vertexIndex][dependencyIndex] += 1;
                    nbEdges++;
            	}
            }
        }
        
    }
    
    
    //Print the graph dependency matrix
    public void printDependencyMatrix() {
        int size = getSize();
        System.out.println("vertices : "+nbVertices);
        System.out.println("edges : "+nbEdges);
        System.out.println("Dependency Matrix:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(dependencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    ////////////////////////////////////Shape Algorithm////////////////////////////////////
    
    //Get size of the cycle
    public int getSize() {
    	return dependencyMatrix.length;
    }
    
    //-------------------------------------------------------
    
    //Get the RATIO of the cycle
    public double getRATIO() {
    	
    	return Math.abs(Double.valueOf(nbVertices)) / Math.abs(Double.valueOf(nbEdges));
    }
    
    //-------------------------------------------------------
    
    //Get the DENSE of the cycle
    public double getDENSE() {
    	
    	return (Math.abs(Double.valueOf(nbEdges)) - Math.abs(Double.valueOf(nbVertices))) 
    			/ (Math.abs(Double.valueOf(nbVertices))*Math.abs(Double.valueOf(nbVertices)) 
    					- 2 *Math.abs(Double.valueOf(nbVertices)));
    }
    
    //-------------------------------------------------------

    //Get the maxDegree of the cycle
    public int getVerticeWithMaxDegree() {
    	
    	int degree = 0;
    	int maxDegree = 0;
    	
        for (int i = 0; i < nbVertices; i++) {
            for (int j = 0; j < nbVertices; j++) {
            	if(dependencyMatrix[i][j] >= 1 || dependencyMatrix[j][i] >= 1) {
            		degree += dependencyMatrix[i][j] + dependencyMatrix[j][i];
            	}
            }
            
            if(degree > maxDegree) {
            	maxDegree = degree;
            }
            degree = 0;
        }
        return maxDegree;
    }
    
    //Get the STAR of the cycle
    public double getSTAR() {
    	return getVerticeWithMaxDegree() / Math.abs(Double.valueOf(nbEdges));
    }
    
    //-------------------------------------------------------
    
    //Get vertices with exactly 2 friends and 2 connected with Tiny cyclic dependency
    public int getVerticesWith2FriendsAnd2InOutDependencies() {
    	
    	int nbVerticesWith2And2 = 0;
    	int OutgoingAndnbFriends = 0;
    	int IngoingDependencies = 0;
    	
        for (int i = 0; i < nbVertices; i++) {
            for (int j = 0; j < nbVertices; j++) {
            	if(dependencyMatrix[i][j] > 0) {
            		OutgoingAndnbFriends++;
            	}
            	if(dependencyMatrix[j][i] > 0) {
            		IngoingDependencies++;
            	}

            }
            if(OutgoingAndnbFriends == 2 && IngoingDependencies == 2) {
            	nbVerticesWith2And2++;
            }
            
            OutgoingAndnbFriends = 0;
            IngoingDependencies = 0;
        }
        return nbVerticesWith2And2;
    	
    }

    //Get the CHAIN of the cycle
    public double getCHAIN() {
    	return Math.min(getVerticesWith2FriendsAnd2InOutDependencies(), (Math.abs(Double.valueOf(nbVertices) - 2)))
    			/ (Math.abs(Double.valueOf(nbVertices) - 2));
    }
    
    //-------------------------------------------------------

    public int getNbEdgesWithBackRef() {

    	int nbBackEdges = 0;
    	
        for (int i = 0; i < nbVertices; i++) {
            for (int j = i+1; j < nbVertices; j++) {
            	if(dependencyMatrix[i][j] >= 1 && dependencyMatrix[j][i] >= 1) {
            		nbBackEdges+=2;
            	}
            }
        }
        return nbBackEdges;
    }
    
    //Get the BACKREF of the cycle
    public double getBACKREF() {
    	return getNbEdgesWithBackRef() / Math.abs(Double.valueOf(nbEdges));
    }
    
    //Get the HUB of the cycle
    public double getHUB() {
		GiniCoefficientBetweennessCentrality gcb = new GiniCoefficientBetweennessCentrality();
    	return Math.abs(gcb.calculateGiniCoefficient(this.dependencyMatrix));

    }
    
    //Shape classification algorithm
    public String shapeClassfication() {
    	
    	if(getSize() == 2) {
    		return "Tiny";
    	}else if(getRATIO() >= 0.75) {
    		return "Circle";
    	}else if(getBACKREF() >= 0.75) {
    		if(getDENSE() >= 0.75) {
    			return "Clique";
    		}else if(getCHAIN() >= 0.75) {
    			return "Chain";
    		}else if(getSTAR() >= 0.75) {
    			return "Star";
    		}
    	}
    	
    	if(getHUB() >= 0.5) {
			return "Multi-Hub";
		}else if(getDENSE() >= 0.45) {
			return "Semi-clique";
		}
    	
    	return "Unknown";

    }
    
    ////////////////////////////////////Tiny Cycles Algorithm////////////////////////////////////
    
    //Visiting status preparation
    public Map<String, NodeStatus> allNodesToNotVisited(Map<String, List<String>> graph) {

    	Map<String, NodeStatus> nodesWithVisitingStatus = new HashMap<>();

        // Initialize all nodes to NOT_VISITED
        for (String node : graph.keySet()) {
        	nodesWithVisitingStatus.put(node, NodeStatus.NOT_VISITED);
        }
        
        return nodesWithVisitingStatus;
    }
    
    
    //Tiny cyclic dependencies detector using DFS algorithm
    public List<List<String>> detectTinyCyclicDependencies(Map<String, List<String>> graph) {
    	
        Map<String, NodeStatus> nodesVisitingStatus = allNodesToNotVisited(graph);
        
    	for (String node : graph.keySet()) {
            if (nodesVisitingStatus.get(node) == NodeStatus.NOT_VISITED) {
                dfsVisit(graph, node, nodesVisitingStatus);
            }
        }
        return this.tinyCyclicDependencies;
    }

    private void dfsVisit(Map<String, List<String>> graph, String node, Map<String, NodeStatus> nodesVisitingStatus) {

    	nodesVisitingStatus.put(node, NodeStatus.CURRENTLY_VISITING); // Mark node as visited but not fully explored

        for (String neighbor : graph.getOrDefault(node, Collections.emptyList())) {
            if (nodesVisitingStatus.get(neighbor) == NodeStatus.NOT_VISITED) {
                dfsVisit(graph, neighbor, nodesVisitingStatus);
            } else if (nodesVisitingStatus.get(neighbor) == NodeStatus.CURRENTLY_VISITING && 
            		   graph.getOrDefault(neighbor, Collections.emptyList()).contains(node)) {
                // Found a back edge
            	this.tinyCyclicDependencies.add(Arrays.asList(node, neighbor));
            }
        }

        nodesVisitingStatus.put(node, NodeStatus.VISITED); // Mark node as fully explored
    }
    
        
    //Tiny cyclic dependencies detector using dependency matrix

    /*public void tinyCyclesDetector() {
        int size = dependencyMatrix.length;

        // Print dependency matrix
        for (int i = 0; i < size; i++) {
            for (int j = i+1; j < size; j++) {
            	if(dependencyMatrix[i][j] == 1 && dependencyMatrix[j][i] == 1) {
            		//Do not forget to change the tinyCyclicDependencies to Integer to use this method
                	tinyCyclicDependencies.add(Arrays.asList(i, j));
            	}
            }
        }
    }
     */
    
    

    
}