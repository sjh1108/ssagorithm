import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_2643_색종이_올려_놓기 {

    /*
     * BOJ_2643 : 색종이 올려 놓기 (Gold_4)
     * 자료구조 및 알고리즘 : DP, 정렬, LIS
     *
     * [문제 요약]
     * - N장의 색종이(가로, 세로)가 주어진다.
     * - 색종이를 90도 회전할 수 있다.
     * - 아래에 있는 색종이가 위의 색종이보다 가로, 세로 모두 크거나 같을 때만 쌓을 수 있다.
     * - 쌓을 수 있는 색종이의 최대 개수를 구하라.
     *
     * [핵심 아이디어]
     * - 색종이를 회전시켜도 되므로, 비교 편의를 위해 항상 (짧은 변, 긴 변) 순서로 정규화하여 저장한다.
     * - 한 쪽 변을 기준으로 내림차순 정렬하면, LIS 알고리즘과 유사한 방식으로 DP를 수행할 수 있다.
     * - i번째 색종이를 가장 위에 놓을 때, 0~i-1번째 색종이 중 i번째를 포함할 수 있는 것의 최대 높이 + 1을 구한다.
     *
     * [구현 메모]
     * - Paper 클래스 : compareTo를 통해 정렬 기준 정의 (세로 내림차순, 같으면 가로 내림차순).
     * - 입력 시 y(짧은 변), x(긴 변)으로 스왑 처리.
     * - DP 배열 : dp[i]는 i번째 색종이를 맨 위에 올렸을 때의 최대 높이.
     *
     * [시간 복잡도]
     * - 정렬 : O(N log N)
     * - DP : 이중 반복문 O(N^2)
     * - N <= 100 이므로 O(N^2)도 충분히 빠르다.
     */

    static class Paper implements Comparable<Paper> {
        int y, x;

        public Paper(int y, int x) {
            this.y = y;
            this.x = x;
        }

        // 큰 색종이가 앞에 오도록 정렬 (내림차순)
        @Override
        public int compareTo(Paper o) {
            if (this.y == o.y) {
                return o.x - this.x;
            }
            return o.y - this.y;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        List<Paper> papers = new ArrayList<>();

        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            // 회전 가능하므로, 짧은 변을 y, 긴 변을 x로 통일(정규화)
            if(y > x) {
                int temp = y; y = x; x = temp;
            }

            papers.add(new Paper(y, x));
        }

        // 크기 순(내림차순) 정렬
        Collections.sort(papers);

        int[] dp = new int[n];
        int max = 0;

        for(int i=0; i<n; i++) {
            dp[i] = 1; // 자기 자신 1장
            Paper cur = papers.get(i); // 현재 가장 위에 놓으려는 색종이

            // 현재 색종이보다 크기가 큰(앞에 있는) 색종이들을 탐색
            for (int j = 0; j < i; j++) {
                Paper bottom = papers.get(j); // 바닥에 깔릴 후보 색종이

                // 현재 색종이가 바닥 색종이 범위 안에 들어오는지 확인
                if (cur.y <= bottom.y && cur.x <= bottom.x) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }

        System.out.println(max);
    }

}