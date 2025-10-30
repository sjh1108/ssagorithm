package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_7576_이용호 {
	/*
	 * 토마토 거꾸로해도 토마토
	 * 익은 토마토 영향을받아 인접 토마토가 익게됨
	 * 모두 익지않으면 -1
	 * bfs로 토마토 익히기
	 */
	public static int[][] tomatoes;
	public static int N, M;
	public static int[] dx = {-1,1,0,0};
	public static int[] dy = {0,0,-1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        tomatoes = new int[N][M];

        Queue<int[]> q = new LinkedList<>();

        // 초기 익은 토마토 위치 저장
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                tomatoes[i][j] = Integer.parseInt(st.nextToken());
                if (tomatoes[i][j] == 1) {
                    q.add(new int[]{i, j});
                }
            }
        }

        int days = bfs(q);

        // 익지 않은 토마토가 남아있는지 확인
        if (!isAllRipe()) System.out.println(-1);
        else System.out.println(days);
    }

    // 날짜 계산
    static int bfs(Queue<int[]> q) {
        int days = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            boolean spread = false; // 하루 동안 익은 토마토가 있었는지

            for (int s = 0; s < size; s++) {//오늘 큐에 있던 토마토 처리
                int[] now = q.poll();
                int x = now[0];
                int y = now[1];

                for (int d = 0; d < 4; d++) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                    if (tomatoes[nx][ny] == 0) {
                        tomatoes[nx][ny] = 1; // 익히기
                        q.add(new int[]{nx, ny});
                        spread = true;
                    }
                }
            }

            if (spread) days++; // 하루 지남
        }

        return days;
    }

    // 모든 토마토가 익었는지 검사
    static boolean isAllRipe() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (tomatoes[i][j] == 0)
                    return false;
            }
        }
        return true;
    }
}
