package algo2025_11_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1238_파티Re {
    static class Node implements Comparable<Node> {
        int to, cost;
        Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    static int N, M, X;
    static List<Node>[] graph, rGraph;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];
        rGraph = new ArrayList[N + 1];
        
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
            rGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            // 인접그래프 a->b로 한개 만들고 역방향으로 b->a로 만들어서
            // 하나의 다익으로 그래프마다 한개씩 돌려주기 역방향이라 어짜피 t는 같은값임
            graph[a].add(new Node(b, t));
            rGraph[b].add(new Node(a, t)); 
        }

        int[] straight = dijkstra(graph, X);
        int[] rStraight = dijkstra(rGraph, X);

        int maxTime = 0;
        for (int i = 1; i <= N; i++) {
            if (straight[i] == INF || rStraight[i] == INF) continue;
            // 최대값
            maxTime = Math.max(maxTime, straight[i] + rStraight[i]);
        }

        System.out.println(maxTime);
    }
    // 그래프랑 출발지 넣고 돌리기
    static int[] dijkstra(List<Node>[] graph, int start) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF); // 형주티칭 매서드 1차원만 적용 2차원은 for문안에 크게 한줄씩
        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.offer(new Node(start, 0));
        dist[start] = 0; // 파티가 열리는 곳이 출발지로 값은 0

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.cost > dist[cur.to]) continue;
            // 각 정점마다 갈수 있는 간선수 테케 예시 1->2, 1->3, 1->3
            // graph[cur.to].size() => 3
            // 간선마다 값 계산 인접그래프 어렵다
            for (int i = 0; i < graph[cur.to].size(); i++) {
            	//아래는 일반 다익이랑 똑같은 구조로
                Node next = graph[cur.to].get(i);
                int newCost = dist[cur.to] + next.cost;
                if (newCost < dist[next.to]) {
                    dist[next.to] = newCost;
                    pq.offer(new Node(next.to, newCost));
                }
            }
        }
        return dist;
    }
}