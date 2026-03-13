package _20260309.thdwngjs.BOJ_15686;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][N];

        // 집과 치킨집 좌표만 분리해서 저장한다.
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

        // 집 i, 치킨집 j 사이 맨해튼 거리를 미리 계산해 둔다.
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

        // 비트마스킹으로 치킨집 부분집합을 순회하며 최소 도시 치킨 거리를 계산한다.
        for(int bit = 1; bit < (1 << chicken); bit++){
            int count = 0;
            int b = bit;
            // 선택한 치킨집 개수 계산
            while(b > 0){
                count += b % 2;
                b /= 2;
            }
            // M개를 넘으면 조건 위반
            if(count > M) continue;

            int sum = 0;

            // 각 집에서 선택된 치킨집들 중 최단 거리를 더한다.
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
