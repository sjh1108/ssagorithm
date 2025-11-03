package week10_02.sjh1108.BOJ_15686;

import java.io.*;
import java.util.*;

/*
 * 비트 마스킹을 활용한 완전탐색
 */
class Main {
    public static void main(String[] args) throws IOException {
        // input
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

        // 연산량을 줄이기 위한 치킨 거리를 계산하는 dist 배열 선언 및 연산
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

        // 비트 마스킹을 활용한 완전탐색
        for(int bit = 1; bit < (1 << chicken); bit++){
            // bit 루프를 돌며 bit의 개수가 M 보다 큰 경우는 연산할 필요가 없음
            int count = 0;
            int b = bit;
            while(b > 0){
                count += b % 2;
                b /= 2;
            }
            if(count > M) continue;

            // 도시 치킨 거리
            int sum = 0;

            // 각 집의 치킨 거리를 계산하여 도시 치킨 거리를 구함
            for(int h = 0; h < house; h++){
                int d = INF;

                for(int c = 0; c < chicken; c++){
                    // 만약 c 치킨집을 활성화 한다면
                    // bit & (1 << c) 연산의 결과는 0이 아니어야 함
                    if((bit & (1 << c)) != 0){ 
                        // 해당 치킨집과의 거리를 통해 최솟값 갱신함
                        d = Math.min(d, dist[h][c]);
                    }
                }

                // 갱신된 최소값은 해당 집의 치킨 거리임
                sum += d;
            }

            min = Math.min(min, sum);
        }

        System.out.println(min);
    }
}