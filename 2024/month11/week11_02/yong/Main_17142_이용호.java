package algostudy.baek.week11_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17142_이용호 {
    static int N, M, K, minTime, emptyCnt;
    static int[][] map;
    static ArrayList<Vi> virus;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static class Vi {
        int x, y, depth;
        Vi(int x, int y, int depth) {
            this.x = x; this.y = y; this.depth = depth;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        virus = new ArrayList<>();
        minTime = Integer.MAX_VALUE;
        emptyCnt = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) virus.add(new Vi(i, j, 0));
                if (map[i][j] == 0) emptyCnt++;
            }
        }

        if (emptyCnt == 0) { // 감염시킬 빈칸이 없을 경우
            System.out.println(0);
            return;
        }

        K = virus.size();
        int[] selected = new int[M];
        comb(0, 0, selected);
        System.out.println(minTime == Integer.MAX_VALUE ? -1 : minTime);
    }

    static void comb(int start, int cnt, int[] selected) {
        if (cnt == M) {
            bfs(selected);
            return;
        }
        for (int i = start; i < K; i++) {
            selected[cnt] = i;
            comb(i + 1, cnt + 1, selected);
        }
    }

    static void bfs(int[] selected) {
        boolean[][] visited = new boolean[N][N];
        Queue<Vi> q = new LinkedList<>();
        int remain = emptyCnt;
        int time = 0;

        for (int idx : selected) {
            Vi v = virus.get(idx);
            q.add(new Vi(v.x, v.y, 0));
            visited[v.x][v.y] = true;
        }

        while (!q.isEmpty()) {
            Vi now = q.poll();
            for (int d = 0; d < 4; d++) {
                int nx = now.x + dx[d];
                int ny = now.y + dy[d];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (map[nx][ny] == 1 || visited[nx][ny]) continue;

                visited[nx][ny] = true;

                if (map[nx][ny] == 0) { // 빈 칸 감염
                    remain--;
                    time = now.depth + 1;
                    if (remain == 0) { // 모든 빈칸 감염 완료 시 바로 종료
                        minTime = Math.min(minTime, time);
                        return;
                    }
                    q.add(new Vi(nx, ny, now.depth + 1));
                } else if (map[nx][ny] == 2) { // 비활성 바이러스 통과 (시간 증가 X)
                    q.add(new Vi(nx, ny, now.depth + 1));
                }
            }
        }
    }
}
