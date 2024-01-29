package johns.taco;

import javax.swing.JFrame;
import java.awt.Dimension;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class DSatur {
    public static void main(String[] args) {
        RandomGraph x;
        
        int n = 10; // graph size

        int value = 0;
        x = new RandomGraph(n);
        value = x.dSatur();
        System.out.println("DSatur found chromatic number k = " + value);
                    
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
