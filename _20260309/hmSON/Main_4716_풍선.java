import java.io.*;
import java.util.*;

public class Main_4716_풍선 {

    /*
     * BOJ_4716 : 풍선 (Gold_1)
     * 자료구조 및 알고리즘 : 그리디, 정렬
     *
     * [문제 요약]
     * - 각 팀은 K개의 풍선을 받아야 하고, 방 A/B에서 팀까지의 거리가 각각 DA/DB로 주어진다.
     * - A방에는 roomA개, B방에는 roomB개의 풍선이 있다.
     * - 풍선은 한 번에 1개씩만 옮길 수 있으므로, K개를 주면 거리 비용도 K배가 된다.
     * - 모든 팀에 풍선을 전달할 때 "방 -> 팀" 이동 거리 합의 최솟값을 구한다.
     *
     * [핵심 아이디어]
     * - 단순히 "가까운 방 우선"만으로는 최적이 보장되지 않는다.
     * - 중요한 기준은 각 팀의 오배정 손해값 |DA - DB|이다.
     *   -> 이 값이 큰 팀일수록 잘못된 방에서 줄 때 손해가 크므로 먼저 처리해야 한다.
     * - 따라서 팀을 |DA - DB| 내림차순으로 정렬한 뒤,
     *   각 팀은 더 가까운 방에서 가능한 만큼 먼저 배정한다.
     * - 가까운 방 재고가 부족하면 남은 수량만 반대 방에서 배정한다.
     *
     * [구현 메모]
     * - balloons[i] = {K, DA, DB}
     * - 정렬: abs(DB - DA) 내림차순
     * - 처리 순회:
     *   - DA < DB: A방 우선 배정, 부족분은 B방에서 배정
     *   - DA >= DB: B방 우선 배정, 부족분은 A방에서 배정
     * - DA == DB인 팀은 어느 방에서 줘도 단가가 같으므로
     *   남은 재고 상황에 맞춰 배정되어도 최적성에 영향을 주지 않는다.
     *
     * [시간 복잡도]
     * - 팀 정렬: O(N log N)
     * - 한 번 순회하며 배정: O(N)
     * - 총: O(N log N) (N <= 1000)
     */
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int teamCnt, roomA, roomB;
    static int[][] balloons;

    static boolean init() throws Exception {
        st = new StringTokenizer(br.readLine());
        teamCnt = Integer.parseInt(st.nextToken());
        roomA = Integer.parseInt(st.nextToken());
        roomB = Integer.parseInt(st.nextToken());
        if(teamCnt == 0) return false;

        balloons = new int[teamCnt][3];

        for(int i=0; i<teamCnt; i++) {
            st = new StringTokenizer(br.readLine());
            balloons[i][0] = Integer.parseInt(st.nextToken());
            balloons[i][1] = Integer.parseInt(st.nextToken());
            balloons[i][2] = Integer.parseInt(st.nextToken());
        }

        // 오배정 손해값(|DA-DB|)이 큰 팀부터 배정해야 전체 비용이 최소가 된다.
        Arrays.sort(balloons, (a, b) -> Integer.compare(Math.abs(b[2] - b[1]), Math.abs(a[2] - a[1])));
        return true;
    }

    public static void main(String[] args) throws Exception {
        while(true) {
            boolean read = init();
            if(!read) {
                System.out.println(sb);
                return;
            }

            int total = 0;
            for(int i=0; i<teamCnt; i++) {
                int cnt = balloons[i][0];

                // A가 더 가깝다면 A를 우선 사용하고, 부족분만 B에서 보낸다.
                if(balloons[i][1] < balloons[i][2]) {
                    if(cnt <= roomA) {
                        total += cnt * balloons[i][1];
                        roomA -= cnt;
                    } else {
                        total += (roomA * balloons[i][1]) + ((cnt - roomA) * balloons[i][2]);
                        roomB -= cnt - roomA; roomA = 0;
                    }
                }
                // B가 더 가깝거나(DA > DB), 두 방이 같은 거리(DA == DB)인 경우 B를 우선 사용한다.
                else {
                    if(cnt <= roomB) {
                        total += cnt * balloons[i][2];
                        roomB -= cnt;
                    } else {
                        total += (roomB * balloons[i][2]) + ((cnt - roomB) * balloons[i][1]);
                        roomA -= cnt - roomB; roomB = 0;
                    }
                }
            }

            sb.append(total).append("\n");
        }
    }

}
