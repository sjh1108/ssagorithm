import java.util.*;
import java.io.*;

public class 상위_기지_판정 {
    static int[] in;
    static int[] out;
    static ArrayList<Integer>[] graph;
    static int time = 0;
    public static void dfs(int cur, int parent){
        in[cur] = ++time;

        for(int next : graph[cur]){
            if(next == parent){ // 탐색 끝(리프노드)
                continue;
            }
            dfs(next,cur);
        }
        out[cur] = ++time; // 노드 탐색 끝나고 돌아오면서 시간 기록)
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];
        for(int i = 0; i <= N; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i = 0; i < N - 1; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph[u].add(v);
            graph[v].add(u);
        }

        in = new int[N + 1];
        out = new int[N + 1];

        dfs(1, 0);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Q; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            // 상위 기지 in ~ out은 하위 기지 in ~ out을 포함함
            if(in[u] < in[v] && out[u] > out[v]){
                sb.append("YES").append("\n");
            }
            else{
                sb.append("NO").append("\n");
            }
        }
        System.out.println(sb);
    }
}
