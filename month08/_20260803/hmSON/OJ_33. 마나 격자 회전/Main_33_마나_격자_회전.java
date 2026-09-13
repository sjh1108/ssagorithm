import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 구현, 시뮬레이션, 덱
     * 정원의 바깥쪽 고리부터 1번 고리라고 할 때, 홀수 고리는 시계 방향으로, 짝수 고리는 반시계 방향으로 회전한다
     * 덱(Deque)을 이용해 양방향 회전을 구현한다.
     * 1. 시계 방향 회전 : 덱의 마지막 값을 빼서 처음 위치로 밀어넣는다
     * 2. 반시계 방향 회전 : 덱의 처음 값을 빼서 마지막 위치로 밀어넣는다
     * 각 고리의 길이가 입력받은 회전 횟수보다 작을 수 있으므로 (rotateCnt) mod (deq.size) 만큼만 회전시켜도 결과는 동일하다
     *
     * 정원의 각 칸은 int 범위를 초과하는 값일 수 있다
     */

    static int r, c, rotateCnt;
    static long[][] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        rotateCnt = Integer.parseInt(st.nextToken());

        arr = new long[r][c];
        for(int i=0; i<r; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<c; j++) {
                arr[i][j] = Long.parseLong(st.nextToken());
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
        // 각 고리마다 회전해야 하는 방향이 다르므로 덱(Deque) 사용
        Deque<Long> deq = new ArrayDeque<>();
        for(int i=0; i<Math.min(r, c) / 2; i++) {
            for(int j=i; j<c-1-i; j++) deq.addLast(arr[i][j]);
            for(int j=i; j<r-1-i; j++) deq.addLast(arr[j][c-1-i]);
            for(int j=c-1-i; j>i; j--) deq.addLast(arr[r-1-i][j]);
            for(int j=r-1-i; j>i; j--) deq.addLast(arr[j][i]);

            // 실제 회전 횟수가 각 고리의 길이보다 클 수 있으므로 유효한 횟수만 남김
            // 유효 횟수가 0이면 굳이 재입력할 필요 없으므로 넘김
            int validCnt = rotateCnt % deq.size();
            if(validCnt == 0) {
                deq.clear(); continue;
            }

            if(i%2 == 0) {
                // 시계 방향 회전
                for(int j=0; j<validCnt; j++) deq.addFirst(deq.pollLast());
            } else {
                // 반시계 방향 회전
                for(int j=0; j<validCnt; j++) deq.addLast(deq.pollFirst());
            }

            for(int j=i; j<c-1-i; j++) arr[i][j] = deq.pollFirst();
            for(int j=i; j<r-1-i; j++) arr[j][c-1-i] = deq.pollFirst();
            for(int j=c-1-i; j>i; j--) arr[r-1-i][j] = deq.pollFirst();
            for(int j=r-1-i; j>i; j--) arr[j][i] = deq.pollFirst();
        }
    }

}