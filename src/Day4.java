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
        p1 = removeRolls(grid, false);
        p2 = removeRolls(grid, true);

        System.out.println(p1 + " " + p2);
    }

    static int removeRolls(Grid grid, boolean p2) {
        grid = grid.copy();
        Grid grid2 = grid.copy();
        int totalRolls = 0;
        int newRolls = -1;

        do  {
            newRolls = 0;
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
                        newRolls++;
                    }
                }
            }
            totalRolls += newRolls;
            grid = grid2.copy();
        } while (newRolls != 0 && p2);

        return totalRolls;
    }
}
