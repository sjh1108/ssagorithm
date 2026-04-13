import java.io.*;
import java.util.*;

public class Main_2593_엘리베이터 {

    /*
     * BOJ_2593 : 엘리베이터 (Gold_1)
     * 자료구조 및 알고리즘 : BFS, 역추적, 수학, 정수론
     *
     * [문제 요약]
     * - 건물의 최고 층수, 여러 대의 엘리베이터 정보, 시작 층과 도착 층이 주어진다.
     * - 각 엘리베이터는 "처음 서는 층(first)"과 "운행 간격(dist)"으로 운행한다.
     * - 시작 층에서 도착 층까지 가기 위해 필요한 최소 엘리베이터 탑승 횟수를 구하고,
     *   그때 어떤 엘리베이터들을 타는지 순서대로 출력해야 한다.
     *
     * [핵심 아이디어]
     * - 층을 정점으로 보면 최대 100,000층까지 있어 비효율적이다.
     * - 대신 "엘리베이터"를 정점으로 보고,
     *   두 엘리베이터가 같은 층에 설 수 있으면 서로 환승 가능한 간선이 있다고 생각하면 된다.
     * - 시작 층에 설 수 있는 엘리베이터들에서 BFS를 시작하면
     *   도착 층에 설 수 있는 엘리베이터까지의 최소 탑승 횟수를 구할 수 있다.
     * - 환승 가능 여부는 실제 층을 모두 돌지 않고,
     *   두 등차수열이 공통 원소를 가지는지(최대공약수/최소공배수)로 판단한다.
     *
     * [구현 메모]
     * - Elevator:
     *   - first : 처음 서는 층
     *   - dist  : 운행 간격
     *   - last  : 건물 내에서 실제로 설 수 있는 마지막 층
     * - canGo(floor):
     *   - 해당 엘리베이터가 floor에 설 수 있는지 확인
     * - canTransfer(idx1, idx2):
     *   1) 두 엘리베이터 운행 구간이 실제로 겹치는지 확인
     *   2) 두 등차수열이 같은 층에서 만날 가능성이 있는지 gcd로 확인
     *      -> |a1-a2|가 gcd(d1,d2)의 배수여야 함
     *   3) 공통 주기는 lcm(d1,d2)이므로 그 범위까지만 보면 충분
     *   4) e1의 정차 층 기준으로 시작점을 보정한 뒤, 실제 공통 층이 있는지 확인
     * - BFS:
     *   - 시작 층에 설 수 있는 엘리베이터들을 시작 정점으로 넣는다.
     *   - prev 배열로 이전에 탄 엘리베이터를 기록해 경로를 복원한다.
     *   - 도착 층에 설 수 있는 엘리베이터를 처음 찾는 순간이 최소 탑승 횟수다.
     *
     * [시간 복잡도]
     * - 엘리베이터 수를 E라 하면 BFS에서 엘리베이터 쌍을 확인하므로 기본적으로 O(E^2)
     * - 각 환승 판정은 gcd/lcm 및 제한된 범위 확인으로 처리
     * - 문제 조건(E ≤ 100)에서는 충분히 가능하다.
     */

    // 엘리베이터 클래스, 운행 가능한 최저층, 최고층과 운행 간격 정보를 가짐
    static class Elevator {
        int first, dist, last;

        public Elevator(int first, int dist, int top) {
            this.first = first;
            this.dist = dist;
            this.last = first + ((top - first) / dist * dist);
        }

        // 이 엘리베이터가 floor 층에 갈 수 있는지 확인
        public boolean canGo(int floor) {
            if(floor < first) return false;
            return (floor - first) % dist == 0;
        }

    }

    // 건물 최고층, 엘리베이터 수, 시작층, 도착층, 도착 시 사용한 엘리베이터 번호
    static int top, e, start, end, last = -1;
    static gold_1.Main_2593.Elevator[] elevators;
    // 역추적을 위한 직전 사용 엘리베이터 번호 저장
    static int[] prev;

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        top = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        elevators = new gold_1.Main_2593.Elevator[e];
        prev = new int[e];
        Arrays.fill(prev, e);

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int base = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            elevators[i] = new gold_1.Main_2593.Elevator(base, dist, top);
        }

        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());
    }

    // 최대공약수 반환
    static int getGCD(int a, int b) {
        int temp;
        if(a < b) { temp = a; a = b; b = temp; }

        while(b != 0) {
            temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    // 두 엘리베이터 간 환승 가능 여부 확인
    static boolean canTransfer(int idx1, int idx2) {
        // 1. 두 엘리베이터의 실제 운행 구간이 겹쳐야 환승 가능성이 생긴다.
        gold_1.Main_2593.Elevator e1 = elevators[idx1], e2 = elevators[idx2];
        int startFloor = Math.max(e1.first, e2.first);
        int endFloor = Math.min(e1.last, e2.last);
        if(endFloor < startFloor) return false;

        // 2. 두 등차수열이 공통 원소를 가질 수 있는지 gcd로 먼저 판정
        int gcd = getGCD(e1.dist, e2.dist);
        if(Math.abs(e1.first - e2.first) % gcd != 0) return false;

        // 3. 공통 패턴은 lcm 주기로 반복되므로 그 범위까지만 확인하면 충분
        int lcm = (e1.dist * e2.dist) / gcd;

        // 4. e1이 실제로 서는 층으로 시작점을 보정
        int offset = (startFloor - e1.first) % e1.dist;
        if(offset != 0) startFloor += e1.dist - offset;

        // 5. 도착점이 시작점 + 최소공배수보다 낮을 수 있으므로 도착점 보정
        endFloor = Math.min(startFloor + lcm, endFloor);
        if(startFloor > endFloor) return false;

        // 6. 보정된 구간 안에서 두 엘리베이터가 같은 층에 서는지 확인
        for(int i=startFloor; i<=endFloor; i+=e1.dist) {
            if((i - e2.first) % e2.dist == 0) return true;
        }

        return false;
    }

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();

        // 시작 층에 설 수 있는 엘리베이터들을 BFS 시작점으로 사용
        for(int i=0; i<e; i++) {
            if(!elevators[i].canGo(start)) continue;

            q.add(new int[]{i, 1});
            prev[i] = -1;
            if(elevators[i].canGo(end)) {
                last = i; return 1;
            }
        }

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<e; i++) {
                if(prev[i] != e || !canTransfer(cur[0], i)) continue;
                prev[i] = cur[0];
                if(elevators[i].canGo(end)) {
                    last = i;
                    return cur[1] + 1;
                }

                q.add(new int[]{i, cur[1] + 1});
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        init();
        StringBuilder sb = new StringBuilder();
        sb.append(bfs()).append("\n");

        // prev를 따라가며 탑승한 엘리베이터 경로 복원
        Deque<Integer> deq = new ArrayDeque<>();
        while(last != -1) {
            deq.addLast(last);
            last = prev[last];
        }

        while(!deq.isEmpty()) sb.append(deq.removeLast() + 1).append("\n");
        System.out.println(sb);
    }

}