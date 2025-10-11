package week10_02.sjh1108.BOJ_15686;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][N];

        List<int[]> houseList = new ArrayList<>();
        List<int[]> chickenList = new ArrayList<>();
        
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                int input = Integer.parseInt(st.nextToken());
                map[i][j] = input;

                if(input == 1){
                    houseList.add(new int[]{i, j});
                } else if(input == 2){
                    chickenList.add(new int[]{i, j});
                }
            }
        }

        int house = houseList.size();
        int chicken = chickenList.size();

        int[][] dist = new int[house][chicken];
        for(int i = 0; i < house; i++){
            int[] h = houseList.get(i);
            for(int j = 0; j < chicken; j++){
                int[] c = chickenList.get(j);

                int d = Math.abs(h[0] - c[0]) + Math.abs(h[1] - c[1]);

                dist[i][j] = d;
            }
        }

        int INF = 1_000_000_000;
        int min = INF;

        for(int bit = 1; bit < (1 << chicken); bit++){
            int count = 0;
            int b = bit;
            while(b > 0){
                count += b % 2;
                b /= 2;
            }
            if(count > M) continue;

            int sum = 0;

            for(int h = 0; h < house; h++){
                int d = INF;

                for(int c = 0; c < chicken; c++){
                    if((bit & (1 << c)) != 0){ 
                        d = Math.min(d, dist[h][c]);
                    }
                }

                sum += d;
            }

            min = Math.min(min, sum);
        }

        System.out.println(min);
    }
}