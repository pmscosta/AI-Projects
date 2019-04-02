package Solver;

import Model.Utilities;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import Model.Klotski;

public class Breadth {

    private Queue<Klotski> queue = new LinkedList<>();
    private Set<Klotski> visited = new HashSet<>();
    private Double memoryMax = -1.0;

    public Breadth(Klotski map) {
        queue.add(map);
    }

    public Klotski solve() {
        int steps = 0;
        long initial = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        while (!queue.isEmpty()) {
            steps++;
            memoryMax = Double.max(memoryMax, Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            Klotski klotski = queue.poll();

            if (klotski.isSolution()) {
                System.out.println("Steps=" + steps);
                System.out.println(
                        "Memory usage= " + Utilities.humanReadableByteCount((long) (memoryMax - initial), true));
                queue.clear();
                return klotski;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {
                nextPuzzle.g = klotski.g;
                nextPuzzle.g++;

                if (!visited.contains(nextPuzzle)) {
                    nextPuzzle.parent = klotski;
                    queue.add(nextPuzzle);
                    visited.add(nextPuzzle);
                }
            }

        }

        System.out.println("Found no solution with " + steps + " steps");
        return null;
    }

}
