import java.nio.file.*;
import java.util.*;
import java.io.*;

public class Day6 {
    public static void main(String[] args) throws IOException{
        long startTime = System.nanoTime();

        String file = "src/input.txt";
        solve(file);

        System.err.println("time (ms): " + (System.nanoTime()-startTime)/1_000_000);
    }

    static void solve(String file) throws IOException {
        Scanner scan = new Scanner(new File(file));
        long p1 = 0; long p2 = 0;

        String[][] nums = new String[4][];

        for (int i=0; i<nums.length; i++) {
            String line = scan.nextLine();
            nums[i] = line.trim().split("\\s+");
        }

        Grid grid = new Grid(Path.of(file));

        List<Integer> opIndex = new ArrayList<>();
        for (int i=0; i<grid.cols(); i++) {
            if (grid.get(grid.rows()-1, i) != ' ') {
                opIndex.add(i);
            }
        }
        opIndex.add(grid.cols());

        String[] ops = scan.nextLine().trim().split("\\s+");

        for (int n=0; n < ops.length; n++) {
            long num = Integer.parseInt(nums[0][n]);
            for (int c=1; c<nums.length; c++) {
                int x = Integer.parseInt(nums[c][n]);
                num = (ops[n].equals("+")) ? num + x : num * x;
            }
            p1 += num;
        }

        for (int i=0; i<opIndex.size()-1; i++) {
            char op = grid.get(grid.rows()-1, opIndex.get(i));
            long ans = (op == '+') ? 0 : 1;
            for (int n = opIndex.get(i); n<opIndex.get(i+1)-1; n++) {
                String num = "";
                for (int r=0; r<grid.rows()-1; r++) {
                    num += (grid.get(r, n) == ' ') ? "" : grid.get(r, n)-'0';
                }
                if (op == '+') {
                    ans += Integer.parseInt(num);
                } else {
                    ans *= Integer.parseInt(num);
                }
            }
            p2 += ans;
        }

        System.out.println(p1 + " " + p2);
    }

}
