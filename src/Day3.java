import java.util.*;
import java.io.*;

public class Day3 {
    public static void main(String[] args) throws IOException{
        String file = "src/input.txt";
        solve(file);
        //part2(file);
    }

    static void solve(String file) throws IOException {
        Scanner scan = new Scanner(new File(file));
        long p1 = 0; long p2 = 0;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            int[] nums = new int[line.length()];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = Integer.parseInt(line.charAt(i) - '0' + "");
            }
            p1 += getLargest(nums, 2);
            p2 += getLargest(nums, 12);
        }
        System.out.println(p1 + " " + p2);
    }

    static long getLargest(int[] nums, int bats) {
        String s = "";
        int cur = 0;
        for (int i=bats; i>0; i--) {
            int max = maxIndex(nums, cur, nums.length-i);
            cur = max+1;
            s += nums[max];
        }
        return Long.parseLong(s);
    }

    static int maxIndex(int[] nums, int lower, int upper) {
        int max = lower;
        for (int i=lower; i<=upper; i++) {
            if (nums[i] > nums[max]) {
                max = i;
            }
        }
        return max;
    }

}
