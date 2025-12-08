import utils.Grid;

import java.io.*;
import java.nio.file.*;

public class Day7 {
    public static void main(String[] args) throws IOException{
        long startTime = System.nanoTime();

        String file = "src/input.txt";
        solve(file);

        System.err.println("time (ms): " + (System.nanoTime()-startTime)/1_000_000);
    }

    static void solve(String file) throws IOException {
        Grid grid = new Grid(Path.of(file));

        long[][] timelines = new long[grid.rows()][grid.cols()];
        int r = grid.getCharLoc('S').r(); int c = grid.getCharLoc('S').c();
        long p1 = getSplits(r+1, c, grid);
        long p2 = getTimelines(r+1, c, grid, timelines);

        System.out.println(p1 + " " + p2);

    }

    static long getSplits(int r, int c, Grid grid) {
        long splits = 0;
        if (r == grid.rows() || grid.get(r, c) == '|') return 0;

        if (grid.get(r,c) == '^') {
            splits++;
            splits += getSplits(r,c+1, grid) + getSplits(r, c-1, grid);
        } else {
            grid.set(r, c, '|');
            splits += getSplits(r+1, c, grid);
        }

        return splits;
    }

    static long getTimelines(int r, int c, Grid grid, long[][] timelines) {
        long tls = 0;

        if (r == grid.rows()) return 1;
        if (timelines[r][c] != 0) return timelines[r][c];

        if (grid.get(r,c) == '^') {
            tls += getTimelines(r,c+1, grid, timelines) + getTimelines(r, c-1, grid, timelines);
        } else {
            tls += getTimelines(r+1, c, grid, timelines);
        }
        timelines[r][c] = tls;

        return tls;
    }
}
