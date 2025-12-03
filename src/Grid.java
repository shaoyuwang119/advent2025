import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * @author Shaoyu Wang
 */

public class Grid {
    private char[][] grid;

    private int rows;
    private int cols;

    private HashMap<Character, ArrayList<Coords>> charLocations;

    public static final Coords[] dirs = {
            new Coords(-1,0),
            new Coords(0,1),
            new Coords(1,0),
            new Coords(0,-1),
    };

    /**
     * Constructs a new {@code Grid} object with specified file at path.
     * The file must contain lines of equal length.
     * @param path the path to the file to be converted to {@code Grid} object. Use {@code Path.of()}
     *             if necessary to convert the path string to Path object.
     */
    public Grid(Path path) {
        try {
            String input = Files.readString(path);
            initGrid(input);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file.");
        } catch (IOException e) {
            System.out.println("IO exception occurred.");
        }
    }

    /**
     * Constructs a new {@code Grid} object with specified string.
     * The string must contain lines of equal length separated by {@code \n}.
     * @param input the string to be converted to {@code Grid} object.
     */
    public Grid(String input) {
        initGrid(input);
    }

    /**
     * Constructs a blank {@code Grid} object with specified rows and columns.
     * Will not initialize the {@code charLocations} {@code HashMap} object.
     */
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new char[rows][cols];
    }

    private void initGrid(String str) {
        str = str.replaceFirst("(\r\n|\r|\n)$", ""); // remove any newline at the end of the string
        String[] lines = str.split("\n", -1);

        rows = lines.length;
        cols = lines[rows-1].length();
        System.out.println(cols);
        grid = new char[rows][cols];
        charLocations = new HashMap<>();

        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                //System.out.println(c);
                char tile = lines[r].charAt(c);
                grid[r][c] = tile;
                if (!charLocations.containsKey(tile)) {
                    charLocations.put(tile, new ArrayList<>());
                }
                charLocations.get(tile).add(new Coords(r, c));
            }
        }
    }

    public char get(int row, int col) {
        if (inBounds(row, col)) {
            return grid[row][col];
        } else {
            throw new IndexOutOfBoundsException("COORDS(" + row + "," + col + ") ARE OUT OF BOUNDS(" + rows + "," + cols + ")!");
        }
    }

    public char get(Coords pos) {
        if (inBounds(pos.r(), pos.c())) {
            return grid[pos.r()][pos.c()];
        } else {
            throw new IndexOutOfBoundsException("COORDS(" + pos.r() + "," + pos.c() + ") ARE OUT OF BOUNDS(" + rows + "," + cols + ")!");
        }
    }

    // TODO: FIGURE OUT A WAY TO COPY THE ARRAYLIST SO IT RETURNS AN ARRAYLIST OF COORDS
    public Coords getCharLocation(char c) {
        if (charLocations.get(c).size() != 1) {
            throw new IllegalArgumentException("This character can be found multiple times throughout the grid!");
        }
        return charLocations.get(c).getFirst();
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public char[] getRow(int rowIndex) {
        return grid[rowIndex].clone();
    }

    public char[] getCol(int colIndex) {
        char[] col = new char[cols];
        for (int i=0; i<cols; i++) {
            col[i] = get(i, colIndex);
        }
        return col;
    }

    public void set(int row, int col, char replacement) {
        if (inBounds(row, col)) {
            grid[row][col] = replacement;
        } else {
            throw new IndexOutOfBoundsException("COORDS(" + row + "," + col + ") ARE OUT OF BOUNDS(" + rows + "," + cols + ")!");
        }
    }

    public void set(Coords pos, char replacement) {
        if (inBounds(pos.r(), pos.c())) {
            grid[pos.r()][pos.c()] = replacement;
        } else {
            throw new IndexOutOfBoundsException("COORDS(" + pos.r() + "," + pos.c() + ") ARE OUT OF BOUNDS(" + rows + "," + cols + ")!");
        }
    }

    public boolean inBounds(int row, int col) { return row >= 0 && row < rows && col >= 0 && col < cols; }
    public boolean inBounds(Coords pos) { return pos.r() >= 0 && pos.r() < rows && pos.c() >= 0 && pos.c() < cols; }

    public char[][] asArray() {
        char[][] newGrid = new char[grid.length][];
        for (int i=0; i<grid.length; i++) {
            newGrid[i] = grid[i].clone();
        }
        return newGrid;
    }

    public void print() {
        for (char[] r : grid) {
            for (char c : r) {
                if ((int) c == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(c);
                }
            }
            System.out.println();
        }
    }

    public void fill(char c) {
        for (int i=0; i<rows; i++) {
            Arrays.fill(grid[i], c);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grid other)) return false;
        return Arrays.deepEquals(this.grid, other.grid);
    }

    /**
     * Finds the size of a cluster given a set of target characters. This is a simple recursive algorithm
     * that finds a cluster composed only of adjacent target characters.
     *
     * @param start the starting location
     * @param tgt the set of character targets
     * @param visited stores the {@code Coords} already visited locations. Put a preexisting {@code HashSet} object here
     *                if you want to find all the {@code Coords} in the cluster.
     * @return {@code int} value of the size of the cluster.
     */
    public int findClusterSize(Coords start, HashSet<Character> tgt, HashSet<Coords> visited) {
        int out = 1;
        if (visited.contains(start)) {
            return 0;
        }
        if (!inBounds(start)) {
            return 0;
        }

        char e = get(start);
        if (!tgt.contains(e)) {
            return 0;
        }

        visited.add(start);
        int[][] directions = {{-1,0}, {0,1}, {1,0}, {0,-1}};
        for (int i=0; i<4; i++) {
            Coords tempPos = start.add(directions[i][0], directions[i][1]);
            out += findClusterSize(tempPos, tgt, visited);
        }

        return out;
    }

}