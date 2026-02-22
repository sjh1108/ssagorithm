package _20260216.sjh1108.BOJ_1184;

import java.io.*;
import java.util.*;

class Main {
    private static int N;
    private static int[][] arr;
    private static int[][] psum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // Note: Check input constraints. O(N^4) logic requires N to be small (approx N <= 50).
        // If this is for BOJ 2829 (N <= 400), this approach might TLE.
        // If this code is for a different problem, ensure the class name matches the BOJ submission requirement (Main).
        
        String input = br.readLine();
        if (input == null || input.isEmpty()) return;
        N = Integer.parseInt(input.trim());
        
        arr = new int[N + 1][N + 1];
        psum = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                // 2D Prefix Sum Calculation
                psum[i][j] = psum[i - 1][j] + psum[i][j - 1] - psum[i - 1][j - 1] + arr[i][j];
            }
        }

        long ans = 0;

        // Iterate through all possible split points (i, j)
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {

                Map<Integer, Integer> map = new HashMap<>();
                
                // Case 1: Top-Left (1,1 to i,j) vs Bottom-Right (i+1,j+1 to N,N)
                // Collect sums from Top-Left sub-rectangles ending at (i, j)
                for (int r = 1; r <= i; r++) {
                    for (int c = 1; c <= j; c++) {
                        int sum = getSum(r, c, i, j);
                        map.put(sum, map.getOrDefault(sum, 0) + 1);
                    }
                }

                // Check sums from Bottom-Right sub-rectangles starting at (i+1, j+1)
                for (int r = i + 1; r <= N; r++) {
                    for (int c = j + 1; c <= N; c++) {
                        int sum = getSum(i + 1, j + 1, r, c);
                        if (map.containsKey(sum)) {
                            ans += map.get(sum);
                        }
                    }
                }

                map.clear();
                
                // Case 2: Top-Right (1,j+1 to i,N) vs Bottom-Left (i+1,1 to N,j)
                // Collect sums from Top-Right sub-rectangles starting at (r, j+1) ending at (i, c)
                // Note: The loop structure suggests sub-rectangles within the quadrant defined by the split
                for (int r = 1; r <= i; r++) {
                    for (int c = j + 1; c <= N; c++) {
                        int sum = getSum(r, j + 1, i, c);
                        map.put(sum, map.getOrDefault(sum, 0) + 1);
                    }
                }

                // Check sums from Bottom-Left sub-rectangles starting at (i+1, 1) ending at (r, j)
                for (int r = i + 1; r <= N; r++) {
                    for (int c = 1; c <= j; c++) {
                        int sum = getSum(i + 1, c, r, j);
                        if (map.containsKey(sum)) {
                            ans += map.get(sum);
                        }
                    }
                }
            }
        }

        System.out.println(ans);
    }

    // Returns sum of sub-rectangle from (r1, c1) to (r2, c2)
    static int getSum(int r1, int c1, int r2, int c2) {
        return psum[r2][c2] - psum[r1 - 1][c2] - psum[r2][c1 - 1] + psum[r1 - 1][c1 - 1];
    }
}