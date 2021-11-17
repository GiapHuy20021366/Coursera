import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] sites;   // Default sites[i][j] = 0, not open
    private final WeightedQuickUnionUF wqfGrid;
    private final WeightedQuickUnionUF wqfFull;
    private final int gridSize;
    private final int virtualTop;
    private final int virtualBottom;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid argument : gridSize must be larger than zero!");
        gridSize = n;
        final int siteCount = n * n;
        sites = new boolean[gridSize][gridSize];
        wqfGrid = new WeightedQuickUnionUF(siteCount + 2); // includes virtual top and bottom
        wqfFull = new WeightedQuickUnionUF(siteCount + 1); // includes virtual top
        virtualBottom = siteCount + 1;
        virtualTop = siteCount;
        openSites = 0;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);

        int i = row - 1;
        int j = col - 1;

        // Size was opened
        if (isOpen(row, col)) {
            return;
        }

        int wqfIndex = wqfIndex(row, col);

        // Open Site
        sites[i][j] = true;
        ++openSites;

        if (row == 1) {  // Top Row
            wqfGrid.union(virtualTop, wqfIndex);
            wqfFull.union(virtualTop, wqfIndex);
        }

        // On bottom row
        if (row == gridSize) {
            wqfGrid.union(virtualBottom, wqfIndex);
        }

        // Check and Union top Site if it was opened
        if (isOnGrid(row - 1, col) && isOpen(row - 1, col)) {
            wqfGrid.union(wqfIndex, wqfIndex(row - 1, col));
            wqfFull.union(wqfIndex, wqfIndex(row - 1, col));
        }

        // Check and Union down Site if it was opened
        if (isOnGrid(row + 1, col) && isOpen(row + 1, col)) {
            wqfGrid.union(wqfIndex, wqfIndex(row + 1, col));
            wqfFull.union(wqfIndex, wqfIndex(row + 1, col));
        }

        // Check and Union left Site if it was opened
        if (isOnGrid(row, col - 1) && isOpen(row, col - 1)) {
            wqfGrid.union(wqfIndex, wqfIndex(row, col - 1));
            wqfFull.union(wqfIndex, wqfIndex(row, col - 1));
        }

        // Check and Union right Site if it was opened
        if (isOnGrid(row, col + 1) && isOpen(row, col + 1)) {
            wqfGrid.union(wqfIndex, wqfIndex(row, col + 1));
            wqfFull.union(wqfIndex, wqfIndex(row, col + 1));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return sites[row - 1][col - 1];

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        return wqfFull.find(virtualTop) == wqfFull.find(wqfIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqfGrid.find(virtualTop) == wqfGrid.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    // return index of site(row, col) in wdf
    private int wqfIndex(int row, int col) {
        return gridSize * (row - 1) + col - 1;
    }

    // Valid row and col check
    private void validateSite(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IllegalArgumentException("Index is out of bounds");
        }
    }

    // Check site(row, col) is on grid
    private boolean isOnGrid(int row, int col) {
        return (row >= 1 && col >= 1 && row <= gridSize && col <= gridSize);
    }

}
