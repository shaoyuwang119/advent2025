import java.util.*;
import java.io.*;

public class Day {
    public static void main(String[] args) throws IOException{
        long startTime = System.nanoTime();

        String file = "sample.txt";
        solve(file);

        System.out.println("exec time (ms): " + (System.nanoTime()-startTime)/1_000_000);
    }

    static void solve(String file) throws IOException {
        Scanner scan = new Scanner(new File(file));
    }
}
