package johns.taco;

import javax.swing.JFrame;
import java.awt.Dimension;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class GreedyKColor {
    public static void main(String[] args) {
        RandomGraph x;
        
        int n = 7; // graph size

        // ArrayList<Integer> res = new ArrayList<>();

        // for (int i = 0; i < 10; i++) {
        //     g = new RandomGraph(n);
        //     res.add(g.greedyKColor());
        // }

        // for (Integer term : res) {
        //     System.out.print(term + " ");
        // }
        int value = 0;
        while (value != 2) {
            x = new RandomGraph(n);
            value = x.greedyKColor();
            System.out.println(value);
            
            if (value == 2) {
                VisualizationImageServer<String,String> vs = 
                    new VisualizationImageServer<>(
                    new CircleLayout<>(x.g), new Dimension(400, 400));
    
                JFrame frame = new JFrame();
                frame.getContentPane().add(vs);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        }
    }
}
