import java.io.*;
import java.util.*;

public class Main_2585_경비행기 {

    /*
     * BOJ_2585 : 경비행기 (Gold_2)
     * 자료구조 및 알고리즘 : BFS, 매개 변수 탐색
     *
     * [문제 요약]
     * - 출발지 (0,0)에서 도착지 (10000,10000)까지 이동한다.
     * - 중간에 공항(좌표)들이 주어지며, 최대 K번까지 중간 급유(= 중간 공항을 경유)할 수 있다.
     * - 한 번 비행할 때의 최대 거리 제한(연료량)을 정했을 때, K번 이하 경유로 도착 가능 여부가 결정된다.
     * - 도착 가능하게 만드는 최소 연료량을 구한다.
     *
     * [핵심 아이디어]
     * - 연료량 fuel이 커질수록 "도달 가능"이 단조 증가하므로 이분 탐색(매개변수 탐색)이 가능하다.
     * - fuel이 주어졌을 때:
     *   - 각 공항(출발/중간/도착)을 정점으로 보고,
     *   - 두 점 사이 거리가 (fuel*10) 이하이면 간선이 있다고 보면,
     *   - K번 이하 중간급유는 "최대 (K+1)번 비행(간선)"으로 도착 가능한지 BFS로 판정할 수 있다.
     *
     * [구현 메모]
     * - total = n + 2 : (0,0) + n개의 공항 + (10000,10000)
     * - 거리 비교는 sqrt를 피하려고 거리의 제곱(dist2)을 미리 전처리한다.
     *   dist2[i*total + j] = (xi-xj)^2 + (yi-yj)^2
     * - bfs(fuel):
     *   - limit2 = (fuel*10)^2 이내인 간선만 따라 이동 가능
     *   - "레벨 BFS"로 (비행 횟수 = 간선 수)를 세고, i=0..K까지 레벨을 확장한다.
     *     -> 중간 급유 K번 이하면 간선은 최대 K+1개이므로 레벨은 0..K까지 확장하면 충분
     * - seen[]은 boolean 대신 토큰(token) 방식으로 초기화 비용을 줄인다.
     *
     * [시간 복잡도]
     * - 거리 전처리: O(total^2)
     * - 판정 BFS 1회: O(total^2) (현재 구현은 모든 next를 스캔)
     * - 이분 탐색: O(log 1415)
     * - 총: O(total^2 * log 1415)
     */

    static int n, k, total;
    static int[] x, y;
    static int[] dist2;    // flattened matrix: dist2[i * total + j]
    static int[] queue;
    static int[] seen;
    static int visitCnt = 1;

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        total = n + 2; // (0, 0) + n + (10000, 10000)

        x = new int[total];
        y = new int[total];

        x[0] = 0;
        y[0] = 0;

        for(int i=1; i<=n; i++) {
            st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }

        x[total-1] = 10000;
        y[total-1] = 10000;

        // 모든 쌍 거리^2 전처리 (sqrt 없이 비교)
        dist2 = new int[total*total];
        for(int i=0; i<total; i++) {
            int rowBase = i * total;
            for(int j=i+1; j<total; j++) {
                int dx = x[i] - x[j];
                int dy = y[i] - y[j];
                int d2 = dx*dx + dy*dy;
                dist2[rowBase+j] = d2;
                dist2[j*total+i] = d2;
            }
        }

        queue = new int[total];
        seen = new int[total];
    }

    // fuel(연료량)로 K번 이하 경유가 가능한지 판정
    static boolean bfs(int fuel) {
        int range = fuel * 10;
        int limit2 = range * range;

        // seen 토큰 방식: visited 초기화 비용을 줄임
        int token = visitCnt++;
        if(visitCnt == Integer.MAX_VALUE) {
            Arrays.fill(seen, 0);
            visitCnt = 1;
        }

        int head = 0, tail = 0;
        queue[tail++] = 0;      // 시작 정점(0,0)
        seen[0] = token;

        // k번 이하 중간급유 => 최대 (k+1)번 비행(간선)
        // 레벨 i는 "i번째 비행을 수행하기 직전의 frontier"를 의미하도록 확장
        for(int i=0; i<=k && head<tail; i++) {
            int levelEnd = tail;

            while (head < levelEnd) {
                int cur = queue[head++];
                int base = cur * total;

                // 현재 구현은 모든 next를 훑으며 거리 제한으로 간선 여부를 판정
                for (int next = 1; next < total; next++) {
                    if (seen[next] == token) continue;
                    if (dist2[base + next] > limit2) continue;

                    if (next == total - 1) return true;

                    seen[next] = token;
                    queue[tail++] = next;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        init();

        int left = 0;
        int right = 1415; // ceil(sqrt(200000000) / 10)

        // 이분 탐색으로 최소 fuel 찾기
        while(left < right) {
            int mid = (left + right) >> 1;
            if(bfs(mid)) right = mid;
            else left = mid + 1;
        }

        System.out.println(left);
    }
}