/**
 * Michael Powell
 * 8 Puzzle Solver with A* Algorithm
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.jetbrains.annotations.NotNull;

class AStar {

    private Node start, goal;
    private Board board = new Board();


    AStar(Node start, Node goal) {
        this.start = start;
        this.goal = goal;
    }

    void initialize() {

        List<Node> open = new ArrayList<>(); // Open list to hold yet visited paths
        List<Node> closed = new ArrayList<>(); // Closed list for the paths already visited
        Node current = new Node(new int[start.puzzle.length][]); //Creating node to hold the currently active node

        // Add the starting node to the open list
        open.add(start);

        // If open still has nodes to visit, keep checking paths
        while(!open.isEmpty()) {

            // Get the lowest f score of all in the open list
            current = getLowest(open);

            // If we reach our goal, exit while loop
            if(Arrays.deepEquals(current.puzzle, goal.puzzle))
                break;

            // Current wasn't the right one, so remove from open and add to closed
            open.remove(current);
            closed.add(current);

            // For every path broken down from the current node
            for(Node n : breakdown(current)) {

                // If we've visited this path already go to the next one, if not add to open list
                if(find(n, closed) != null)
                    continue;
                if(find(n, open) == null)
                    open.add(n);
                else {
                    Node node = find(n, open);

                    if(n.getG() < node.getG()) {
                        node.setG(n.getG());
                        node.setF(n.getF());
                        node.parent = n.parent;
                    }
                }


            }
        }

        // Print the full path starting with the goal all the way to the starting parent
        path(current);
    }

    private void printPuzzle(Node goal) {

        int[][] easyGrid = goal.puzzle;
        String lineSplit;
        StringJoiner splitJoiner = new StringJoiner("+", "|", "|");
        for (int index = 0; index < easyGrid[0].length; index++) {
            splitJoiner.add(String.format("%4s", "").replace(" ", "-"));
        }
        lineSplit = splitJoiner.toString();
        for (int[] row : easyGrid) {
            StringJoiner sj = new StringJoiner(" | ", "| ", " |");
            for (int col : row) {
                sj.add(String.format("%2d", col));
            }
            System.out.println(lineSplit);
            System.out.println(sj.toString());
        }
        System.out.println(lineSplit);
        System.out.println("Manhattan Distance: " + board.heuristics(goal, this.goal));
        System.out.println("F Score: " + goal.getF());

    }

    private List<Node> breakdown(Node n) {

        // Let's create a local list to hold the different nodes we'll be getting from the current one
       List<Node> open = new ArrayList<>();
       // There is a temporary copy of the nodes created to prevent references from messing things up
       open.add(board.move(Board.Direction.UP, copyNode(n))); // Up from current zero position
       open.add(board.move(Board.Direction.DOWN, copyNode(n))); // Down
       open.add(board.move(Board.Direction.LEFT, copyNode(n))); // Left
       open.add(board.move(Board.Direction.RIGHT, copyNode(n))); // Right

        // Get rid of all the null nodes from moving zero around
       while(open.remove(null));

       // For every node left in open, set the g and h scores and add up the f score
       for(Node node : open) {
           node.setG(n.getG() + 1);
           node.setH(board.heuristics(node, goal));
           node.setF(node.getG() + node.getH());
           node.parent = n;
       }

       // Return the list with all the new nodes inside
       return open;

    }

    private Node getLowest(@NotNull List<Node> list) {

        // If there's only one node the lowest is that one
        if(list.size() == 1)
            return list.get(0);

        // Give the node lowest a value of null for now
        Node lowest = null;

        // Compare every node in the list to find the lowest f score
        for(Node n : list) {
            if(lowest == null)
                lowest = n;
            else
                lowest = lowest.getF() > n.getF() ? n : lowest;
        }

        // Return the node with the lowest f score
        return lowest;
    }

    private Node copyNode(Node node) {
        // Initializing a completely new node
        Node n = new Node(new int[node.puzzle.length][]);

        // Cloning every row in the puzzle
        for(int i = 0; i < node.puzzle.length; i++)
            n.puzzle[i] = node.puzzle[i].clone();

        // Copying all the rest of the scores
        n.setG(n.getG());
        n.setH(node.getH());
        n.setF(node.getF());

        // Return the newly copied node
        return n;
    }

    private Node find(Node n, List<Node> list) {
        Node actual = null;

        for(Node node : list)
            if(Arrays.deepEquals(node.puzzle, n.puzzle))
                actual = n;

        return actual;
    }

    private void path(Node node) {
        // If there's not another parent, that's the last of them
        if(node.parent == null) {
            printPuzzle(node);
            return;
        }

        printPuzzle(node);
        System.out.println(" |");
        System.out.println(" V");
        path(node.parent);
    }

}
