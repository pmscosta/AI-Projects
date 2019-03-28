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
    private PriorityQueue<Klotski> priorityQueueStar = new PriorityQueue<Klotski>(11, new Comparator<Klotski>() {
        @Override
        public int compare(Klotski klotski, Klotski that) {
            int finalThis = klotski.calculateH() + klotski.g;
            int finalThat = that.calculateH() + that.g;

            if (finalThis < finalThat)
                return -1;
            else if (finalThis > finalThat)
                return 1;
            else
                return 0;
        }
    });

    private HashMap<Klotski, Integer> mapF = new HashMap<>();

    private Set<Klotski> visited = new HashSet<>();

    private int[][] starting_map = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 1, 1, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] original_map = { { 2, 4, 4, 2 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] starting_easy = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 1, 1, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] test_vertical = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_square = { { 0, 4, 4, 0 }, { 0, 4, 4, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_vr = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_vh = { { 0, 3, 3, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] hard = { { 1, 4, 4, 1 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 1, 3, 3, 1 }, { 0, 3, 3, 0 } }; // 120
    private int[][] hard2 = { { 1, 4, 4, 1 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 1, 3, 3, 1 }, { 0, 3, 3, 0 } };
    private int[][] hard3 = { { 1, 4, 4, 1 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 1, 3, 3, 1 }, { 0, 3, 3, 0 } };

    public void start() {
        Klotski klotski = new Klotski(original_map.clone());
        Klotski klotski2 = new Klotski(hard2.clone());
        Klotski klotski3 = new Klotski(hard3.clone());

        this.visited = new HashSet<>();

        priorityQueueStar.add(klotski);
        this.astar();

        // queue.add(klotski);
        // this.breath_first();

        // priorityQueue.add(klotski2);
        // this.greedy();

        // this.visited = new HashSet<>();

        // stack.add(klotski3);
        // queue.add(klotski);
        // this.depth_first();

        this.visited = new HashSet<>();
        this.breath_first();

        // System.out.println("Original:\n");

        // Utilities.printMap(klotski.constructMap());

        // System.out.println("\n");

    }

    public void printQueue(PriorityQueue<Klotski> queue) {
        System.out.println("Initial");
        for (Klotski k : queue) {
            Utilities.printMap(k.constructMap());
            System.out.println("H= " + k.calculateH());
            System.out.println("\n------\n");
        }

    }

    public void greedy() {
        int steps = 0;
        while (!priorityQueue.isEmpty()) {
            steps++;
            Klotski klotski = priorityQueue.poll();

            if (klotski.isSolution()) {
                System.out.println("\nGreedy - Iterations=" + steps + "\nSolution steps: " + klotski.g + "\n");
                // Utilities.printMap(klotski.constructMap());
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

    public void astar() {
        int steps = 0;
        while (!priorityQueueStar.isEmpty()) {
            steps++;
            Klotski klotski = priorityQueueStar.poll();

            visited.add(klotski);

            if (klotski.isSolution()) {
                System.out.println("\nSteps=" + steps + "\nSolution:\n");
                System.out.println("\nMoves=" + klotski.g + "\n");
                printSolution(klotski);
                Utilities.printMap(klotski.constructMap());
                priorityQueueStar.clear();
                return;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {
                nextPuzzle.g = klotski.g;
                nextPuzzle.g += 1;

                if (!visited.contains(nextPuzzle)) {
                    Integer f = mapF.get(nextPuzzle);

                    if (f == null) {
                        nextPuzzle.parent = klotski;
                        priorityQueueStar.add(nextPuzzle);
                        mapF.put(nextPuzzle, nextPuzzle.g);
                    } else {
                        if (nextPuzzle.g < f) {
                            nextPuzzle.parent = klotski;
                            mapF.put(nextPuzzle, nextPuzzle.g);
                            priorityQueueStar.remove(nextPuzzle);
                            priorityQueueStar.add(nextPuzzle);
                        }
                    }
                }

            }
        }

        System.out.println("Found no solution with " + steps + " steps");

    }

    private void printSolution(Klotski klotski) {
        int i = -1;
        LinkedList<Klotski> list = new LinkedList<>();

        while (klotski.parent != null) {
            list.addFirst(klotski);
            klotski = klotski.parent;
        }
        list.addFirst(klotski);

        for (Klotski elem : list) {
            System.out.println(++i);
            Utilities.printMap(elem.constructMap());
        }
    }

    public void breath_first() {
        int steps = 0;
        while (!queue.isEmpty()) {
            steps++;
            Klotski klotski = queue.poll();

            if (klotski.isSolution()) {
                System.out.println("\nSteps=" + steps + "\nSolution:\n");
                System.out.println("\nMoves=" + klotski.g + "\n");
                Utilities.printMap(klotski.constructMap());
                queue.clear();
                return;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {
                nextPuzzle.g = klotski.g;
                nextPuzzle.g++;

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
                System.out.println("\nDepth - Iterations=" + steps + "\nSolution steps: " + klotski.g + "\n");
                // Utilities.printMap(klotski.constructMap());
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