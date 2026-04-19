package _20260420.thdwngjs.BOJ_13911;

import java.io.*;
import java.util.*;

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

        V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            graph.get(start).add(new Edge(end, weight));
            graph.get(end).add(new Edge(start, weight));
        }

        st = new StringTokenizer(br.readLine());
        int mCnt = Integer.parseInt(st.nextToken());
        int mLimit = Integer.parseInt(st.nextToken());
        int[] mList = new int[mCnt];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < mCnt; i++) {
            mList[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int sCnt = Integer.parseInt(st.nextToken());
        int sLimit = Integer.parseInt(st.nextToken());
        int[] sList = new int[sCnt];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sCnt; i++) {
            sList[i] = Integer.parseInt(st.nextToken());
        }

        long[] mMinDistance = bfs(mList);

        long[] sMinDistance = bfs(sList);

        long minSum = INF;

        for (int i = 1; i <= V; i++) {
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

    static long[] bfs(int[] startNodes) {
        long[] dist = new long[V + 1];
        Arrays.fill(dist, INF);

        ArrayDeque<Edge> q = new ArrayDeque<>();

        for (int node : startNodes) {
            dist[node] = 0;
            q.offer(new Edge(node, 0));
        }

        while (!q.isEmpty()) {
            Edge current = q.poll();
            
            if (current.weight > dist[current.to]) {
                continue;
            }

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