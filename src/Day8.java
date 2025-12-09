import utils.DSU;

import java.util.*;
import java.io.*;

public class Day8 {
    static class Pair {
        public final int p1;
        public final int p2;
        public int dist;

        public Pair(int p1, int p2, List<int[]> boxes) {
            this.p1 = p1;
            this.p2 = p2;
            for (int i=0; i<3; i++) {
                int j = boxes.get(p1)[i]-boxes.get(p2)[i];
                dist += (double)j*j;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        long startTime = System.nanoTime();

        String file = "src/input.txt";
        solve(file);

        System.err.println("time (ms): " + (System.nanoTime()-startTime)/1_000_000 );
    }

    static void solve(String file) throws IOException {
        long startTime = System.nanoTime();
        Scanner scan = new Scanner(new File(file));
        int p1; int p2;
        List<int[]> boxes = new ArrayList<>();
        List<Pair> pairs = new ArrayList<>();

        while (scan.hasNextLine()) { // O(n^2)
            int[] box = Arrays.stream(scan.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
            boxes.add(box);
            for (int b=0; b<boxes.size()-1; b++) {
                Pair newPair = new Pair(boxes.size()-1, b, boxes);
                pairs.add(newPair);
            }
        }
        pairs.sort(Comparator.comparingInt(a -> a.dist)); // O(n log n)

        DSU graph = new DSU(boxes.size());
        int i=0;
        for (; i<1000; i++) {
            graph.union(pairs.get(i).p1, pairs.get(i).p2);
        }
        List<Integer> sizes = graph.getSizes();
        sizes.sort(Integer::compare);
        p1 = sizes.getLast() * sizes.get(sizes.size()-2) * sizes.get(sizes.size()-3);

        while (graph.getSets() > 1) {
            i++;
            graph.union(pairs.get(i).p1, pairs.get(i).p2);
        }
        p2 = boxes.get(pairs.get(i).p1)[0] * boxes.get(pairs.get(i).p2)[0];
        System.out.println(p1 + " " + p2);

    }

    static int[] parseLine(String s) {
        String[] pos = s.split(",");
        int[] box = new int[3];
        for (int i=0; i<3; i++) {
            box[i] = Integer.parseInt(pos[i]);
        }
        return box;
    }
}