package johns.taco;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;


public class RandomGraph {
    private int size;
    public int convergence; // number of times it takes to get a connected graph
    private ArrayList<LinkedList<Integer>> spine; // list of linkedlists
    public UndirectedGraph<String,String> g;
    private ArrayList<ArrayList<Integer>> matrix; // adjacency matrix temporarily for dev

    // constructor
    public RandomGraph(int size) {
        this.size = size;
        convergence = 1;
        initializeGraph();
        while (!connected()) {
            System.out.println("Populating graph");
            initializeGraph();
            populateADJ();
            convergence++;
        }
        System.out.println("Random graph successfully generated after " + convergence + " generations");
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
                if (random.nextInt(100) < 8 && i != j && matrix.get(j).get(i) == 0) {
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

    // bfs approach for two coloring a graph
    // if two nodes are on the same bfs level they are given the same color
    public boolean twoColor() {
        int start = 0;
        boolean[] visited = new boolean[size];
        Queue<Integer> q = new LinkedList<>();
        ArrayList<Integer> levels = new ArrayList<>(size + 1);
        for (int i = 0; i < size; i++) {
            levels.add(0);
        }


        visited[start] = true;
        q.add(start);
        levels.add(0,1);
        
        while (!q.isEmpty()) {
            start = q.poll();
            int currentLevel = levels.get(start);
            
            LinkedList<Integer> neighborhood = spine.get(start);
            System.out.println(q.size());
            for (Integer neighbor : neighborhood) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.add(neighbor);
                    System.out.println("neighbor: " + neighbor);
                    levels.add(neighbor, currentLevel + 1); // color alternating by parity of level
                } else { // have previously visited neighbor
                    if (levels.get(neighbor) == currentLevel) {
                        System.out.println("Graph is not bipartite");
                        return false;
                    }
                }
            }
        }
        System.out.println("Graph is bipartite");
        return true;
    }

    // basic greedy coloring - isomorphisms / different start vertices can change soln
    public Integer greedyKColor() {
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Integer> allColors = new ArrayList<>();
        HashSet<Integer> usedColors = new HashSet<>();

        for (int j = 0; j < size; j++) {
            colors.add(0);
            allColors.add(j + 1);
        }
        // colors initalized with all zeroes - uncolored vertices

        // loop vertices
        // assign lowest unused color
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> availableColors = new ArrayList<>(allColors);
            // check neighbors of each vertex
            for (Integer vertex : spine.get(i)) {
                if (colors.get(vertex) != 0) {
                    availableColors.remove(colors.get(vertex));
                }
            }
            int color = availableColors.get(0);
            colors.add(i, color);
            usedColors.add(color);
        }
        return usedColors.size();
    }

    // optimal solution for wheel graphs
    public Integer dSatur() {
        ArrayList<Integer> vertex_colors = new ArrayList<>();
        ArrayList<Integer> allColors = new ArrayList<>();
        ArrayList<Integer> availableColors;
        HashSet<Integer> usedColors = new HashSet<>();
        HashMap<Integer, Integer> saturation_degree = new HashMap<>();
        HashSet<Integer> current_degree;
        HashSet<Integer> uncolored_vertices = new HashSet<>();
        
        int max_sat_vertex;
        
        for (int j = 0; j < size; j++) {
            vertex_colors.add(0);
            allColors.add(j + 1);
            saturation_degree.put(j, 0);
            uncolored_vertices.add(j);
        }
        // colors initalized with all zeroes - uncolored vertices
    
        while (!uncolored_vertices.isEmpty()) { // problem doing too many iterations
            availableColors = new ArrayList<>(allColors);
            max_sat_vertex = -1;
            for (Integer num : saturation_degree.keySet()) { // find max saturation degree vertex
                if (saturation_degree.get(num) > max_sat_vertex && uncolored_vertices.contains(num)) {
                    max_sat_vertex = num; // could make this better by keeping max heap pq
                }
            }

            // check neighbors of each vertex to find available colors
            for (Integer vertex : spine.get(max_sat_vertex)) {
                if (!uncolored_vertices.contains(vertex)) {
                    availableColors.remove(vertex_colors.get(vertex));
                }
            }
            int selected_color = availableColors.get(0);
            usedColors.add(availableColors.get(0));
            vertex_colors.add(max_sat_vertex, selected_color);
            uncolored_vertices.remove(max_sat_vertex);

            // update saturation degrees
            for (Integer vertex : spine.get(max_sat_vertex)) {
                current_degree = new HashSet<>();
                for (Integer neighbor : spine.get(vertex)) { // checking neighbors to find distinct colors
                    current_degree.add(vertex_colors.get(neighbor));
                }
                if (!current_degree.contains(selected_color)) { // new neighbor color
                    saturation_degree.put(vertex, saturation_degree.get(vertex) + 1); // increment sat deg
                }
            }
        }

        return usedColors.size(); // don't need to keep usedColors with present implementation - can remove later
    }

    

}
