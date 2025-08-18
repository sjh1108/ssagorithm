import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1916_최소비용_구하기_손홍민 {

    // 각각 도시 수, 버스 수, 출발할 도시, 도착해야 할 도시
    // 그래프 간선을 인접리스트로 관리, int[]는 각각 버스가 도착하는 도시 번호와 비용
    // 다익스트라 알고리즘에 사용할 최소비용 갱신 배열
    static int n, busCnt, start, end;
    static List<List<int[]>> list = new ArrayList<>();
    static int[] dist;

    public static void main(String[] args) throws Exception {
        init();
        dijkstra();
    }

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        busCnt = Integer.parseInt(br.readLine());
        for(int i=0; i<n; i++) {
            list.add(new ArrayList<>());
        }
        // 모든 도시로 도착하는 데 드는 비용을 미리 최대값으로 초기화
        dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        StringTokenizer st;
        for(int i=0; i<busCnt; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1;
            int e = Integer.parseInt(st.nextToken()) - 1;
            int cost = Integer.parseInt(st.nextToken());
            list.get(s).add(new int[]{e, cost});
        }
        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken()) - 1;
        end = Integer.parseInt(st.nextToken()) - 1;
    }

    static void dijkstra() {
        // 우선순위 큐. 정렬 순서는 현재까지 사용한 비용 오름차순
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        dist[start] = 0;
        q.add(new int[]{start, 0});
        // 현재 도시로부터 이동 가능한 모든 버스에 대한 비용 합계를 기록
        // 우선순위 큐에서 poll되는 값은 현재까지 이동하는 데 소요되는 비용이 가장 적은 도시의 번호 및 비용임
        // 다른 루트가 더 적은 비용으로 이동할 수 있었다면 굳이 탐색할 필요 없음
        // 어떻게든 도착점까지 왔다면 그 루트가 제일 빠르게 올 수 있는 루트임. 조기 종료
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int curCity = cur[0], curCost = cur[1];
            if(curCity == end) break;
            if(dist[curCity] < curCost) continue;
            List<int[]> nextCities = list.get(curCity);
            for(int[] next : nextCities) {
                int nextCity = next[0];
                int nextCost = next[1] + curCost;
                if(dist[nextCity] <= nextCost) continue;
                dist[nextCity] = nextCost;
                q.add(new int[]{nextCity, nextCost});
            }
        }

        // end 도시까지 도착하는 데 드는 비용 출력
        System.out.println(dist[end]);
    }

}
