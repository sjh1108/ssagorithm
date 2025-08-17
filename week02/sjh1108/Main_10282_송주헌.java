import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 해킹
public class Main_10282_송주헌 {
    private static int n;
    private static int c;

    private static final int INF = 1_000_000_000;

    private static int[] dist;
    private static List<List<Node>> graph;

    static class Node{
        int idx;
        int c;

        Node(int idx, int s){
            this.idx = idx;
            this.c = s;
        }
    }
    
    private static void dijkstra(){
        PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(o -> o.c));
		
        dist[c] = 0;
        boolean[] visited = new boolean[n+1];
        q.offer(new Node(c, 0));
		
        while (!q.isEmpty()) {
            int cur = q.poll().idx;
			
            if(visited[cur]) continue;

            visited[cur] = true;
            
            for (Node next : graph.get(cur)) {
                if (dist[next.idx] > dist[cur] + next.c) {
                    dist[next.idx] = dist[cur] + next.c;
                    q.offer(new Node(next.idx, dist[next.idx]));
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int t = 0; t < T; t++){
            StringTokenizer st = new StringTokenizer(br.readLine());

            n = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            dist = new int[n+1];
            graph = new ArrayList<>(n+1);
            for(int i = 0; i < n+1; i++){
                dist[i] = INF;
                graph.add(new ArrayList<>());
            }
            
            while(d-- > 0){
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int s = Integer.parseInt(st.nextToken());

                graph.get(b).add(new Node(a, s));
            }

            dijkstra();

            int cnt = 0;
            int max = 0;

            for(int i = 1; i < n+1; i++){
                int cur = dist[i];

                if(cur != INF){
                    max= Math.max(max, cur);
                    cnt++;
                }
            }

            sb.append(cnt + " " + max + "\n");
        }

        System.out.println(sb);
    }
}
