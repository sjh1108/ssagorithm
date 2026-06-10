import java.io.*;
import java.util.*;

public class Main_6_오늘의_배달_목록 {

    /**
     * Algoj_6 : 오늘의 배달 목록
     * 자료구조 및 알고리즘 : BFS, 외판원 순회
     */
    static final int INF = 1_000_000;

    static int r, c, sIdx;
    static char[][] map;
    static int[][] dist; // 각 점으로부터 다른 점까지의 거리
    static List<int[]> list = new ArrayList<>(); // 배달지 좌표 리스트
    static Queue<int[]> q = new ArrayDeque<>();
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1}, visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        visited = new int[r];
        for(int i = 0; i < r; i++) {
            String line = br.readLine();
            for(int j = 0; j < c; j++) {
                char ch = line.charAt(j);
                // 시작점 또는 배달지에 대해 인덱스 부여 및 리스트 추가
                // 시작점의 경우 어떤 인덱스를 받았는지 저장
                if(ch == 'S' || ch == '*') {
                    if(ch == 'S') sIdx = list.size();
                    map[i][j] = (char)(list.size() + '0');
                    list.add(new int[]{i, j});
                } else {
                    map[i][j] = ch;
                }
            }
        }

        // 각 배달지(또는 시작점) 기준 BFS로 다른 점까지의 거리 저장
        int areaCnt = list.size();
        dist = new int[areaCnt][areaCnt];
        for(int i=0; i<areaCnt; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
            int[] p = list.get(i);
            bfs(i, p[0], p[1]);
        }

        // 외판원 순회
        // DP 활용. 도착한 배달지 집합 표현은 비트마스킹으로 표현(전부 순회한 경우 111111)
        int pow = 1 << areaCnt;
        int[][] dp = new int[pow][areaCnt];
        for(int i=0; i<pow; i++) Arrays.fill(dp[i], INF);
        // 시작점 -> 첫 배달지까지의 거리 저장
        for(int i=0; i<areaCnt; i++) dp[1 << sIdx | 1 << i][i] = dist[sIdx][i];

        for(int i=0; i<pow; i++) {
            for(int j=0; j<areaCnt; j++) {
                if(dp[i][j] == INF || (i & 1<<j) == 0) continue;

                for(int k=0; k<areaCnt; k++) {
                    if(dist[j][k] == INF || (i & 1<<k) != 0) continue;

                    dp[i | (1<<k)][k] = Math.min(dp[i | (1<<k)][k], dp[i][j] + dist[j][k]);
                }
            }
        }

        // 모든 배달지를 순회한 경우 중 가장 이동 거리가 짧은 경우가 답
        int min = INF;
        for(int i=0; i<areaCnt; i++) {
            if(dp[pow-1][i] < min) min = dp[pow-1][i];
        }

        System.out.println(min == INF ? -1 : min);
    }

    static void bfs(int idx, int sy, int sx) {
        q.clear();
        Arrays.fill(visited, 0);
        q.add(new int[]{sy, sx, 0});
        visited[sy] = 1 << sx;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<4; i++) {
                int y = cur[0] + dy[i];
                int x = cur[1] + dx[i];
                if(y < 0 || x < 0 || y >= r || x >= c || (visited[y] & 1 << x) != 0 || map[y][x] == '#') continue;
                if(map[y][x] >= '0' && map[y][x] <= '9') {
                    int idx2 = map[y][x] - '0';
                    dist[idx][idx2] = cur[2] + 1;
                }

                visited[y] |= 1 << x;
                q.add(new int[]{y, x, cur[2]+1});
            }
        }
    }

}