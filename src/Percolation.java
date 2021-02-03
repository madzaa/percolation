import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final Boolean[][] grid;
    private int numberOfOpenSites;
    private final int gridSize;
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.gridSize = n;
        this.grid = new Boolean[n][n];
        this.numberOfOpenSites = 0;
        this.top = 0;
        this.bottom = (n * n) + 1;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(((n * n) + 2));
    }

    public void open(int i, int j) {
        int row = i - 1;
        int col = j - 1;
        if (row < 0 || col < 0 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException();
        }

        if (grid[row][col] == null) {
            grid[row][col] = false;
        } else {
            return;
        }

        if (row + 1 < gridSize && grid[row + 1][col] != null) {
            if (isOpen(i + 1, j)) {
                weightedQuickUnionUF.union(xyTo1D(row + 1, col), xyTo1D(row, col));
                if (grid[row + 1][col]) {
                    grid[row][col] = true;
                }
            }
        }
        if (col + 1 < gridSize && grid[row][col + 1] != null) {
            if (isOpen(i, j + 1)) {
                weightedQuickUnionUF.union(xyTo1D(row, col + 1), xyTo1D(row, col));
                if (grid[row][col + 1]) {
                    grid[row][col] = true;
                }
            }
        }
        if (row - 1 >= 0 && grid[row - 1][col] != null) {
            if (isOpen(i - 1, j)) {
                weightedQuickUnionUF.union(xyTo1D(row - 1, col), xyTo1D(row, col));
                if (grid[row - 1][col]) {
                    grid[row][col] = true;
                }
            }
        }
        if (col - 1 >= 0 && grid[row][col - 1] != null) {
            if (isOpen(i , j - 1)) {
                weightedQuickUnionUF.union(xyTo1D(row, col - 1), xyTo1D(row, col));
                if (grid[row][col - 1]) {
                    grid[row][col] = true;
                }
            }
        }
        if (row == 0) {
            weightedQuickUnionUF.union(xyTo1D(row, col), top);
            grid[row][col] = true;
        }
        if (row == gridSize - 1) {
            weightedQuickUnionUF.union(xyTo1D(row, col), bottom);
        }
        numberOfOpenSites++;
    }

    public boolean isOpen(int i, int j) {
        int row = i - 1;
        int col = j - 1;
        if (row < 0 || col < 0 || row > gridSize || col > gridSize ) {
            throw new IllegalArgumentException();
        }
        if (grid[row][col] == null) {
            return false;
        }
        if (grid[row][col]) {
            return isFull(i,j);
        }
        return !grid[row][col];
    }

    public boolean isFull(int i, int j) {
        int row = i - 1;
        int col = j - 1;
        if (row < 0 || col < 0 || row > gridSize  || col > gridSize ) {
            throw new IllegalArgumentException();
        }
        if (grid[row][col] == null) {
            return false;
        }
        return weightedQuickUnionUF.find(top) == weightedQuickUnionUF.find(xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.find(this.top) == weightedQuickUnionUF.find(this.bottom);
    }

    private int xyTo1D(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
        return ((row * gridSize) + col) + 1;
    }
}
