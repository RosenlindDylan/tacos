package johns.taco;
import javax.swing.JFrame;

import java.awt.Dimension;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
 
public class JungExample {
  public static void main(String[] args) {
    DirectedSparseGraph<String,String> g = new DirectedSparseGraph<>();
    g.addVertex("Vertex1");
    g.addVertex("Vertex2");
    g.addVertex("Vertex3");
    g.addEdge("Edge1", "Vertex1", "Vertex2");
    g.addEdge("Edge2", "Vertex1", "Vertex3");
    g.addEdge("Edge3", "Vertex3", "Vertex1");

    VisualizationImageServer<String, String> vs =
      new VisualizationImageServer<>(
        new CircleLayout<>(g), new Dimension(200, 200));
 
    JFrame frame = new JFrame();
    frame.getContentPane().add(vs);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}