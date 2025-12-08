package utils;

import java.util.*;

public class DyCon {
    private int[] id; // id[i] == parent of i
    private int[] size; // if i == a root: size[i] == size of tree with root at i
    private List<Integer> sizes;

    public DyCon(int n) {
        id = new int[n];
        size = new int[n];

        for (int i=0; i<n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int i) {
        if (i != id[i]) {
            id[i] = root(id[i]);
        }
        return id[i];
    }

    public void union(int i, int j) {
        int ri = root(i);
        int rj = root(j);
        if (ri == rj) return;
        if (size[ri] > size[rj]) {
            id[rj] = ri;
            size[ri] += size[rj];
        } else {
            id[ri] = rj;
            size[rj] += size[ri];
        }
    }

    public boolean isConnected(int i, int j) {
        return root(i) == root(j);
    }

    public int getSize(int i) {
        return size[root(i)];
    }

    public List<Integer> getSizes() {
        Set<Integer> checked = new HashSet<>();
        List<Integer> sizes = new ArrayList<>();
        for (int i=0; i<id.length; i++) {
            if (checked.add(root(i))) {
                sizes.add(size[root(i)]);
            }
        }
        return sizes;
    }
}
