import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.LinkedList;

public class Solver {

    private final Deque<Board> solutionsBoard;
    private boolean isSolvable; // default is false

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();

        solutionsBoard = new LinkedList<>();
        SearchNode solutionNode = null;
        MinPQ<SearchNode> priorityQueue = new MinPQ<>();
        priorityQueue.insert(new SearchNode(null, initial, 0));


        while (true) {
            SearchNode curr = priorityQueue.delMin();
            Board currBoard = curr.getBoard();
            // reach the goal
            if (currBoard.isGoal()) {
                isSolvable = true;
                solutionNode = curr;
                break;
            }
            // unsolvable board (swap is goal and hamming = 2)
            if (currBoard.twin().isGoal() && currBoard.hamming() == 2) {
                isSolvable = false;
                break;
            }
            int moves = curr.getMoves();
            Board prevBoard = moves > 0 ? curr.getPrevNode().getBoard() : null;
            for (Board neighborBoard:currBoard.neighbors()) {
                // when the neighbor is the same as the previous board, skip the board
                if (prevBoard != null && neighborBoard.equals(prevBoard)) continue;
                priorityQueue.insert(new SearchNode(curr, neighborBoard, moves + 1));
            }

        }

        while (solutionNode != null) {
            solutionsBoard.addFirst(solutionNode.getBoard());
            solutionNode = solutionNode.prevNode;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable) return -1;
        return solutionsBoard.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable) return solutionsBoard;
        return null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final SearchNode prevNode;
        private final Board currBoard;
        private final int moves;
        private final int priority;
        SearchNode(SearchNode prevBoard, Board currBoard, int moves) {
            this.prevNode = prevBoard;
            this.currBoard = currBoard;
            this.moves = moves;
            this.priority = currBoard.manhattan() + moves;
        }

        // calculates the priority of the node
        public int getPriority() {
            return priority;
        }

        public SearchNode getPrevNode() {
            return prevNode;
        }

        public Board getBoard() {
            return currBoard;
        }

        public int getMoves() {
            return moves;
        }
        @Override
        public int compareTo(SearchNode that) {
            return this.getPriority() - that.getPriority();
        }
    }


    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}