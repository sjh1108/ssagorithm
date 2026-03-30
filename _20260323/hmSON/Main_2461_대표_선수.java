import java.io.*;
import java.util.*;

public class Main_2461_대표_선수 {

    /*
     * BOJ_2461 : 대표 선수 (Gold_2)
     * 자료구조 및 알고리즘 : 정렬, 우선순위 큐, 두 포인터
     *
     * [문제 요약]
     * - N개 반(팀)마다 학생들의 능력치가 S개씩 주어진다.
     * - 각 반에서 대표 선수 1명씩을 뽑아 N명을 구성할 때,
     *   (선택된 능력치의 최댓값 - 최솟값)을 최소로 만드는 값을 출력한다.
     *
     * [핵심 아이디어]
     * - 각 반에서 1명씩 뽑아야 하므로 "N개의 정렬된 리스트에서 하나씩 선택"하는 문제로 볼 수 있다.
     * - 어떤 시점의 선택 상태에서
     *   - 현재 선택된 값들 중 최솟값을 올려주면(해당 반에서 더 큰 후보로 교체),
     *     구간 [min, max]가 줄어들 가능성이 생긴다.
     * - 따라서
     *   1) 각 반의 능력치를 오름차순으로 준비해두고(이 코드는 PriorityQueue로 정렬 상태 유지),
     *   2) 각 반에서 현재 후보 1명을 뽑아 "현재 최솟값"을 우선순위 큐(stats)에서 관리하며,
     *   3) 최솟값을 가진 반의 후보를 다음 후보로 교체해가며 답을 갱신한다.
     * - 이 방식은 정렬된 리스트에서 구간을 좁혀가는 전형적인 투 포인터/슬라이딩 윈도우 형태와 같다.
     *
     * [구현 메모]
     * - q[i] : i번 반의 학생 능력치를 오름차순으로 꺼내기 위한 PriorityQueue
     * - stats : 현재 각 반에서 뽑힌 후보 (반 번호, 능력치)를 담고,
     *           능력치 기준 오름차순으로 정렬 -> peek/poll이 현재 최솟값
     * - max : 현재 후보들 중 최댓값을 별도로 유지
     * - 초기화:
     *   - 각 반에서 최소 능력치 1명씩 뽑아 stats에 넣고 max 갱신
     * - 반복:
     *   - stats에서 최솟값 후보를 꺼내고 diff = max - min을 답 후보로 갱신
     *   - 그 후보가 속한 반에서 다음 능력치(next)를 꺼내 새 후보로 넣는다.
     *   - 해당 반의 후보가 더 이상 없으면(필수 조건: 모든 반에서 1명씩) 종료
     *
     * [시간 복잡도]
     * - 각 반에서 S개를 PriorityQueue에 넣는 비용 포함: O(N * S log S)
     * - 반복은 전체 원소를 최대 한 번씩 교체하며 stats는 O(log N)
     * - 총: O(N * S log S + N * S log N) 정도
     */

    static int team; // 반 수
    static PriorityQueue<Integer>[] q; // 각 반별 학생들의 능력치를 오름차순 정렬

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        team = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        q = new PriorityQueue[team];
        for(int i=0; i<team; i++) {
            q[i] = new PriorityQueue<>();

            st = new StringTokenizer(br.readLine());
            for(int j=0; j<s; j++) {
                q[i].add(Integer.parseInt(st.nextToken()));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        init();

        int minDiff = Integer.MAX_VALUE, max = 0;
        // stats: {반 번호, 현재 후보 능력치}, 능력치 오름차순 -> poll이 현재 최솟값
        PriorityQueue<int[]> stats = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        // 각 반에서 최소값 1개씩 뽑아 초기 후보 구성
        for(int i=0; i<team; i++) {
            int stat = q[i].poll();
            if(stat > max) max = stat;
            stats.add(new int[]{i, stat});
        }

        while(true) {
            // 현재 후보들 중 최솟값을 가진 반을 한 단계 올려본다.
            int[] min = stats.poll();
            int diff = max - min[1];
            if(diff < minDiff) minDiff = diff;

            // 해당 반에 더 뽑을 후보가 없으면 모든 반에서 1명씩 유지가 불가능 -> 종료
            if(q[min[0]].isEmpty()) break;

            int next = q[min[0]].poll();
            if(next > max) max = next;
            stats.add(new int[]{min[0], next});
        }

        System.out.println(minDiff);
    }

}