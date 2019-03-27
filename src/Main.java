/**
 * Michael Powell
 * 8 Puzzle Solver with A* Algorithm
 */

public class Main {

    public static void main(String[] args) {

        // Create starting node
        Node start = new Node(new int[][]{
            {8, 7, 1},
            {6, 0, 2},
            {5, 4, 3}
        });

        // Create goal node
        Node goal = new Node(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        });

        // Initialize AStar
        AStar aStar = new AStar(start, goal);

        // Run the algorithm on the puzzles
        aStar.initialize();
    }

}
