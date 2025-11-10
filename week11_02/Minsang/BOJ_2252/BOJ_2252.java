package week11_02.Minsang.BOJ_2252;

import java.io.*;
import java.util.*;

class BOJ_2252 {
    static int N, M;
    static ArrayList<Integer>[] graph;
    static boolean[] visited;
    static ArrayList<Integer> result;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        graph = new ArrayList[N + 1];
        visited = new boolean[N + 1];
        result = new ArrayList<>();
        
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
        }
        
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
        
        // 위상 정렬 결과를 역순으로 출력
        Collections.reverse(result);
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
    
    static void dfs(int node) {
        visited[node] = true;
        
        // 현재 노드와 연결된 모든 노드 방문
        for (int next : graph[node]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
        
        // 모든 인접 노드를 방문한 후에 현재 노드를 결과 리스트에 추가
        result.add(node);
    }
}