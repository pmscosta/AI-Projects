import java.util.*;
import java.io.Console;
import java.io.IOException;

@SuppressWarnings("Duplicates")
public class KlotskiSolver {

    private Queue<Klotski> queue = new LinkedList<>();
    private Stack<Klotski> stack = new Stack<>();
    private PriorityQueue<Klotski> priorityQueue = new PriorityQueue<Klotski>(30, new Comparator<Klotski>() {
        @Override
        public int compare(Klotski klotski, Klotski that) {
            int hThis = klotski.calculateH();
            int hThat = that.calculateH();

            if (hThis < hThat)
                return 1;
            else if (hThis > hThat)
                return -1;
            else
                return 0;
        }
    });
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

        Klotski klotski = new Klotski(original_map);

        priorityQueue.add(klotski);

        stack.add(klotski);

        queue.add(klotski);

        System.out.println("Original:\n");

        Utilities.printMap(klotski.constructMap());

        System.out.println("\n");

        this.astar();

    }

    public void printQueue(PriorityQueue<Klotski> queue) {
        System.out.println("Initial");
        for (Klotski k : queue) {
            Utilities.printMap(k.constructMap());
            System.out.println("H= " + k.calculateH());
            System.out.println("\n------\n");
        }

    }

    public void astar() {
        int steps = 0;
        while (!priorityQueue.isEmpty()) {
            steps++;
            Klotski klotski = priorityQueue.poll();

            if (klotski.isSolution()) {
                System.out.println("\nSteps=" + steps + "\nSolution:\n");
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
        }

        System.out.println("Found no solution with " + steps + " steps");

    }

    public void breath_first() {
        int steps = 0;
        while (!queue.isEmpty()) {
            steps++;
            Klotski klotski = queue.poll();

            if (klotski.isSolution()) {
                System.out.println("\nSteps=" + steps + "\nSolution:\n");
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

        }

        System.out.println("Found no solution with " + steps + " steps");

    }

    public void depth_first() {

        int steps = 0;
        while (!stack.isEmpty()) {
            steps++;
            Klotski klotski = stack.pop();

            if (klotski.isSolution()) {
                System.out.println("\nSteps=" + steps + "\nSolution:\n");
                Utilities.printMap(klotski.constructMap());
                stack.clear();
                return;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {

                if (!visited.contains(nextPuzzle)) {

                    stack.push(nextPuzzle);
                    visited.add(nextPuzzle);
                }

            }

        }

        System.out.println("Found no solution with " + steps + " steps");

    }

    public static void main(String[] args) {

        KlotskiSolver solver = new KlotskiSolver();

        solver.start();

    }

}