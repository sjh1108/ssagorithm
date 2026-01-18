import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main_10282_손홍민 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    static int n, d, start;
    static List<List<int[]>> list = new ArrayList<>();
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            init();
            infect();

            // 매 테스트 케이스가 종료될 때마다 인접 리스트 초기화
            list.clear();
        }
        System.out.println(sb);
    }

    public static void init() throws Exception {
        // 초기 설정 입력
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken()) - 1;

        // 인접 리스트 세팅 및 중복 방지 세팅
        for(int i=0; i<n; i++) {
            list.add(new ArrayList<>());
        }
        visited = new boolean[n];

        // 각 의존성 정보 입력
        for(int i=0; i<d; i++) {
            st = new StringTokenizer(br.readLine());
            int depend = Integer.parseInt(st.nextToken()) - 1;
            int target = Integer.parseInt(st.nextToken()) - 1;
            int sec = Integer.parseInt(st.nextToken());
            list.get(target).add(new int[] {depend, sec});
        }
    }

    public static void infect() {
        // 각각 감염된 컴퓨터 수, 총 소요 시간
        int infectedCnt = 0, totalSec = 0;
        List<int[]> link;
        // 다익스트라 알고리즘을 위한 우선순위 큐 생성
        // 정렬 기준(a[1]) : 현재 컴퓨터가 감염되기까지 소요된 시간(초)
        // a[0] = 컴퓨터의 인덱스, a[1] = 현재까지 소요된 시간(초)
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // (a, b) -> Integer.compare(a[1], b[1])
        q.add(new int[] {start, 0});
        while(!q.isEmpty()) {
            int[] curr = q.poll();
            int currCom = curr[0], elapsed = curr[1];
            if(visited[currCom]) continue;
            visited[currCom] = true;
            infectedCnt++;
            totalSec = elapsed;
            link = list.get(currCom);
            for(int[] next : link) {
                int nextCom = next[0], costTime = next[1];
                q.add(new int[] {nextCom, elapsed + costTime});
            }
        }

        sb.append(infectedCnt).append(" ").append(totalSec).append("\n");
    }
}
