package johns.taco;
import java.util.Random;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import java.util.ArrayList;
import java.util.LinkedList;

public class RandomGraph {
    private int size;
    private ArrayList<LinkedList<Integer>> spine; // list of linkedlists
    public UndirectedGraph<String,String> g;

    // constructor
    public RandomGraph(int size) {
        this.size = size;
        this.spine = new ArrayList<>(size);
        initializeGraph();
        System.out.println("Populating graph");
        populateADJ();
        jungObject();
    }

    private void initializeGraph() {
        for (int i = 0; i < size; i++) {
            spine.add(new LinkedList<>());
        }
    }

    // just starting nxn 
    private void populateADJ() {
        Random random = new Random();
        Random chance = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (chance.nextInt(10) <= 3) {
                    spine.get(i).addLast(random.nextInt(size));
                }
            }
        }
    }

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

    // use jung to visualize graph?

}
