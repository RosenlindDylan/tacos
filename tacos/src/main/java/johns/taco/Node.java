package johns.taco;
import java.util.LinkedList;

// simple node class, maybe mess around with adj list cardinality for B&B heuristics later
public class Node {
    private LinkedList<Node> adj;
    
    public void appendNode(Node summand) {
        adj.addLast(summand);
    }

    public int cardinality() {
        return adj.size();
    }
}
