package utils;

import java.util.*;

public class DSU {
    private int[] id; // id[i] == parent of i
    private int[] size; // if i == a root: size[i] == size of tree with root at i
    private int sets;
    private List<Integer> sizes;

    public DSU(int n) {
        id = new int[n];
        size = new int[n];
        sets = n;

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
        i = root(i);
        j = root(j);
        if (i == j) return;
        sets--;
        if (size[i] > size[j]) {
            id[j] = i;
            size[i] += size[j];
        } else {
            id[i] = j;
            size[j] += size[i];
        }
    }

    public boolean isConnected(int i, int j) {
        return root(i) == root(j);
    }

    public int getSize(int i) {
        return size[root(i)];
    }

    public int getSets() {return sets; }

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
