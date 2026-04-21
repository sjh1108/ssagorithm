package _20260420.thdwngjs.BOJ_13911;

import java.io.*;
import java.util.*;

// 다중 시작점 다익스트라 풀이
// 맥도날드들과 스타벅스들 각각을 시작점으로 다익스트라를 수행하여
// 각 정점까지의 최단 거리를 구한 뒤,
// 두 거리의 합이 최소인 집 위치를 찾는다.
class Main {
    static class Edge {
        int to;
        long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static ArrayList<ArrayList<Edge>> graph;
    static int V;
    static final long INF = Long.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken()); // 정점 수
        int E = Integer.parseInt(st.nextToken()); // 간선 수

        // 인접 리스트 그래프 생성
        graph = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        // 양방향 간선 입력
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            graph.get(start).add(new Edge(end, weight));
            graph.get(end).add(new Edge(start, weight));
        }

        // 맥도날드 정보 입력 (개수, 거리 제한, 위치 목록)
        st = new StringTokenizer(br.readLine());
        int mCnt = Integer.parseInt(st.nextToken());
        int mLimit = Integer.parseInt(st.nextToken());
        int[] mList = new int[mCnt];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < mCnt; i++) {
            mList[i] = Integer.parseInt(st.nextToken());
        }

        // 스타벅스 정보 입력 (개수, 거리 제한, 위치 목록)
        st = new StringTokenizer(br.readLine());
        int sCnt = Integer.parseInt(st.nextToken());
        int sLimit = Integer.parseInt(st.nextToken());
        int[] sList = new int[sCnt];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sCnt; i++) {
            sList[i] = Integer.parseInt(st.nextToken());
        }

        // 다중 시작점 다익스트라: 모든 맥도날드/스타벅스에서 동시에 출발
        long[] mMinDistance = bfs(mList);  // 각 정점까지 가장 가까운 맥도날드와의 거리
        long[] sMinDistance = bfs(sList);  // 각 정점까지 가장 가까운 스타벅스와의 거리

        long minSum = INF;

        // 모든 정점을 순회하며 조건을 만족하는 최적의 집 위치를 탐색
        for (int i = 1; i <= V; i++) {
            // 맥도날드/스타벅스 위치 자체는 제외 (거리 > 0)
            // 각각의 거리 제한도 확인
            if (mMinDistance[i] > 0 && sMinDistance[i] > 0 &&
                mMinDistance[i] <= mLimit && sMinDistance[i] <= sLimit) {
                minSum = Math.min(minSum, mMinDistance[i] + sMinDistance[i]);
            }
        }

        if (minSum == INF) {
            System.out.println(-1);
        } else {
            System.out.println(minSum);
        }
    }

    // 다중 시작점 다익스트라 (BFS 형태이지만 가중치 그래프에서 최단 거리 계산)
    static long[] bfs(int[] startNodes) {
        long[] dist = new long[V + 1];
        Arrays.fill(dist, INF);

        ArrayDeque<Edge> q = new ArrayDeque<>();

        // 모든 시작점을 거리 0으로 큐에 삽입
        for (int node : startNodes) {
            dist[node] = 0;
            q.offer(new Edge(node, 0));
        }

        while (!q.isEmpty()) {
            Edge current = q.poll();

            // 이미 더 짧은 경로로 방문된 경우 스킵
            if (current.weight > dist[current.to]) {
                continue;
            }

            // 인접 정점으로 거리 갱신 시도
            for (Edge next : graph.get(current.to)) {
                long newDist = current.weight + next.weight;

                if (newDist < dist[next.to]) {
                    dist[next.to] = newDist;
                    q.offer(new Edge(next.to, newDist));
                }
            }
        }
        return dist;
    }
}