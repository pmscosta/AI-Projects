package Solver;

import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Klotski;
import Model.Utilities;

public class KlotskiSolver {

    private static Scanner in = new Scanner(System.in);

    public int[][] selectMap() {

        System.out.println("\n\n***** Please choose the map *****");
        System.out.println("*****   1 - Easy            *****");
        System.out.println("*****   2 - Medium          *****");
        System.out.println("*****   3 - Hard            *****");
        System.out.println("*****   4 - Read From File  *****");

        int option = 0;

        System.out.print("Select one of the above options: ");

        while (option < 1 || option > 4) {
            try {
                option = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please select one of the above options");
                option = 0;
            }
            in.nextLine();
        }
        switch (option) {
        case 1:
            return MapExamples.starting_easy;
        case 2:
            return MapExamples.original_map;
        case 3:
            return MapExamples.hard;
        case 4:
            System.out.print("Please type the filename: ");
            int[][] result = MapExamples.readMapFromFile(in.nextLine());
            if (result != null)
                return result;
            else
                break;
        }

        return MapExamples.original_map;

    }

    public Klotski start(int option) {

        int[][] map = selectMap();

        Klotski klotski = new Klotski(map);

        System.out.println("Initial Map:");
        Utilities.printMap(klotski.constructMap());
        System.out.println();

        Klotski endNode = null;

        switch (option) {
        case 1:
            Breadth breadth = new Breadth(klotski);
            endNode = breadth.solve();
            break;

        case 2:
            Depth depth = new Depth(klotski);
            endNode = depth.solve();
            break;

        case 3:
            AStar aStar = new AStar(klotski);
            endNode = aStar.solve();
            break;

        case 4:
            Greedy greedy = new Greedy(klotski);
            endNode = greedy.solve();
            break;
        case 5:
            IterativeDepth iterativeDepth = new IterativeDepth(klotski);
            endNode = iterativeDepth.solve();
            break;
        }

        return endNode;
    }

    public static void main(String[] args) {

        KlotskiSolver solver = new KlotskiSolver();

        System.out.println("*****    Klotski Solver    *****");
        System.out.println("*****  1 - Breadth Search  *****");
        System.out.println("*****  2 - Depth Search    *****");
        System.out.println("*****  3 - A Star Search   *****");
        System.out.println("*****  4 - Greedy Search   *****");
        System.out.println("*****  5 - Iterative Depth *****");

        int option = 0;

        System.out.print("Select one of the above options: ");

        while (option < 1 || option > 5) {
            try {
                option = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please select one of the above options");
                option = 0;
            }

            in.nextLine();
        }

        long startTime = System.currentTimeMillis();

        Klotski endNode = solver.start(option);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution took: " + elapsedTime + " miliseconds.");

        System.out.println("** Do you want to see all the steps:");
        System.out.println("** 1 - Yes ");
        System.out.println("** 2 - No  ");

        int printMap = 0;

        while (printMap < 1 || printMap > 2) {

            System.out.print("Select one of the above options: ");

            try {
                printMap = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\rPlease select one of the above options");
            }

        }

        if (printMap == 1)
            endNode.printSolution();

        in.close();
    }

}