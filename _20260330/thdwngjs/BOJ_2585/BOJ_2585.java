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

    private static int N, K;

    private static double[][] dist;
    
    private static Node[] nodes;
    private static List<List<Integer>> list;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); K = Integer.parseInt(st.nextToken());
        
        nodes = new Node[N+2];
        
        nodes[0] = new Node(0, 0);
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            nodes[i] = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        nodes[N+1] = new Node(10000, 10000);
        
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

    private static boolean checkPossible(int l){
        Queue<Integer> q = new ArrayDeque<>();

        int[] visited = new int[N+2];
        Arrays.fill(visited, -1);

        q.add(0); visited[0] = 0;

        while(!q.isEmpty()){
            int cur = q.poll();

            for(int nxt: list.get(cur)){
                if(visited[nxt] != -1) continue;

                double d = dist[cur][nxt];

                int fuel = (int)Math.ceil(d / 10);

                if(fuel <= l){
                    q.add(nxt);

                    visited[nxt] = visited[cur] + 1;

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