import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        // Create starting node
        Node start = new Node(new int[][]{
            {8, 0, 6},
            {5, 4, 2},
            {7, 3, 1}
        });

        // Create goal node
        Node goal = new Node(new int[][]{
            {0, 1, 2},
            {6, 7, 8},
            {3, 4, 5}
        });

        // Initialize AStar
        AStar aStar = new AStar(start, goal);

        // Run the algorithm on the puzzles
        aStar.initialize();
    }

}
