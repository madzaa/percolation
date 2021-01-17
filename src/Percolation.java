import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Percolation {
    private final boolean[][] grid;
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
        this.grid = new boolean[n][n];
        this.numberOfOpenSites = 0;
        this.top = 0;
        this.bottom = (n * n) + 2;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF((bottom));
    }

    public void open(int row, int col) {

        int shiftDown = row - 1;
        int shiftLeft = col - 1;

        if (row < 0 || col < 0 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException();
        }

        if (isFull(row, col)) {
            return;
        }

        this.grid[shiftDown][shiftLeft] = true;
        connected(row, col);
        numberOfOpenSites++;
    }

    private void connected(int row, int col) {

        int north = row - 1;
        int south = row + 1;
        int east = col - 1;
        int west = col + 1;

        if (isOnGrid(north, col) && isFull(north,col)) {
            LOGGER.log(Level.INFO,"Checking north...");
            weightedQuickUnionUF.union(xyTo1D(row, col), (xyTo1D(north, col)));
            LOGGER.log(Level.INFO, "Connected north " + row + " & " + north);
        }
        if (isOnGrid(south,col) && isFull(south,col)) {
            LOGGER.log(Level.INFO,"Checking south...");
            weightedQuickUnionUF.union(xyTo1D(row, col), (xyTo1D(south, col)));
            LOGGER.log(Level.INFO,"Connected south " + row + " & " + south);
        }
        if (isOnGrid(row,east) && isFull(row,east)) {
            LOGGER.log(Level.INFO,"Checking east...");
            weightedQuickUnionUF.union(xyTo1D(row, col), (xyTo1D(row, east)));
            LOGGER.log(Level.INFO,"Connected east " + col + " & " + east);

        }
        if (isOnGrid(row, west) && isFull(row, west)) {
            LOGGER.log(Level.INFO,"Checking west...");
            weightedQuickUnionUF.union(xyTo1D(row, col), (xyTo1D(row, west)));
            LOGGER.log(Level.INFO, "Connected west " + col + " & " + west);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row > gridSize || col > gridSize ) {
            throw new IllegalArgumentException();
        }
        return !grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row > gridSize  || col > gridSize ) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.find(this.top) == weightedQuickUnionUF.find(this.bottom);
    }

    private boolean isOnGrid(int row, int col) {
        return row - 1 >= 0 && col - 1 >= 0 && row <= gridSize && col <= gridSize;
    }

    private int xyTo1D(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
        return gridSize * (row - 1) + col;
    }

    private static final Logger LOGGER = Logger.getLogger(Percolation.class.getName());

    @Override
    public String toString() {
        return "Percolation { " +
                "grid="  + Arrays.deepToString(grid) + '}';
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1,3);
        percolation.open(1,1);
        percolation.open(1,2);
        percolation.percolates();
        System.out.println(percolation);
    }
}
