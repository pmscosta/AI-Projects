package Solver;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import Model.Utilities;

import Model.Klotski;

public class Greedy {
    private Set<Klotski> visited = new HashSet<>();

    private int heuristic;

    private Double memoryMax = -1.0;

    private PriorityQueue<Klotski> priorityQueue = new PriorityQueue<Klotski>(11, new Comparator<Klotski>() {
        @Override
        public int compare(Klotski klotski, Klotski that) {
            int finalThis = calculateH(klotski);
            int finalThat = calculateH(that);

            if (finalThis < finalThat)
                return -1;
            else if (finalThis > finalThat)
                return 1;
            else
                return 0;
        }
    });

    public Greedy(Klotski map, int heuristic) {
        priorityQueue.add(map);
        this.heuristic = heuristic;
    }

    public int calculateH(Klotski map) {
        if (heuristic == 1)
            return map.heuristic1();
        else
            return map.greedyV2();
    }

    public Klotski solve() {
        int steps = 0;

        long initial = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        while (!priorityQueue.isEmpty()) {
            steps++;

            memoryMax = Double.max(memoryMax, Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            Klotski klotski = priorityQueue.poll();

            if (klotski.isSolution()) {
                System.out.println("Steps=" + steps);

                System.out.println(
                        "Memory usage= " + Utilities.humanReadableByteCount((long) (memoryMax - initial), true));
                priorityQueue.clear();
                return klotski;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {
                nextPuzzle.g = klotski.g;
                nextPuzzle.g += 1;

                if (!visited.contains(nextPuzzle)) {
                    nextPuzzle.parent = klotski;
                    priorityQueue.add(nextPuzzle);
                    visited.add(nextPuzzle);
                }

            }
        }

        System.out.println("Found no solution with " + steps + " steps");
        return null;
    }
}
