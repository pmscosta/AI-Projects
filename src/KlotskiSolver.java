import java.util.*;
import java.io.Console;
import java.io.IOException;


@SuppressWarnings("Duplicates")
public class KlotskiSolver {

    private Queue<Klotski> queue = new LinkedList<>();
    private PriorityQueue<Klotski> priorityQueue = new PriorityQueue<Klotski>();
    private final Set<Klotski> visited = new HashSet<>();

    private int[][] starting_map = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 1, 1, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] original_map = { { 2, 4, 4, 2 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] starting_easy = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 1, 1, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] test_vertical = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_square = { { 0, 4, 4, 0 }, { 0, 4, 4, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_vr = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_vh = { { 0, 3, 3, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] hard = { { 1, 4, 4, 1 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 1, 3, 3, 1 }, { 0, 3, 3, 0 } };

    public void start() {

        Klotski klotski = new Klotski(starting_map);

        priorityQueue.add(klotski);

        System.out.println("Original:\n");
        
        System.out.println("\n");

        this.astar();

        // this.depth_first(klotski);
    }

    public void printQueue(PriorityQueue<Klotski> queue){
        System.out.println("Initial");
        for(Klotski k:queue){
            Utilities.printMap(k.constructMap());
            System.out.println("H= " + k.calculateH());
            System.out.println("\n------\n");
        }

    }
    public void astar() {
        int steps=0;
        while (!priorityQueue.isEmpty()) {
            steps++;
            Klotski klotski = priorityQueue.poll();
            //Utilities.printMap(klotski.constructMap());
            /*try{
              //  System.out.println(klotski.calculateH());
            System.in.read();
            }catch(IOException e){
                e.printStackTrace();
            }*/
            if (klotski.isSolution()) {
                System.out.println("\nSteps="+steps  + "\nSolution:\n");
                Utilities.printMap(klotski.constructMap());
                priorityQueue.clear();
                return;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {

                if (!visited.contains(nextPuzzle)) {

                    priorityQueue.add(nextPuzzle);
                    visited.add(nextPuzzle);
                }

            }

            // printQueue(queue);
        }

        System.out.println("Found no solution with " + steps + " steps");

    }

    public void breath_first() {
        int steps=0;
        while (!queue.isEmpty()) {
            steps++;
            Klotski klotski = queue.poll();
             //Utilities.printMap(klotski.constructMap());
            /*try{
              //  System.out.println(klotski.calculateH());
            System.in.read();
            }catch(IOException e){
                e.printStackTrace();
            }*/
            if (klotski.isSolution()) {
                System.out.println("\nSteps="+steps  + "\nSolution:\n");
                Utilities.printMap(klotski.constructMap());
                queue.clear();
                return;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {

                if (!visited.contains(nextPuzzle)) {
                    
                    queue.add(nextPuzzle);
                    visited.add(nextPuzzle);
                }

            }

           // printQueue(queue);
        }

        System.out.println("Found no solution with " + steps + " steps");

    }

    public void depth_first(Klotski klotski) {

        if (klotski.isSolution()) {
            System.out.println("\nSolution:\n");
            queue.clear();
            return;
        }

        visited.add(klotski);

        for (Klotski nextPuzzle : klotski.getNextBoards()) {
            if (!visited.contains(nextPuzzle)) {
                depth_first(klotski);
            }
        }

        System.out.println("Empty Queue!");
    }

    public static void main(String[] args) {

        KlotskiSolver solver = new KlotskiSolver();

        solver.start();

    }

}