import java.io.*;
import java.util.*;

public class Main_16927_배열_돌리기_2 {

    /*
     * BOJ_16927 : 배열 돌리기 2 (Gold_5)
     * 자료구조 및 알고리즘 : 구현, 큐
     *
     * [문제 요약]
     * - R×C 배열을 반시계 방향으로 회전하는 연산을 K번 수행한 결과를 출력한다.
     * - 배열은 바깥 테두리부터 안쪽으로 들어가는 여러 개의 레이어(라인)로 구성된다.
     * - 각 레이어는 독립적으로 회전한다.
     * - 회전 횟수 K는 매우 클 수 있으므로(최대 10^9), 불필요한 반복 회전을 줄여야 한다.
     *
     * [핵심 아이디어]
     * - 레이어 하나를 1차원 배열처럼 펼치면, 회전은 "원형 시프트" 문제로 바뀐다.
     * - 레이어의 길이를 L이라 할 때, K번 회전은 (K % L)번 회전과 동일하다.
     * - 따라서 레이어의 원소를 순서대로 큐에 담고,
     *   (K % 큐크기)만큼만 poll -> add로 회전시키고,
     *   다시 같은 순서로 배열에 채워 넣으면 된다.
     *
     * [구현 메모]
     * - line = Math.min(r, c) / 2 :
     *   - 회전 가능한 레이어의 개수 (행/열 중 작은 쪽 기준으로 2칸씩 안으로 들어감)
     * - 각 레이어 i에 대해, 큐에 담는 순서와 다시 채우는 순서는 동일해야 한다.
     *   (이 코드의 순서)
     *   1) 위쪽 행: (i, i) -> (i, c-1-i-1)까지
     *   2) 오른쪽 열: (i, c-1-i) -> (r-1-i-1, c-1-i)까지
     *   3) 아래쪽 행: (r-1-i, c-1-i) -> (r-1-i, i+1)까지 (역방향)
     *   4) 왼쪽 열: (r-1-i, i) -> (i+1, i)까지 (역방향)
     * - 회전은 for (0 .. rotateCnt % q.size()) 만큼 q.add(q.poll())로 처리한다.
     * - 레이어 처리가 끝나면 q.clear()로 다음 레이어를 위해 비운다.
     *
     * [시간 복잡도]
     * - 각 레이어의 원소를 한 번씩 큐에 넣고 빼므로 전체적으로 O(R*C)
     * - 회전은 K가 아니라 (K % L)만 수행하므로 과도한 반복을 피한다.
     * - 총: O(R*C)
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int rotateCnt = Integer.parseInt(st.nextToken());
        int line = Math.min(r, c) / 2;

        int[][] arr = new int[r][c];
        for(int i=0; i<r; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<c; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for(int i=0; i<line; i++) {
            // 1) 위쪽
            for(int j=i; j<c-1-i; j++) q.add(arr[i][j]);
            // 2) 오른쪽
            for(int j=i; j<r-1-i; j++) q.add(arr[j][c-1-i]);
            // 3) 아래쪽 (역방향)
            for(int j=c-1-i; j>i; j--) q.add(arr[r-1-i][j]);
            // 4) 왼쪽 (역방향)
            for(int j=r-1-i; j>i; j--) q.add(arr[j][i]);

            // 큰 회전 횟수는 레이어 길이로 나눈 나머지만큼만 수행
            for(int j=0; j<rotateCnt%q.size(); j++) q.add(q.poll());

            // 큐에서 꺼낸 순서대로 다시 레이어에 채움(위에서 담은 순서와 동일)
            for(int j=i; j<c-1-i; j++) arr[i][j] = q.poll();
            for(int j=i; j<r-1-i; j++) arr[j][c-1-i] = q.poll();
            for(int j=c-1-i; j>i; j--) arr[r-1-i][j] = q.poll();
            for(int j=r-1-i; j>i; j--) arr[j][i] = q.poll();

            q.clear();
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                sb.append(arr[i][j]).append(" ");
            }

            sb.append("\n");
        }
        System.out.println(sb);
    }

}