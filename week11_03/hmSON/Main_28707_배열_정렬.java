import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_28707_배열_정렬 {

    /*
     * BOJ_28707 : 배열 정렬
     * 자료구조 및 알고리즘 : 상태 그래프 최단거리(Dijkstra), 해시맵(dist), 우선순위큐
     *
     * [문제 요약]
     * - 길이 N의 배열이 주어지고, K개의 연산 (i, j, c)이 제공된다.
     *   각 연산은 위치 i와 j의 원소를 서로 교환(swap)하며, 비용 c가 든다.
     * - 여러 연산을 조합해 배열을 오름차순으로 정렬하려 할 때, 총 비용의 최솟값을 구하라.
     * - 정렬이 불가능한 경우 -1을 출력.
     *
     * [접근 아이디어]
     * - 각 "배열 상태"를 노드, 한 번의 "스왑 연산"을 간선(가중치: 비용)으로 보는 상태 그래프 문제.
     * - 시작 상태에서 목표 상태(오름차순 정렬)까지의 최소 비용 경로를 Dijkstra로 탐색한다.
     *   - dist[상태문자열] = 현재까지 알려진 최소 비용
     *   - 우선순위큐에는 (상태, 비용)을 넣고, 더 작은 비용부터 확장
     * - 상태 표현은 문자열로 단순화. (예: "0123...")
     *
     * [구현 포인트]
     * - 입력 값들을 '0 기반 문자'로 변환해 상태 문자열을 compact하게 관리.
     * - 방문배열 대신 HashMap<String, Integer>를 dist 테이블로 사용.
     * - 우선순위큐에서 꺼낸 비용이 dist보다 크면 skip(최단 경로 갱신 불필요).
     *
     * [시간 복잡도]
     * - 상태 공간은 최악에선 N! 이지만, 제공된 스왑 연산들로 생성되는 이웃 수가 K로 제한된다.
     * - Dijkstra 한 번의 팝/푸시가 log(#큐 크기), 각 상태에서 간선 K개 확장.
     */
    
    static int n, k;                  // n: 배열 길이, k: 연산 개수
    static char[] arr;                // 시작 배열(0-based 문자로 저장)
    static int[][] cmds;              // 스왑 연산 목록: [i, j, cost]
    static HashMap<String, Integer> map = new HashMap<>(); // dist 테이블: 상태 -> 최소 비용
    
    // 우선순위큐에 넣을 상태-비용 쌍
    static class Pair implements Comparable<Pair> {
        String state; // 현재 상태(문자열)
        int cost;     // 이 상태에 도달한 누적 비용
        
        public Pair(String state, int cost) {
            this.state = state;
            this.cost = cost;
        }
        @Override
        public int compareTo(Pair o) {
            return this.cost - o.cost; // 비용 오름차순
        }
    }
    
    // 입력 및 초기화
    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        
        // 상태를 문자열로 관리하기 위해 각 값을 0-based char로 변환 (값-1 후 '0' 더함)
        // 이러한 풀이가 가능한 이유? -> 주어지는 수열 내 수의 범위가 1~10까지밖에 안되기 때문. 그 이상으로 넘어가면 불가능한 전략임
        // ★★★ 사실 이 전략은 지쌤에게 약간의 힌트를 요구했고, 수열에 들어갈 수 있는 수의 범위가 1~10이라는 점을 이용할 수 있다는 힌트에서 시작됐다.
        // ★★★ 근데 나중에 다시 물어보니 지쌤의 의도는 비트마스킹을 이용하는 것이었다. 의도에서 한참 벗어난 것이다. 
        // ★★★ 어쨋든 이거 덕분에 800ms 통으로 지워버렸으니까 한잔해
        arr = new char[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = (char) ('0' + (Integer.parseInt(st.nextToken()) - 1));
        }
        
        // 스왑 연산들 (1-based 인덱스가 들어오므로 사용 시 -1 처리)
        k = Integer.parseInt(br.readLine());
        cmds = new int[k][3];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                cmds[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
    
    /*
     * dijkstra()
     * - 시작 상태에서 목표 상태(오름차순)로 가는 최소 비용을 계산.
     * - 도달 불가 시 -1 반환.
     */
    static int dijkstra() {
        // 목표 상태(오름차순) 구성
        char[] copied = Arrays.copyOf(arr, n);
        Arrays.sort(copied);
        String target = String.valueOf(copied);
        
        // 시작 상태
        String start = String.valueOf(arr);
        if (start.equals(target)) return 0; // 이미 정렬됨
        
        // 우선순위큐 준비 및 dist 초기화
        PriorityQueue<Pair> q = new PriorityQueue<>();
        q.add(new Pair(start, 0));
        map.put(start, 0);
        
        // Dijkstra
        while (!q.isEmpty()) {
            Pair p = q.poll();
            
            // 목표 도달 시 누적 비용이 최소
            if (p.state.equals(target)) return p.cost;
            // 더 나은 경로가 이미 알려진 상태면 스킵
            if (p.cost > map.get(p.state)) continue;
            
            // 모든 스왑 연산 적용
            for (int[] cmd : cmds) {
                char[] str = p.state.toCharArray();
                int left  = cmd[0] - 1; // 0-based
                int right = cmd[1] - 1; // 0-based
                int w     = cmd[2];     // 스왑 비용
                
                // 스왑 적용
                char temp = str[left];
                str[left] = str[right];
                str[right] = temp;
                
                String next = String.valueOf(str);
                int newCost = p.cost + w;
                
                // 더 좋은 비용으로만 갱신
                if (newCost >= map.getOrDefault(next, Integer.MAX_VALUE)) continue;
                map.put(next, newCost);
                q.add(new Pair(next, newCost));
            }
        }
        
        // 큐가 빌 때까지 목표를 못 만났다면 도달 불가
        return -1;
    }
    
    public static void main(String[] args) throws Exception {
        init();
        System.out.println(dijkstra());
    }
	
}
