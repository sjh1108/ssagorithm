package week01_04.sjh1108.BOJ_1167;

import java.util.*;
import java.io.*;

class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, M;

    static class Node {
        int x, y;
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static ArrayList<Node>[] list;
    static boolean[] visited;
    static int max;
    
    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        list = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            while(true){
                int b = Integer.parseInt(st.nextToken());
                if(b == -1) break;
                int c = Integer.parseInt(st.nextToken());
                list[a].add(new Node(b, c));
            }
        }

        visited = new boolean[N + 1];
        dfs(1, 0);

        visited = new boolean[N + 1];
        dfs(M, 0);
        
        System.out.println(max);
    }

    static void dfs(int x, int len){
        if(len > max){
            max = len;
            M = x;
        }
        visited[x] = true;

        for(int i = 0; i < list[x].size(); i++){
            Node node = list[x].get(i);
            if(!visited[node.x]){
                dfs(node.x, len + node.y);
                visited[node.x] = true;
            }
        }
    }
}
