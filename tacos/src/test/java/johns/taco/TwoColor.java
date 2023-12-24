package johns.taco;

public class TwoColor {
    
    public static void main(String[] args) {
        RandomGraph g;
        
        int n = 10; // graph size
        int x = 0; // num bipartite
        int y = 0; // num non bipartite
        int generations = 100; // times to run the two color check
        double totalIterations = 0;

        for (int i = 0; i < generations; i++) {
            g = new RandomGraph(n);
            if (g.twoColor()) {
                x++;
            } else {
                y++;
            }
            totalIterations += g.convergence;
        }

        System.out.println(x + " bipartite and " + y + " nonbipartite");
        System.out.println("generating these random graphs of size " + n + " took an average of "
         + totalIterations/generations + " randomizations");
    }
}
