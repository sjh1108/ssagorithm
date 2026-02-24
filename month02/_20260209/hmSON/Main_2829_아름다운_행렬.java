import java.io.*;
import java.util.*;

public class Main_2829_아름다운_행렬 {

    /*
     * BOJ_2829 : 아름다운 행렬 (Gold_3)
     * 자료구조 및 알고리즘 : 누적합(대각선 prefix sum), 브루트포스
     *
     * [문제 요약]
     * - N×N 행렬이 주어진다.
     * - 임의의 정사각형 부분행렬(크기 k×k)을 선택했을 때,
     *   (우하향 대각선 합) - (좌하향 대각선 합) 값을 계산한다.
     * - 가능한 모든 정사각형 부분행렬 중 위 값의 최댓값을 출력한다.
     *
     * [핵심 아이디어]
     * - 정사각형을 하나 고를 때마다 두 대각선의 합을 매번 직접 더하면 O(k)이 들어 비효율적이다.
     * - 따라서 대각선 방향 누적합을 미리 만들어 두면, 임의 구간 대각선 합을 O(1)에 뽑아낼 수 있다.
     *   1) rightDown[i][j] = (1,1)에서 (i,j)까지 "우하향(↘)" 대각선 누적합
     *   2) leftDown[i][j]  = (1,n)에서 (i,j)까지 "좌하향(↙)" 대각선 누적합
     * - 이후 (i,j)를 좌상단으로 하는 k×k 정사각형에 대해
     *   ↘ 대각선 합과 ↙ 대각선 합을 누적합 차로 계산하여 최댓값을 갱신한다.
     *
     * [구현 메모]
     * - 인덱스를 1부터 사용해 경계 처리를 단순화했다.
     * - leftDown은 j+1을 참조하므로 배열을 [n+2][n+2]로 잡아 범위 밖 접근을 방지했다.
     * - ↘ 대각선 합(정사각형의 주대각선):
     *     sum1 = rightDown[i+k-1][j+k-1] - rightDown[i-1][j-1]
     * - ↙ 대각선 합(정사각형의 부대각선):
     *     sum2 = leftDown[i+k-1][j] - leftDown[i-1][j+k]
     *
     * [시간 복잡도]
     * - 대각선 누적합 구성: O(N^2)
     * - 모든 (k, i, j) 정사각형 탐색: Σ_k (N-k+1)^2 = O(N^3)
     * - 총: O(N^3)
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        int[][] matrix = new int[n+1][n+1];
        for(int i=1; i<=n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=1; j<=n; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // rightDown: ↘ 방향 대각선 누적합, leftDown: ↙ 방향 대각선 누적합
        // (i-1, j-1), (i-1, j+1) 참조가 있으므로 경계 여유를 위해 n+2 사용
        int[][] rightDown = new int[n+2][n+2];
        int[][] leftDown = new int[n+2][n+2];

        // 대각선 누적합 전처리
        for(int i=1; i<=n; i++) {
            for(int j=1; j<=n; j++) {
                rightDown[i][j] = rightDown[i-1][j-1] + matrix[i][j]; // ↘
                leftDown[i][j] = leftDown[i-1][j+1] + matrix[i][j];   // ↙
            }
        }

        int max = Integer.MIN_VALUE;
        // k: 정사각형 크기, (i,j): 좌상단
        for(int k=1; k<=n; k++) {
            for(int i=1; i<=n-k+1; i++) {
                for(int j=1; j<=n-k+1; j++) {
                    // k×k 정사각형의 주대각선(↘) 합: (i,j) ~ (i+k-1, j+k-1)
                    int sum1 = rightDown[i+k-1][j+k-1] - rightDown[i-1][j-1];

                    // k×k 정사각형의 부대각선(↙) 합: (i, j+k-1) ~ (i+k-1, j)
                    // leftDown은 (i-1, j+k)까지 누적이므로 차로 구간합을 만든다.
                    int sum2 = leftDown[i+k-1][j] - leftDown[i-1][j+k];

                    // 문제에서 요구하는 값: (↘ 합) - (↙ 합)의 최댓값
                    max = Math.max(max, sum1 - sum2);
                }
            }
        }

        System.out.println(max);
    }
}
