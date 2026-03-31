import java.io.*;
import java.util.*;

public class Main_10216_Count_Circle_Groups {

    /*
     * BOJ_10216 : Count Circle Groups (Gold_4)
     * 자료구조 및 알고리즘 : 유니온 파인드
     *
     * [문제 요약]
     * - 각 적군 진영은 (x, y) 중심과 반지름 r(통신 범위)를 가진 원으로 표현된다.
     * - 두 진영의 통신 원이 서로 닿거나(접함) 겹치면 같은 그룹으로 간주한다.
     * - 모든 진영을 몇 개의 그룹으로 나눌 수 있는지(연결 요소 개수)를 구한다.
     *
     * [핵심 아이디어]
     * - "원들이 닿거나 겹치는 관계"는 그래프의 간선으로 볼 수 있고,
     *   그룹 수는 곧 연결 요소 개수와 같다.
     * - 모든 쌍(i, j)에 대해
     *   중심 거리 d <= r_i + r_j 이면 같은 그룹이므로 union(i, j) 한다.
     * - 최종적으로 남은 분리 집합(대표자) 개수가 정답이다.
     *
     * [구현 메모]
     * - 좌표/반지름을 배열(py, px, pDist)에 저장.
     * - 그룹 수 group을 v로 시작하고, union 성공 시 group-- 한다.
     * - 거리 계산은 피타고라스 정리를 사용:
     *   d = sqrt((x1-x2)^2 + (y1-y2)^2)
     *   d > r1 + r2 이면 서로 닿지 않으므로 스킵.
     * - 입력 크기가 크지 않아 O(N^2) 쌍 비교로 충분하다.
     *
     * [시간 복잡도]
     * - 각 테스트케이스에서 모든 쌍 비교: O(V^2)
     * - 유니온 파인드 연산: 거의 상수
     * - 총: O(V^2)
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int v;
    static int[] head, py, px, pDist;

    static void init() throws Exception {
        v = Integer.parseInt(br.readLine());
        head = new int[v];
        py = new int[v];
        px = new int[v];
        pDist = new int[v];

        for(int i=0; i<v; i++) {
            head[i] = i;

            st = new StringTokenizer(br.readLine());
            py[i] = Integer.parseInt(st.nextToken());
            px[i] = Integer.parseInt(st.nextToken());
            pDist[i] = Integer.parseInt(st.nextToken());
        }
    }

    static int find(int x) {
        if(x == head[x]) return x;
        return head[x] = find(head[x]);
    }

    static boolean union(int a, int b) {
        int ha = find(a);
        int hb = find(b);
        if(ha == hb) return false;

        if(ha < hb) head[hb] = ha;
        else head[ha] = hb;
        return true;
    }

    public static void main(String[] args) throws Exception {
        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            init();

            int group = v;
            for(int i=0; i<v-1; i++) {
                for(int j=i+1; j<v; j++) {
                    int distX = Math.abs(px[i] - px[j]);
                    int distY = Math.abs(py[i] - py[j]);

                    // 두 중심 사이 거리
                    double dist = Math.sqrt((distX * distX) + (distY * distY));

                    // 중심 거리 > 반지름 합이면 서로 닿지 않음
                    if(dist > pDist[i] + pDist[j]) continue;

                    // 닿거나 겹치면 같은 그룹
                    if(union(i, j)) group--;
                }
            }

            sb.append(group).append("\n");
        }

        System.out.println(sb);
    }

}