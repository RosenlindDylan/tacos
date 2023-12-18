package johns.taco;

import javax.swing.JFrame;
import java.awt.Dimension;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class General {

    public static void main(String[] args) {
        int n = 10;
        RandomGraph x = new RandomGraph(n);
        

        // eventually make this a method of graph class
        VisualizationImageServer<String,String> vs = 
            new VisualizationImageServer<>(
            new CircleLayout<>(x.g), new Dimension(400, 400));
 
        JFrame frame = new JFrame();
        frame.getContentPane().add(vs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        x.twoColor();
    }
}