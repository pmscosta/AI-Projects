import java.util.PriorityQueue;

public class KlotskiSolver{

    private PriorityQueue<Klotski> queue = new PriorityQueue<>();

    private int[][] starting_map = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 3, 3, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };


    public void start(){

        Klotski klotski = new Klotski(starting_map);

        queue.add(klotski);

        klotski.printMap();

        klotski.printHashMap();
    }

    public static void main(String[] args){

            KlotskiSolver solver = new KlotskiSolver();

            solver.start();

    
    }


}