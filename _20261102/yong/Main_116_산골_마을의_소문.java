import java.util.*;
import java.io.*;
/*
마을 N개와 오솔길 N - 1개, S번 마을 스타트 > 트리, bfs
인접리스트 만들고 S에서 퍼뜨리면서 마을 개수 카운트
*/
public class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int S = Integer.parseInt(st.nextToken());
    
    // 인접 리스트
    List<Integer>[] graph = new ArrayList[N + 1];
    for(int i = 1; i <= N; i++){
      graph[i] = new ArrayList<>();
    }

    for(int i = 0; i < N - 1;i++){
      StringTokenizer line = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(line.nextToken());
      int b = Integer.parseInt(line.nextToken());
      graph[a].add(b);
      graph[b].add(a);
    }

    // 모든 마을이 소문을 듣게 되는 시각
    int T = 0;
    // 시각별 소문을 처음 듣는 마을의 수
    int[] count = new int[N];
    
    // 각 마을 방문 시각
    int[] timeStamp = new int[N+1];
    Arrays.fill(timeStamp, -1);

    Queue<Integer> q = new ArrayDeque<>();
    timeStamp[S] = 0;
    count[timeStamp[S]]++;

    q.offer(S);
    while(!q.isEmpty()){
      int cur = q.poll();

      // 연결된 마을 방문
      for(int next : graph[cur]){
        // 이미 방문했다면 패스
        if(timeStamp[next] != -1) continue;
        // 소문 듣게되는 시각 = 현재 마을에 소문 퍼진 시각 + 1
        timeStamp[next] = timeStamp[cur] + 1;
        // T 갱신
        T = Math.max(timeStamp[next],T);
        // 해당 시각에 소문이 퍼진 마을 수++
        count[timeStamp[next]]++;
        q.offer(next);
      }
    }
    System.out.println(T);
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i <= T; i++){
      sb.append(count[i]).append(" ");
    }
    System.out.println(sb.toString());
  }
  
}
