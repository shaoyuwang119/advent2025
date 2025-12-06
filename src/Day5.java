import java.util.*;
import java.io.*;

public class Day5 {
    public static void main(String[] args) throws IOException{
        long startTime = System.nanoTime();

        String file = "src/input.txt";
        solve(file);

        System.err.println("time (ms): " + (System.nanoTime()-startTime)/1_000_000);
    }

    static void solve(String file) throws IOException {
        Scanner scan = new Scanner(new File(file));
        Set<long[]> ranges = new HashSet<>();
        long p1 = 0; long p2 = 0;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.isEmpty()) break;
            String[] rangeStr = line.split("-");
            long[] range = new long[]{Long.parseLong(rangeStr[0]), Long.parseLong(rangeStr[1])};

            Set<long[]> delete = new HashSet<>();

            while (true) { // each round
                boolean roundDel = false;
                for (long[] testRange : ranges) {
                    if (range[1] < testRange[0] || range[0] > testRange[1]) continue;

                    if (range[0] > testRange[0]) {
                        range[0] = testRange[0];
                        roundDel = true;
                    }
                    if (range[1] < testRange[1]) {
                        range[1] = testRange[1];
                        roundDel = true;
                    }
                    delete.add(testRange);
                }

                if (!roundDel) break; // breaks once it stops finding modifications
            }
            ranges.add(range);
            for (long[] del : delete) {
                ranges.remove(del);
            }

        }
        for(long[] range : ranges) {
            p2 += range[1] - range[0] + 1;
        }

        while (scan.hasNextLine()) {
            long ing = Long.parseLong(scan.nextLine());
            for (long[] range : ranges) {
                if (ing >= range[0] && ing <= range[1]) {
                    p1++;
                    break;
                }
            }
        }
        System.out.println(p1 + " " + p2);
    }
}
