package week10_02.sjh1108.BOJ_20951;

import java.io.*;
import java.util.*;

class Main {
    private final static int MOD = 1_000_000_007;
    private static long[][] map;

    private static List<List<Integer>> list = new ArrayList<>();

    // dfs
    private static long path(int idx, int len){
        // 거리가 7이면 1
        if(len == 7) return 1;
        
        // DP 적용 부분
        // 해당 depth를 방문하였다면 추가 연산하지 않음
        if(map[idx][len] != -1) return map[idx][len];
        
        long res = 0;

        // 인접한 위치로 이동한 경우의 수를 result 값에 더함
        for(int nxt: list.get(idx)){
            res = (res + path(nxt, len+1)) % MOD;
        }

        return map[idx][len] = res;
    }

    public static void main(String[] args) throws IOException {
        // 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        for(int i = 0; i <= N; i++){
            list.add(new ArrayList<>());
        }
        
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            list.get(u).add(v);
            list.get(v).add(u);
        }

        map = new long[100_000 + 1][7 + 1];
        for(long[] arr: map){
            Arrays.fill(arr, -1);
        }

        long answer = 0;

        for(int i = 1; i <= N; i++){
            // i 번째 정점에서 시작한 경로의 개수를 answer에 더함
            answer += path(i, 0) % MOD;
        }

        System.out.println(answer % MOD);
    }
}