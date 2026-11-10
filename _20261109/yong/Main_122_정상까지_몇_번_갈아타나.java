import java.util.*;
import java.io.*;

public class Main_122_정상까지_몇_번_갈아타나 {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    
    // 인접 리스트
    ArrayList<Integer>[] graph = new ArrayList[N + 1];
    for(int i = 0; i <= N; i++){
      graph[i] = new ArrayList<>();
    }

    for(int i = 0; i < M; i++){
      st = new StringTokenizer(br.readLine());
      int u = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken());
      graph[v].add(u); // 역방향
    }
    Queue<Integer> q = new ArrayDeque<>();
    q.offer(N);
    int[] dist = new int[N + 1];
    Arrays.fill(dist, -1);
    dist[N] = 0;

    while(!q.isEmpty()){
      int now = q.poll();
      for(int next: graph[now]){
        if(dist[next] != -1) continue;
        dist[next] = dist[now] + 1;
        q.offer(next);
      }
    }
    
    StringBuilder sb = new StringBuilder();
    for(int i = 1; i <= N; i++){
      sb.append(dist[i]);
      sb.append(" ");
    }
    System.out.println(sb);

  }
}
