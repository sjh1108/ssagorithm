import java.util.*;
import java.io.*;
public class Main {
  static ArrayList<Integer>[] town;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    int Q = Integer.parseInt(st.nextToken());

    // 중계탑 입력
    int[] towers = new int[M];
    StringTokenizer line = new StringTokenizer(br.readLine());
    for(int i = 0; i < M; i++){
      towers[i] = Integer.parseInt(line.nextToken());
    }

    // 오솔길 입력
    town = new ArrayList[N + 1];
    for(int i = 1; i <= N; i++){
      town[i] = new ArrayList<>();
    }
    
    for(int i = 0; i < N-1; i++){
      line = new StringTokenizer(br.readLine());
      int u = Integer.parseInt(line.nextToken());
      int v = Integer.parseInt(line.nextToken());
      town[u].add(v);
      town[v].add(u);
    }
    // dist[i] = i와 가장 가까운 중계탑 거리
    int[] dist = new int[N + 1];
    Arrays.fill(dist, -1);

    // 큐에 넣어서 동시에 bfs 시작
    Queue<Integer> q = new ArrayDeque<>();
    for(int tower : towers){
      q.offer(tower);
      dist[tower] = 0; // 자신이 중계기 이므로 중계기와의 거리는 0
    }

    while(!q.isEmpty()){
      int cur = q.poll();

      for(int next : town[cur]){
        if(dist[next] != -1) continue;
        dist[next] = dist[cur] + 1;
        q.offer(next);
      }
    }
    // 각 거리별 개수
    int[] count = new int[N]; // 최대 거리 N - 1 까지 이므로
    for(int i = 1; i <= N; i++){
      count[dist[i]]++;
    }

    // 사각지대 계산
    int[] blind = new int[N + 2];
    for(int i = N - 1; i >= 0; i--){ // count 크기는 [N-1] 까지, 
      blind[i] = blind[i + 1] + count[i];
    }

    // 출력후보 K, 결과 출력
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < Q; i++){
      int K = Integer.parseInt(br.readLine());
      sb.append(blind[K + 1]).append("\n"); // K = N일 때 blind[N + 1]될수 있음을 고려 -> blind 크기 + 1
    }
    System.out.println(sb);
  }
}
