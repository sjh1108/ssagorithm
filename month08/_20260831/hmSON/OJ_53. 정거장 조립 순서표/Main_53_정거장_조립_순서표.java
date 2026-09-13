import java.util.*;
import java.io.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 위상 정렬, 그리디, 우선순위 큐
     * 조립 공정 사이에 선행 관계가 존재하므로 이를 준수하여 조립 순서표를 출력해야 함
     * 사전순으로 가장 앞서는 조립 순서표를 출력해야 함
     * 올바른 조립 순서표 출력이 불가능하면 -1 출력
     */

    static int v; // 공정 수
    static int[] prev; // 각 공정별 선행 공정 수(0이면 즉시 진행 가능)
    static StringBuilder sb = new StringBuilder(); // 조립 순서표 기록
    static List<Integer>[] graph; // 인접 리스트

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken()); // 공정 관계 수

        prev = new int[v];
        graph = new ArrayList[v];
        for(int i=0; i<v; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;

            // 두 공정 간 선행 관계 기록
            graph[v1].add(v2); // a -> b 인접 리스트 등록
            prev[v2]++; // b의 선행 공정 수 카운트
        }

        // 위상 정렬에서 사이클 없으면 조립 순서표 출력, 사이클 발생하면 -1 출력
        System.out.println(topologicalSort() ? sb : -1);
    }

    // 위상 정렬 메서드
    // 위상 정렬 진행 후 사이클 발생 여부 반환
    static boolean topologicalSort() {
        // 진행 공정 수. 정상적으로 정렬이 끝났다면 cnt == v 조건이 성립해야 함
        int cnt = 0;
        // 우선순위 큐. 공정 순서 사전순 정렬을 위함
        Queue<Integer> q = new PriorityQueue<>();
        // 선행 공정이 없는 공정을 큐에 등록
        for(int i=0; i<v; i++) {
            if(prev[i] == 0) q.add(i);
        }

        while(!q.isEmpty()) {
            int cur = q.poll();
            sb.append(cur+1).append(" "); // 조립 순서 등록
            cnt++; // 진행 공정 수 카운트

            for(int next : graph[cur]) {
                prev[next]--; // 다음 공정의 선행 공정 수 감소
                if(prev[next] == 0) q.add(next); // 선행 공정 수가 0이 된 공정을 큐에 등록
            }
        }

        // cnt == v라면 모든 공정을 진행했음.
        // 아니라면 사이클 발생으로 인해 모든 공정을 진행하지 못했음.
        return cnt == v;
    }

}
