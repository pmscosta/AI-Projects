package Solver;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import Model.Klotski;

public class AStar {
    private Set<Klotski> visited = new HashSet<>();
    private HashMap<Klotski, Integer> mapF = new HashMap<>();
    private Double memoryMax = -1.0;

    private PriorityQueue<Klotski> priorityQueue = new PriorityQueue<Klotski>(11, new Comparator<Klotski>() {
        @Override
        public int compare(Klotski klotski, Klotski that) {
            int finalThis = calculateH(klotski) + klotski.g;
            int finalThat = calculateH(that) + that.g;

            if (finalThis < finalThat)
                return -1;
            else if (finalThis > finalThat)
                return 1;
            else
                return 0;
        }
    });

    public AStar(Klotski map) {
        priorityQueue.add(map);
    }

    public int calculateH(Klotski map) {
        return map.heuristic3();
    }


    public Klotski solve() {
        int steps = 0;
        long initial = Runtime.getRuntime().freeMemory();

        while (!priorityQueue.isEmpty()) {
            memoryMax = Double.max(memoryMax,Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

            steps++;
            Klotski klotski = priorityQueue.poll();

            visited.add(klotski);

            if (klotski.isSolution()) {
                System.out.println("Steps=" + steps);
                priorityQueue.clear();
                System.out.println("Memory usage= " +  ((int) (100*((memoryMax- initial)/Runtime.getRuntime().totalMemory()))) + "%");
                return klotski;
            }

            for (Klotski nextPuzzle : klotski.getNextBoards()) {
                nextPuzzle.g = klotski.g;
                nextPuzzle.g += 1;

                if (!visited.contains(nextPuzzle)) {
                    Integer f = mapF.get(nextPuzzle);

                    if (f == null) {
                        nextPuzzle.parent = klotski;
                        priorityQueue.add(nextPuzzle);
                        mapF.put(nextPuzzle, nextPuzzle.g);
                    } else {
                        if (nextPuzzle.g < f) {
                            nextPuzzle.parent = klotski;
                            mapF.put(nextPuzzle, nextPuzzle.g);
                            priorityQueue.remove(nextPuzzle);
                            priorityQueue.add(nextPuzzle);
                        }
                    }
                }

            }
        }

        System.out.println("Found no solution with " + steps + " steps");
        return null;
    }

}
