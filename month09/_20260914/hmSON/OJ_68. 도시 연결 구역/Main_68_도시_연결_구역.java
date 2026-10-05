import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : BFS
     * 그래프 탐색을 이용해 모든 정점을 1회씩 탐색하며, 각 그룹의 정점 수와 총 그룹 수를 확인하는 것이 목적
     * 각 정점 간 인접 리스트를 만들어 두고, 모든 정점을 한번씩 확인한 뒤 미방문한 정점을 기준으로 BFS 수행
     * 각 BFS 수행마다 방문하는 모든 정점을 방문 처리하고 방문한 정점의 수를 카운트
     */

    static List<Integer>[] graph; // 인접 리스트
    static boolean[] visited; // 방문 처리 배열
    static Queue<Integer> q = new ArrayDeque<>(); // BFS용 큐, 객체 재생성 방지를 위해 static으로 사용

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        // 인접 리스트 초기화
        graph = new ArrayList[v];
        visited = new boolean[v];
        for(int i=0; i<v; i++) {
            graph[i] = new ArrayList<>();
        }

        // 인접 리스트 등록
        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            graph[v1].add(v2);
            graph[v2].add(v1);
        }

        // 각각 그룹 수, 최대 그룹의 크기, 각 그룹별 연결 요소의 합
        int cnt = 0, max = 0;
        long sum = 0;

        for(int i=0; i<v; i++) {
            // 이미 BFS 과정에서 방문한 정점은 무시
            if(visited[i]) continue;
            // 반환값 : 그룹 크기
            int curCnt = bfs(i);

            cnt++;
            if(curCnt > max) max = curCnt; // 최대 그룹 크기 갱신

            // 크기가 A인 그룹의 연결 요소 수 : A(A-1)/2
            sum += (long)curCnt * (curCnt - 1) / 2;
        }

        System.out.println(cnt + "\n" + max + "\n" + sum);
    }

    // BFS 메서드
    // 시작 정점 번호를 매개변수로 받아 BFS를 수행하여 해당 정점이 속한 그룹 전체 방문
    // BFS 종료 후 해당 그룹의 크기 반환
    static int bfs(int start) {
        // 큐 초기화
        q.clear();
        visited[start] = true;
        q.add(start);

        // 방문 정점마다 카운트
        int cnt = 0;
        while(!q.isEmpty()) {
            int cur = q.poll();
            cnt++;

            for(int next : graph[cur]) {
                if(visited[next]) continue;
                visited[next] = true;
                q.add(next);
            }
        }

        // 반환값 : 그룹 크기
        return cnt;
    }

}