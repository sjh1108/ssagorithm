import java.io.*;
import java.util.*;

// BOJ_3665_줄 세우기

public class Main_3665_송주헌 {
    private static int N;

    private static int[] arr;
    private static boolean[][] graph;

    private static void swap(int a, int b){
        if(!graph[a][b]) {
            graph[a][b] = true;
            graph[b][a] = false;
            arr[a]--;
            arr[b]++;
        } else{
            graph[a][b] = false;
            graph[b][a] = true;
            arr[a]++;
            arr[b]--;
        }
    }

    private static StringBuilder topologicalSort() {
        Queue<Integer> q = new LinkedList<>();

        for (int i = 1; i <= N; i++) {
            if (arr[i] == 0) {
                q.offer(i);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= N; i++){
            if(q.isEmpty()){
                return new StringBuilder("IMPOSSIBLE");
            }

            if(q.size() > 1){
                return new StringBuilder("?");
            }

            int cur = q.poll();
            sb.append(cur + " ");

            for (int j = 1; j <= N; j++) {
                if (graph[cur][j]) {
                    graph[cur][j] = false;

                    if(--arr[j] == 0) {
                        q.offer(j);
                    }
                }
            }
        }

        return sb;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int T = Integer.parseInt(br.readLine());
        while(T-- > 0){
            N = Integer.parseInt(br.readLine());
            arr = new int[N+1];
            graph = new boolean[N+1][N+1];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i = 0; i < N; i++) {
                int x = Integer.parseInt(st.nextToken());
                arr[x] = i;

                for(int j = 1; j <= N; j++){
                    if(j != x && !graph[j][x]) {
                        graph[x][j] = true;
                    }
                }
            }

            int m = Integer.parseInt(br.readLine());
            while(m-- > 0){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                swap(a, b);
            }

            sb.append(topologicalSort()).append("\n");
        }


        System.out.println(sb);
    }
}