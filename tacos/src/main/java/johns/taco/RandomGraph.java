package johns.taco;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;


public class RandomGraph {
    private int size;
    private ArrayList<LinkedList<Integer>> spine; // list of linkedlists
    public UndirectedGraph<String,String> g;
    private ArrayList<ArrayList<Integer>> matrix;

    // constructor
    public RandomGraph(int size) {
        this.size = size;
        int iterations = 1;
        initializeGraph();
        while (!connected()) {
            System.out.println("Populating graph");
            initializeGraph();
            populateADJ();
            iterations++;
        }
        System.out.println("Random graph successfully generated after " + iterations + " generations");
        jungObject();
    }

    // makes spine arraylist (outer list of the adjacency list)
    private void initializeGraph() {
        spine = new ArrayList<>(size);
        matrix = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            spine.add(new LinkedList<>());
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            matrix.add(row); // just doing both for now gonna reduce later
        }
    }

    // creates the random edges to populate the graph
    private void populateADJ() {
        Random random = new Random(); // functionally a sparsity parameter
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextInt(100) < 10 && i != j && matrix.get(j).get(i) == 0) { // add something to ensure the inverse edge doesn't exist
                    spine.get(i).addLast(j); // add edge from i to j
                    matrix.get(i).add(j);
                }
            }
        }
    }

    // prints out adjacency list
    public void readOut() {
        for (int i = 0; i < size; i++) {
            System.out.println("Vertex " + i);
            LinkedList<Integer> neighborhood = spine.get(i);

            for (Integer vertex : neighborhood) {
                System.out.println(vertex + " ");
            }
            System.out.println();
        }
    }

    // runs dfs to check if candidate graph is connected
    private boolean connected() {
        Set<Integer> visited = new HashSet<>();
        dfs(0, visited);
        // temp check to test connected method
        if (visited.size() == size) {
            System.out.println("graph is connected");
        } else {
            System.out.println("Graph is not connected, trying again");
        }
        return visited.size() == size;
    }

    // helper dfs method for connected method
    private void dfs(int vertex, Set<Integer> visited) {
        if (!visited.contains(vertex)) {
            visited.add(vertex);
    
            for (Integer neighbor : spine.get(vertex)) {
                dfs(neighbor, visited);
            }
        }
    }

    // creates jung graph visual for graph object
    private void jungObject() {
        g = new UndirectedSparseGraph<>();
        for (int i = 0; i < size; i++) {
            LinkedList<Integer> neighborhood = spine.get(i);
            g.addVertex("Vertex" + i);
            for (Integer vertex : neighborhood) {
                g.addEdge("Edge" + i + "&" + vertex, "Vertex" + i, "Vertex" + vertex);
            }
        }
        System.out.println("JUNG representation created");
    }


}
