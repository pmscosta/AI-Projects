import java.util.*;

public class KlotskiSolver {

    private PriorityQueue<Klotski> queue = new PriorityQueue<>();
    private final Set<Klotski> visited = new HashSet<>();

    private int[][] starting_map = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 3, 3, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] original_map = { { 2, 4, 4, 2 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] starting_easy = { { 1, 4, 4, 1 }, { 1, 4, 4, 1 }, { 2, 1, 1, 2 }, { 2, 1, 1, 2 }, { 1, 0, 0, 1 } };

    private int[][] test_vertical = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 2, 2, 0, 0 }, { 2, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_square = { { 0, 4, 4, 0 }, { 0, 4, 4, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_vr = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] test_vh = { { 0, 3, 3, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

    private int[][] hard = { { 1, 4, 4, 1 }, { 2, 4, 4, 2 }, { 2, 3, 3, 2 }, { 1, 3, 3, 1 }, { 0, 3, 3, 0 } };

    public void start() {

        Klotski klotski = new Klotski(starting_easy);

        queue.add(klotski);

        System.out.println("Original:\n");
        Utilities.printMap(klotski.constructMap());

        this.breath_first();

        // this.depth_first(klotski);
    }

    public void breath_first() {
        while (!queue.isEmpty()) {

            Klotski klotski = queue.poll();

            if (klotski.isSolution()) {
                System.out.println("\nSolution:\n");
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