package week03.sjh1108.BOJ_12100;

import java.io.*;
import java.util.*;

public class Main_12100_송주헌 {
    private static int N;

    private static int max = 0;

    // 맵의 최대값을 저장하기 위한 메소드
    private static void getMax(int[][] map) {
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                max = Math.max(max, map[i][j]);
            }
        }
    }

    // 맵을 90도 회전시키는 메소드
    private static int[][] rotate(int[][] map) {
        int[][] rotated = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                rotated[j][N - 1 - i] = map[i][j];
            }
        }

        return rotated;
    }

    // 맵을 위로 눌러서 합치는 메소드
    private static void pressMap(int[][] map){
        for(int i = 0; i < N; i++){
            int[] t = new int[N];

            int idx = 0;
            for(int j = 0; j < N; j++){
                if(map[j][i] == 0) continue;

                if(t[idx] == 0){
                    t[idx] = map[j][i];
                }
                else if(t[idx] == map[j][i]){
                    t[idx] *= 2;
                    idx++;
                }else{
                    idx++;
                    t[idx] = map[j][i];
                }
            }

            for(int j = 0; j < N; j++){
                map[j][i] = t[j];
            }
        }
    }

    // DFS를 통해 맵을 5번까지 눌러서 최대값을 구하는 메소드
    private static void dfs(int[][] map, int depth){
        if(depth == 5) {
            getMax(map);
            return;
        }
        
        // 현재 맵을 위로 눌러서 합치고 최대값을 구함
        for (int i = 0; i < 4; i++) {
            // 현재 맵을 회전시켜서 다음 방향으로 진행
            int[][] newMap = rotate(map);

            // 맵을 위로 눌러서 합치고 최대값을 구함
            pressMap(newMap);
            dfs(newMap, depth + 1);

            // 맵을 다시 회전하여 다음 방향으로 진행
            map = rotate(map);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력
        N = Integer.parseInt(br.readLine());
        
        int[][] map = new int[N][N];
        
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // DFS를 통해 최대값을 구함
        dfs(map, 0);

        System.out.println(max);
    }
}