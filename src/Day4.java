import java.nio.file.Path;
import java.util.*;
import java.io.*;

public class Day4 {
    public static void main(String[] args) throws IOException{
        long startTime = System.nanoTime();

        String file = "src/input.txt";
        solve(file);

        System.err.println((System.nanoTime()-startTime)/1_000_000 + " ms");
    }

    static void solve(String file) throws IOException {
        Grid grid = new Grid(Path.of(file));
        //grid.print();

        int p1 = 0; int p2=0;
        p1 = removeRolls(grid, 1);
        p2 = removeRolls(grid, 800);

        System.out.println(p1 + " " + p2);
    }

    static int removeRolls(Grid grid, long times) {
        grid = grid.copy();
        Grid grid2 = grid.copy();
        int totalRolls = 0;

        for (int t = 0; t < times; t++) {
            for (int r = 0; r < grid.rows(); r++) {
                for (int c = 0; c < grid.cols(); c++) {
                    int rolls = 0;
                    Coords cur = new Coords(r, c);
                    if (grid.get(cur) == '.') continue;
                    for (Coords dir : Grid.ALLDIRS) {
                        try {
                            char tile = grid.get(cur.add(dir));
                            if (tile == '@') rolls++;
                        } catch (Exception _) {

                        }
                    }
                    if (rolls < 4) {
                        grid2.set(cur, '.');
                        totalRolls++;
                    }
                }
            }
            grid = grid2.copy();
        }

        return totalRolls;
    }
}
