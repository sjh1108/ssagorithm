import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 구현, 시뮬레이션, 덱
     * 확산(#32), 회전(#33) 로직을 모두 적용해야 함
     * 확산 - (각 칸의 값) / 5 만큼의 수치를 인접한 다른 칸으로 확산
     * 회전 - 바깥쪽부터 시작해서 홀수 고리는 시계 방향, 짝수 고리는 반시계 방향으로 "1칸씩" 회전
     * 정화 - 각 행마다 가장 큰 값의 절반을 배출 및 총 배출값 확산
     *
     * 정화는 매 턴의 마지막마다 각 행의 최대값을 가진 첫번째 위치를 확인하여 (arr[i][j] / 2)만큼의 값을 배출
     * 확산과 회전은 기존의 풀이를 그대로 적용하되, 회전 횟수는 매 턴 1회로 고정됨에 유의
     */

    static int r, c, t;
    // 총 배출량 합산
    static long discharged = 0L;
    // temp : 확산 단계에서의 임시 배열
    static long[][] arr, temp;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        arr = new long[r][c];
        temp = new long[r][c];
        for(int i=0; i<r; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<c; j++) {
                arr[i][j] = Long.parseLong(st.nextToken());
            }
        }

        for(int i=0; i<t; i++) {
            spread();
            rotate();
            discharge();
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                sb.append(arr[i][j]).append(" ");
            }
            sb.append("\n");
        }
        sb.append(discharged);

        System.out.println(sb);
    }

    // 1. 확산
    // 매 칸마다 인접한 4방향으로 (현재 위치의 마나량) / 5 만큼의 값을 확산
    // 기존 칸에서는 확산된 값만큼 차감
    // 맵 밖으로는 확산시킬 수 없음
    // 확산된 값은 임시 배열에 저장한 후 일괄 적용
    static void spread() {
        for(int i=0; i<r; i++) Arrays.fill(temp[i], 0);

        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                if(arr[i][j] < 5) continue;
                long val = arr[i][j] / 5;

                for(int k=0; k<4; k++) {
                    int y = i + dy[k];
                    int x = j + dx[k];
                    if(y < 0 || y >= r || x < 0 || x >= c) continue;

                    arr[i][j] -= val;
                    temp[y][x] += val;
                }
            }
        }

        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                arr[i][j] += temp[i][j];
            }
        }
    }

    // 2. 회전
    // 양방향 회전을 요구하므로 #33과 동일하게 덱(Deque) 사용
    // 0-based 기준 짝수 고리 : 시계 방향이므로 덱의 마지막 값을 처음 위치에 넣는다
    // 0-based 기준 홀수 고리 : 반시계 방향이므로 덱의 처음 값을 마지막 위치에 넣는다
    // #33과 달리 매 턴마다 1칸씩만 회전함
    static void rotate() {
        Deque<Long> deq = new ArrayDeque<>();
        for(int i=0; i<Math.min(r, c) / 2; i++) {
            for(int j=i; j<c-1-i; j++) deq.addLast(arr[i][j]);
            for(int j=i; j<r-1-i; j++) deq.addLast(arr[j][c-1-i]);
            for(int j=c-1-i; j>i; j--) deq.addLast(arr[r-1-i][j]);
            for(int j=r-1-i; j>i; j--) deq.addLast(arr[j][i]);

            if(i%2 == 0) deq.addFirst(deq.pollLast());
            else deq.addLast(deq.pollFirst());

            for(int j=i; j<c-1-i; j++) arr[i][j] = deq.pollFirst();
            for(int j=i; j<r-1-i; j++) arr[j][c-1-i] = deq.pollFirst();
            for(int j=c-1-i; j>i; j--) arr[r-1-i][j] = deq.pollFirst();
            for(int j=r-1-i; j>i; j--) arr[j][i] = deq.pollFirst();
        }
    }

    // 3. 정화
    // 각 행마다 최대값을 가진 칸의 위치를 저장
    // 최대값을 가진 칸이 한 행에 여러 곳일 경우 열 번호가 빠른 칸을 적용
    // (최대값을 가진 칸의 값) / 2 만큼을 기존 위치에서 빼고 총 배출량에 합산
    static void discharge() {
        for(int i=0; i<r; i++) {
            int idx = -1;
            long max = -1;

            for(int j=0; j<c; j++) {
                if(arr[i][j] > max) {
                    max = arr[i][j];
                    idx = j;
                }
            }

            discharged += arr[i][idx] / 2;
            arr[i][idx] -= arr[i][idx] / 2;
        }
    }

}