import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 정렬, 스위핑, 기하학
     * 직전 문제의 일반화로, 모든 드론 중 제일 가까운 드론 쌍의 거리 제곱을 출력해야 한다
     * 모든 드론이 비교 대상이 되므로, 직전 문제의 풀이에서 초기값 및 처리 순서의 변경만으로도 문제를 해결할 수 있다
     *
     * 스위핑을 이용해 드론 간 거리 비교 횟수 최소화
     * 스위핑 구현을 위해 드론 좌표 수열의 x 좌표 오름차순 정렬과 y 좌표 오름차순으로 정렬되는 활성 드론 트리셋이 요구됨
     * 1. 각 드론을 x 좌표 오름차순으로 1회씩 순회
     * 2. 트리셋 내 활성 드론의 x 좌표와 현재 처리하는 드론 i의 x 좌표의 거리가 maxDist보다 크면 그 활성 드론을 트리셋에서 제거
     * 3. 현재 처리하는 드론 기준, 남아있는 활성 드론 중 y 좌표간 거리도 maxDist 이하인 드론들과 실제 거리 비교
     * 4. 현재 루프에서 처리한 드론을 활성 드론 트리셋에 추가
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); // 드론 수

        int[][] points = new int[n][2]; // { x, y }
        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            points[i][0] = Integer.parseInt(st.nextToken());
            points[i][1] = Integer.parseInt(st.nextToken());
        }
        // x 좌표 오름차순 정렬
        Arrays.sort(points, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));

        // 활성 드론 트리셋은 y 좌표 오름차순 정렬
        TreeSet<int[]> activeSet = new TreeSet<>(
                (a, b) -> a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0])
        );

        long firstX = points[1][0] - points[0][0];
        long firstY = points[1][1] - points[0][1];
        long best = firstX * firstX + firstY * firstY; // 0번 드론과 1번 드론의 거리 제곱으로 초기화
        int left = 0; // 왼쪽 포인터. 가장 왼쪽에 있는 드론의 인덱스

        // 1. 각 드론을 x 좌표 오름차순으로 1회씩 순회
        for(int i=0; i<n; i++) {
            // 2. 현재 처리하려는 드론과의 거리가 먼 활성 드론 비활성화
            while(left < i) {
                long dx = points[i][0] - points[left][0];
                if(dx * dx < best) break;

                activeSet.remove(points[left]);
                left++;
            }

            // 3. 현재 처리하는 드론과 활성 드론의 실제 거리 비교 및 드론 쌍 최단 거리 갱신
            long dist = (long)Math.sqrt(best) + 2;
            int[] low = new int[]{Integer.MIN_VALUE, (int)(points[i][1] - dist)};
            int[] high = new int[]{Integer.MAX_VALUE, (int)(points[i][1] + dist)};

            for(int[] p : activeSet.subSet(low, true, high, true)) {
                long dx = points[i][0] - p[0];
                long dy = points[i][1] - p[1];
                long d = dx * dx + dy * dy;
                if(d < best) best = d;
            }

            // 4. 활성 드론 목록에 현재 루프에서 처리한 드론 추가
            activeSet.add(points[i]);
        }

        System.out.println(best);
    }

}