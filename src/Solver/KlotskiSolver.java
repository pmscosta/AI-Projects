package Solver;

import java.util.*;

import Model.Klotski;
import Model.Utilities;

public class KlotskiSolver {

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
    private int[][] level3 = { { 1, 4, 4, 1 }, { 2, 4, 4, 1 }, { 2, 1, 1, 1 }, { 2, 1, 1, 2 }, { 2, 0, 0, 2 } };

    public void start() {
        Klotski klotski = new Klotski(original_map);

        AStar aStar = new AStar(klotski);
        aStar.solve();

        // Greedy greedy = new Greedy(klotski);
        // greedy.solve();

        // Breadth breadth = new Breadth(klotski);
        // breadth.solve();

        // Depth depth = new Depth(klotski);
        // depth.solve();
    }
    
    public static void main(String[] args) {
        KlotskiSolver solver = new KlotskiSolver();
        solver.start();
    }

}