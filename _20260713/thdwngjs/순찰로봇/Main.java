// [#11] 순찰 로봇  —  SILVER
// 접근: 단일 로봇 격자 시뮬레이션. 매 초, 앞 칸이 격자 안이고 벽이 아니면 전진,
//       아니면 제자리에서 시계방향 90도 회전. 밟은 칸을 boolean 격자로 표시해
//       서로 다른 방문 칸의 개수를 센다.
// 복잡도: O(K + R*C)
import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        boolean[][] wall = new boolean[R][C];
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) if (s.charAt(j) == '#') wall[i][j] = true;
        }

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken()) - 1;
        int c = Integer.parseInt(st.nextToken()) - 1;
        int[] di = new int[128];
        di['U'] = 0; di['R'] = 1; di['D'] = 2; di['L'] = 3;   // clockwise
        int d = di[st.nextToken().charAt(0)];
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        boolean[][] vis = new boolean[R][C];
        vis[r][c] = true;
        long cnt = 1;
        for (int t = 1; t <= K; t++) {
            int fr = r + dr[d], fc = c + dc[d];
            if (fr < 0 || fr >= R || fc < 0 || fc >= C || wall[fr][fc]) {
                d = (d + 1) & 3;                 // blocked: rotate CW, stay
            } else {
                r = fr; c = fc;                  // advance
                if (!vis[r][c]) { vis[r][c] = true; cnt++; }
            }
        }
        System.out.println(cnt);
    }
} 