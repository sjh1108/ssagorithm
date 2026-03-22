package _20260323.thdwngjs.BOJ_1119;

import java.io.*;
import java.util.*;

class Main {
    private static int N;

    private static boolean[] visited;
    private static boolean[][] linked;
    private static List<int[]> edgeList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        if(N == 1){
            System.out.println(0);
            return;
        }

        linked = new boolean[N][N];

        for(int i = 0; i < N; i++){
            char[] input = br.readLine().toCharArray();
           
            for(int j = 0; j <= i; j++){
                linked[i][j] = linked[j][i] = input[j] == 'Y';
            }
        }
        
        visited = new boolean[N];

        for(int i = 0; i < N; i++){
            if(!visited[i]) dfs(i);
        }

        boolean enable = true;
        int cnt = 0;

        for(int[] comp: edgeList){
            int nodes = comp[0];
            int edges = comp[1];
            
            if(nodes == 1){
                enable = false;
                break;
            }

            cnt += edges - nodes + 1;
        }

        if(enable && cnt >= edgeList.size() - 1){
            System.out.println(edgeList.size() - 1);
        } else{
            System.out.println(-1);
        }
    }

    private static void dfs(int start){
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        visited[start] = true;

        int nodes = 0;
        int edges = 0;

        while(!stack.isEmpty()){
            int cur = stack.pop();
            nodes++;

            for(int i  = 0; i < N; i++){
                if(!linked[cur][i]) continue;

                edges++;
                
                if(!visited[i]){
                    visited[i] = true;
                    stack.push(i);
                }
            }
        }

        edgeList.add(new int[]{nodes, edges / 2});
    }
}
