import java.util.Arrays;
import java.util.Stack;

public class Board {

    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = copy(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder(tiles.length + "\n");
        for (int[] tile : tiles) {
            for (int j = 0; j < tiles.length; j++) {
                builder.append(String.format("%2d ", tile[j]));
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != 0 && i * dimension() + j + 1 != tiles[i][j])
                    ++hamming;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i * dimension() + j + 1) {
                    int x = (tiles[i][j] - 1) / dimension();
                    int y = (tiles[i][j] - 1) % dimension();
                    manhattan += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null || y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int blank = getBlankPosition();

        int i = blank / dimension();
        int j = blank % dimension();

        if (i > 0) {
            neighbors.push(new Board(swap(i, j, i - 1, j)));
        }
        if (j > 0) {
            neighbors.push(new Board(swap(i, j, i, j - 1)));
        }
        if (i < dimension() - 1) {
            neighbors.push(new Board(swap(i, j, i + 1, j)));
        }
        if (j < dimension() - 1) {
            neighbors.push(new Board(swap(i, j, i, j + 1)));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (getBlankPosition() / dimension() != 0) return new Board(swap(0, 0, 0, 1));
        else return new Board(swap(1, 0, 1, 1));
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

    private int[][] copy(int[][] tiles) {
        int[][] newTiles = new int[tiles.length][tiles.length];

        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, newTiles[i], 0, tiles.length);
        }

        return newTiles;
    }

    private int[][] swap(int i1, int j1, int i2, int j2) {
        int[][] tilesCopy = copy(this.tiles);
        int t = tilesCopy[i1][j1];
        tilesCopy[i1][j1] = tilesCopy[i2][j2];
        tilesCopy[i2][j2] = t;
        return tilesCopy;
    }

    private int getBlankPosition() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) return i * dimension() + j;
            }
        }
        return -1;
    }

}