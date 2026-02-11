import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_3079_입국심사 {

    /*
     * BOJ_3079 : 입국심사 (Gold_5)
     * 자료구조 및 알고리즘 : 이분 탐색(매개변수 탐색)
     *
     * [문제 요약]
     * - N개의 심사대가 있고, 각 심사대 i는 한 명을 처리하는 데 T[i]분이 걸린다.
     * - M명의 사람이 모두 심사를 마치는 데 걸리는 '최소 시간'을 구하라.
     *
     * [핵심 아이디어]
     * - "시간 t분 안에 M명을 처리할 수 있는가?"를 판별할 수 있다.
     *   -> 각 심사대가 t분 동안 처리 가능한 인원 수는 (t / T[i])명.
     *   -> 모든 심사대의 처리 가능 인원의 합이 M 이상이면 t분은 가능, 아니면 불가능.
     * - 가능/불가능이 시간에 대해 단조성을 가지므로(시간이 늘수록 가능해짐),
     *   최소 가능한 시간을 이분 탐색으로 찾는다.
     *
     * [구현 메모]
     * - 탐색 구간:
     *   left = 1
     *   right = (가장 빠른 심사대 시간) * M  (최악 케이스: 가장 빠른 심사대 하나가 전부 처리)
     * - mid 시간에서 처리 가능한 인원을 누적하는 대신,
     *   ready = M에서 (mid / T[i])만큼 빼서 ready <= 0인지로 판별(조기 종료 가능).
     * - right 값은 long으로 잡아 오버플로우를 방지한다.
     *
     * [시간 복잡도]
     * - 판별 1회: O(N)
     * - 이분 탐색 횟수: O(log (min*T * M))
     * - 총: O(N log (min*T * M))
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int desk = Integer.parseInt(st.nextToken());
        int mens = Integer.parseInt(st.nextToken());

        int[] deskLine = new int[desk];
        int min = Integer.MAX_VALUE;
        for(int i=0; i<desk; i++) {
            int x = Integer.parseInt(br.readLine());
            if(x < min) min = x;      // 가장 빠른 심사대 시간(상한 계산에 사용)
            deskLine[i] = x;
        }

        // [매개변수 탐색] 시간 t를 기준으로 "t분 안에 mens명을 처리 가능?"을 판별
        long left = 1L, right = (long)min * mens; // right는 최악 상한이 커질 수 있어 long 필요
        while(left <= right) {
            long mid = left + (right - left) / 2;

            int ready = mens; // mid분 안에 처리해야 하는 남은 인원 수
            for(int i=0; i<desk; i++) {
                // i번 심사대가 mid분 동안 처리 가능한 인원: mid / deskLine[i]
                ready -= (int)(mid / deskLine[i]);

                // 이미 모두 처리 가능(ready <= 0)이라면 더 볼 필요 없음(조기 종료)
                if(ready <= 0) break;
            }

            // mid분 안에 처리 가능 -> 더 줄여볼 수 있으므로 right 감소
            if(ready <= 0) right = mid - 1;
                // mid분 안에 처리 불가 -> 시간을 늘려야 하므로 left 증가
            else left = mid + 1;
        }

        // 탐색 종료 후 left가 '최소로 가능한 시간'
        System.out.println(left);

    }

}
