package week03.sjh1108.BOJ_9663;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_9663_송주헌 {
    private static int N;

    private static int cnt;
    private static boolean[] sero;
    private static boolean[] lUp;
    private static boolean[] rUp;
    private static void dfs(int depth){
        // N-Queen 문제의 종료 조건
        // depth가 N에 도달하면 모든 퀸을 배치한 상태이므로
        // cnt를 증가시키고 리턴
        if(depth == N){
            ++cnt;
            return;
        }

        // 현재 depth에 퀸을 배치할 수 있는 모든 행을 탐색
        // sero[i]는 i번째 행에 퀸이 배치되었는지 여부
        // rUp[depth + i]는 우상향 대각선에 퀸이 배치되었는지 여부
        // lUp[depth - i + (N-1)]는 좌상향 대각선에 퀸이 배치되었는지 여부
        // 이 세 가지 조건을 모두 만족하지 않는 행에 퀸을 배치
        for(int i = 0; i < N; i++){
            if(sero[i] || rUp[depth+i] | lUp[depth - i + (N-1)]){
                continue;
            }

            sero[i] = true;
            rUp[depth + i] = true;
            lUp[depth - i + (N-1)] = true;

            dfs(depth + 1);

            sero[i] = false;
            rUp[depth + i] = false;
            lUp[depth - i + (N-1)] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        // 초기화
        sero = new boolean[N];
        lUp = new boolean[(N << 1) - 1];
        rUp = new boolean[(N << 1) - 1];

        // DFS를 통해 N-Queen 문제 해결
        dfs(0);

        System.out.println(cnt);
    }
}