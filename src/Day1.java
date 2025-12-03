import java.io.*;
import java.util.*;

public class Day1 {
    public static void main(String[] args) throws IOException {
        String file = "src/input.txt";
        part1(file);
        part2(file);
    }

    static void part1(String file) throws IOException {
        Scanner scan = new Scanner(new File(file));
        List<Integer> turns = new ArrayList<>();
        String line;

        while (scan.hasNextLine()) {
            String l = scan.nextLine();
            if (l.isEmpty()) break;
            char dir = l.charAt(0);
            int num = Integer.parseInt(l.substring(1));

            if (dir == 'L') {
                num*=-1;
            }
            turns.add(num);
        }

        int dial = 50;
        int ans = 0;
        for (int i : turns) {
            dial += i;
            dial %= 100;
            if (dial == 0) {
                ans++;
            }
        }
        System.out.println(ans);
    }

    static void part2(String file) throws IOException  {
        Scanner scan = new Scanner(new File(file));
        List<Integer> turns = new ArrayList<>();
        String line;

        while (scan.hasNextLine()) {
            String l = scan.nextLine();
            if (l.isEmpty()) break;
            char dir = l.charAt(0);
            int num = Integer.parseInt(l.substring(1));

            if (dir == 'L') {
                num*=-1;
            }
            turns.add(num);
        }

        int dial = 50;
        int last = dial;
        int ans = 0;
        for (int i : turns) {
            int fulls = Math.abs(i/100);
            int rem = i%100;

            ans += fulls;
            //System.err.println("fulls " + fulls);
            dial += rem;
            if (dial <= 0 && last > 0 || dial >= 100 && last < 100) {
                ans++;
            }
            dial = Math.floorMod(dial, 100);
            last = dial;
        }
        System.out.println(ans);
    }
}
