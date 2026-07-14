package _20260706.taeyum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 순찰_로봇 {
    // U R D L -> U -1,0 (상) / R 0,1 (우) / D 1,0 (하) / L 0,-1 (좌)
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        // K초 동안 움직임
        int K = Integer.parseInt(st.nextToken());

        // R,C ,map
        char[][] map = new char[R][C];

        for (int i = 0; i < R; i++) {
            map[i] = br.readLine().toCharArray();
        }

        st = new StringTokenizer(br.readLine());
        int input_r = Integer.parseInt(st.nextToken()) - 1;
        int input_c = Integer.parseInt(st.nextToken()) - 1;
        String str = st.nextToken();

        int dir = 0;

        switch (str) {
            case "U" :
                dir = 0;
                break;
            case "R" :
                dir = 1;
                break;
            case "D" :
                dir = 2;
                break;
            case "L" :
                dir = 3;
                break;
        }

        boolean[][] visited = new boolean[R][C];
        visited[input_r][input_c] = true;
        int count = 1;

        for (int i = 0; i < K; i++) {
            int nr = input_r + dx[dir];
            int nc = input_c + dy[dir];

            // 벽이거나 칸 넘지 않으면
            if(nr >= 0 && nr < R && nc >= 0 && nc < C && map[nr][nc] != '#') {
                input_r = nr;
                input_c = nc;
                // 방문 하지 않았다면
                if(!visited[input_r][input_c]) {
                    visited[input_r][input_c] = true;
                    count++;
                }
            } else {
                // 회전 회오리
                dir = (dir + 1) % 4;
            }
        }
        System.out.println(count);
    }
}
