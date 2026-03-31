/**
 * BOJ 2585 - 경비행기 (골드 2)
 *
 * [풀이] 이분탐색 + BFS
 * 출발지 (0,0)에서 목적지 (10000,10000)까지, 중간 급유소를 최대 K번 경유하여
 * 도달할 수 있는 최소 연료탱크 용량을 구한다.
 *
 * - 이분탐색: 연료탱크 용량(L)을 이분탐색
 * - BFS: 해당 용량으로 K번 이하 경유하여 목적지에 도달 가능한지 확인
 *   (연료 = ceil(거리 / 10), 해당 연료가 L 이하인 간선만 사용)
 */
package _20260330.thdwngjs.BOJ_2585;

import java.io.*;
import java.util.*;

class Main {
    static class Node{
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static int N, K; // N: 급유소 수, K: 최대 경유 횟수

    private static double[][] dist; // 모든 노드 간 거리

    private static Node[] nodes;               // 0: 출발, 1~N: 급유소, N+1: 목적지
    private static List<List<Integer>> list;   // 인접 리스트

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); K = Integer.parseInt(st.nextToken());

        nodes = new Node[N+2];

        nodes[0] = new Node(0, 0); // 출발지
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            nodes[i] = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        nodes[N+1] = new Node(10000, 10000); // 목적지

        // 모든 노드 쌍 간 거리 계산 및 인접 리스트 구성
        dist = new double[N+2][N+2];
        list = new ArrayList<>();
        for(int i = 0; i <= N + 1; i++){
            list.add(new ArrayList<>());
        }

        for(int i = 1; i <= N + 1; i++){
            for(int j = 0; j < i; j++){
                dist[i][j] = dist[j][i] = getDistance(nodes[i], nodes[j]);

                list.get(i).add(j);
                list.get(j).add(i);
            }
        }

        // 이분탐색: 최소 연료탱크 용량 탐색
        int l = 0, r = 22000, m, ans = 22000;

        while(l <= r){
            m = (l + r) / 2;

            if(checkPossible(m)){
                ans = m; r = m - 1;
            } else{
                l = m + 1;
            }
        }

        System.out.println(ans);
    }

    /**
     * BFS로 연료탱크 용량 l 이하로 K번 이하 경유하여 목적지 도달 가능 여부 확인
     */
    private static boolean checkPossible(int l){
        Queue<Integer> q = new ArrayDeque<>();

        int[] visited = new int[N+2]; // 경유 횟수 기록
        Arrays.fill(visited, -1);

        q.add(0); visited[0] = 0;

        while(!q.isEmpty()){
            int cur = q.poll();

            for(int nxt: list.get(cur)){
                if(visited[nxt] != -1) continue;

                double d = dist[cur][nxt];

                int fuel = (int)Math.ceil(d / 10); // 필요 연료 계산 (거리 10당 1리터)

                if(fuel <= l){
                    q.add(nxt);

                    visited[nxt] = visited[cur] + 1;

                    // 목적지 도달 시, 경유 횟수가 K 이하인지 확인 (출발/도착 제외)
                    if(nxt == N+1 && K >= visited[nxt] - 1){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static double getDistance(Node a, Node b){
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

}