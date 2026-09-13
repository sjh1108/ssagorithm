import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    // 섬의수 N 항로의 수 M 보급항의 수 P
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    int P = Integer.parseInt(st.nextToken());

    List<Integer>[] adj = new ArrayList[N+1];
    for (int i = 0; i <= N; i++) {
      adj[i] = new ArrayList<>();
    }

    // 거리 배열: -1 미방문
    int[] dist = new int[N+1];
    Arrays.fill(dist, -1);

    Queue<Integer> q = new ArrayDeque<>();
    
    // 보급항 입력
    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < P; i++) {
      int s = Integer.parseInt(st.nextToken());
      dist[s] = 0;
      q.offer(s);
      }

    // 항로 입력 = 양방향
    for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
            }
    
    // BFS
    while(!q.isEmpty()) {
      int cur = q.poll();
      for (int next : adj[cur]) {
        if(dist[next] != -1) continue; // 이미 가까운 거리로 확정됨
        dist[next] = dist[cur] + 1;
        q.offer(next);
      }
    }

    // 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(dist[i]);
            if (i < N) sb.append(' ');
        }
        System.out.println(sb);

  }
}
