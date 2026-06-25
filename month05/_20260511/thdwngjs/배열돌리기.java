package _20260511.thdwngjs;

import java.io.*;
import java.util.*;

class Main {
    // N: 행 개수, M: 열 개수, R: 회전 수
    private static int N, M, R;

    // 방향 벡터: 우(0) → 하(1) → 좌(2) → 상(3) 순서로 시계 반대 방향 껍질을 따라 이동
    private static int[] dx = {0, 1, 0, -1};
    private static int[] dy = {1, 0, -1, 0};
    private static int[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        // 배열 입력
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 현재 껍질의 행/열 길이 (안쪽으로 들어갈수록 2씩 감소)
        int n = N, m = M;

        // 껍질 단위로 회전 수행. 껍질 개수는 min(N, M) / 2
        for(int i = 0; i < Math.min(M, N) / 2; i++){
            // 현재 껍질 둘레 길이 = 2*(n + m) - 4
            rotate(i, 2*n + 2*m - 4);
            n -= 2;
            m -= 2;
        }

        // 결과 출력
        StringBuilder sb = new StringBuilder();
        for(int[] arr: map){
            for(int i: arr)
                sb.append(i + " ");
            sb.append('\n');
        }
        System.out.print(sb);
    }

    // shell: 현재 껍질 인덱스 (좌상단 좌표 기준), len: 해당 껍질의 둘레 길이
    private static void rotate(int shell, int len){
        // R번 회전해도 둘레 길이만큼 돌면 제자리이므로 모듈러 연산으로 실제 회전 수 계산
        int cir = R % len;

        for(int t = 0; t < cir; t++){
            // 좌상단 값을 임시 저장 (한 칸 반시계 회전 시 마지막에 채워질 값)
            int first = map[shell][shell];

    		int x = shell;
			int y = shell;

            // 한 껍질을 따라 우 → 하 → 좌 → 상 방향으로 한 칸씩 당겨오기
    		for(int idx = 0; idx < 4; ) {
    			int nx = x + dx[idx];
    			int ny = y + dy[idx];

                // 현재 껍질 범위 안이면 다음 칸 값을 현재 칸으로 당기고 이동
    			if(nx >= shell && ny >= shell && nx < N-shell && ny < M-shell) {
    				map[x][y] = map[nx][ny];
    				x = nx;
    				y = ny;
    			} else {
                    // 껍질 경계에 닿으면 방향 전환
    			    idx++;
    			}
    		}
            // 마지막에 좌상단 바로 아래 칸에 처음 저장해 둔 값을 채워 한 바퀴 완성
    		map[shell+1][shell] = first;
        }
    }
}