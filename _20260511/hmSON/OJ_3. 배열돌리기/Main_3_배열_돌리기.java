import java.io.*;
import java.util.*;

public class Main {

    // 자료구조 및 알고리즘 : 시뮬레이션, 큐
    // 2차원 배열의 외곽에 있는 값들부터 하나의 라인으로 인식하고, 큐를 이용해 반시게 방향 회전을 구현한다.
    // 각 라인의 회전 횟수는 rotateCnt % Queue.size() -> 총 회전 횟수 % 현재 라인의 길이로 최적화한다.

    static int r, c, rotateCnt;
    static int[][] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        rotateCnt = Integer.parseInt(st.nextToken());

        arr = new int[r][c];
        for(int i=0; i<r; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<c; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        rotate();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                sb.append(arr[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void rotate() {
        Queue<Integer> q = new ArrayDeque<>();

        // 외곽부터 각 라인마다 회전시킨다.
        // 문제에는 따로 언급되지 않으나, r과 c 중 작은 수에 해당하는 값은 반드시 짝수여야 한다.
        for(int i=0; i<Math.min(r, c) / 2; i++) {
            // 반시계 방향 회전 구현을 위해 (i, i)부터 시계 방향으로 같은 라인의 모든 값을 큐에 넣는다.
            for(int j=i; j<c-1-i; j++) q.add(arr[i][j]);
            for(int j=i; j<r-1-i; j++) q.add(arr[j][c-1-i]);
            for(int j=c-1-i; j>i; j--) q.add(arr[r-1-i][j]);
            for(int j=r-1-i; j>i; j--) q.add(arr[j][i]);

            // rotateCnt 만큼 회전할 필요 없음
            // rotateCnt % q.size()인 나머지 값만큼 돌려도 결과는 동일하다.
            int cnt = rotateCnt % q.size();
            for(int j=0; j<cnt; j++) q.add(q.poll());

            // 큐에 값을 넣었던 순서 그대로 다시 회전된 결과를 재배치한다.
            for(int j=i; j<c-1-i; j++) arr[i][j] = q.poll();
            for(int j=i; j<r-1-i; j++) arr[j][c-1-i] = q.poll();
            for(int j=c-1-i; j>i; j--) arr[r-1-i][j] = q.poll();
            for(int j=r-1-i; j>i; j--) arr[j][i] = q.poll();
        }
    }

}