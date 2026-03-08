package _20260309.thdwngjs.BOJ_2250;

import java.io.*;
import java.util.*;

class Main {
    private static class Node {
        int left;
        int right;

        Node(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    private static int N;
    private static Node[] nodes;
    private static int[] parent;
    private static int[] minCol;
    private static int[] maxCol;
    private static int col = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        nodes = new Node[N + 1];
        parent = new int[N + 1];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            nodes[node] = new Node(left, right);

            if (left != -1) parent[left] = node;
            if (right != -1) parent[right] = node;
        }

        int root = 1;
        for (int i = 1; i <= N; i++) {
            if (parent[i] == 0) {
                root = i;
                break;
            }
        }

        minCol = new int[N + 1];
        maxCol = new int[N + 1];
        Arrays.fill(minCol, Integer.MAX_VALUE);

        inorder(root, 1);

        int bestLevel = 1;
        int bestWidth = 1;

        for (int level = 1; level <= N; level++) {
            if (minCol[level] == Integer.MAX_VALUE) continue;
            int width = maxCol[level] - minCol[level] + 1;
            if (width > bestWidth) {
                bestWidth = width;
                bestLevel = level;
            }
        }

        System.out.println(bestLevel + " " + bestWidth);
    }

    private static void inorder(int node, int depth) {
        if (node == -1) return;

        inorder(nodes[node].left, depth + 1);

        minCol[depth] = Math.min(minCol[depth], col);
        maxCol[depth] = Math.max(maxCol[depth], col);
        col++;

        inorder(nodes[node].right, depth + 1);
    }
}
