import utils.DyCon;

import java.util.*;
import java.io.*;

public class Day8 {
    static class Pair {
        public final int p1;
        public final int p2;
        public final double dist;
        String string;

        public Pair(int p1, int p2, List<int[]> boxes) {
            this.p1 = p1;
            this.p2 = p2;
            double pyth = 0;
            for (int i=0; i<3; i++) {
                pyth += Math.pow(boxes.get(p1)[i]-boxes.get(p2)[i],2);
            }
            dist = Math.sqrt(pyth);

            string = Arrays.toString(boxes.get(p1)) + p1 + " " + Arrays.toString(boxes.get(p2)) + p2 + " " + dist;
        }

        public boolean equals(Object o) {
            Pair pair = (Pair) o;
            return this.p1 == pair.p1 && this.p2 == pair.p2 ||
                    this.p1 == pair.p2 && this.p2 == pair.p1;
        }

        public String toString() {
            return string;
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
        long p1 = 1; long p2 = 0;
        List<int[]> boxes = new ArrayList<>();
        HashSet<Pair> pairSet = new HashSet<>();

        while (scan.hasNextLine()) { // O(n^2)
            int[] box = parseLine(scan.nextLine());
            boxes.add(box);
            for (int b=0; b<boxes.size()-1; b++) {
                Pair newPair = new Pair(boxes.size()-1, b, boxes);
                pairSet.add(newPair);
            }
        }

        List<Pair> pairs = new ArrayList<>(pairSet);
        pairs.sort((a, b) -> (int)(a.dist - b.dist)); // O(n log n)
        DyCon graph = new DyCon(boxes.size());
        int i=0;
        for (; i<1000; i++) {
            graph.union(pairs.get(i).p1, pairs.get(i).p2);
        }
        List<Integer> sizes = graph.getSizes();
        sizes.sort(Integer::compare);
        p1 *= (long) sizes.getLast() * sizes.get(sizes.size()-2) * sizes.get(sizes.size()-3);
        while (graph.getSizes().size() > 1) {
            graph.union(pairs.get(i).p1, pairs.get(i).p2);
            p2 = (long) boxes.get(pairs.get(i).p1)[0] * boxes.get(pairs.get(i).p2)[0];
            i++;
        }
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