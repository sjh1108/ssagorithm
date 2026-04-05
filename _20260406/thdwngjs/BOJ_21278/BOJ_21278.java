package _20260406.thdwngjs.BOJ_21278;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int INF = 1_000_000;
        int[][] map = new int[N + 1][N + 1];
        for(int i = 1; i <= N; i++){
            Arrays.fill(map[i], INF);

            map[i][i] = 0;
        }

        while(M-- > 0){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            map[a][b] = map[b][a] = 1;
        }

        for(int k = 1; k <= N; k++){
            for(int i = 1; i <= N; i++){
                if(k == i) continue;
                for(int j = 1; j <= N; j++){
                    if(i == j || k == j) continue;

                    map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        int a = -1, b = -1;
        for(int i = 1; i < N; i++){
            for(int j = i + 1; j <= N; j++){
                int sum = 0;
                
                for(int k = 1; k <= N; k++){
                    sum += Math.min(i == k ? 0 : map[i][k], j == k ? 0 : map[j][k]);
                }

                if(sum < min){
                    min = sum;
                    a = i;
                    b = j;
                }
            }
        }

        System.out.println(a + " " + b + " " + min * 2);
    }
}