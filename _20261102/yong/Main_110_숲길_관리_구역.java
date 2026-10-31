import java.util.*;
import java.io.*;
public class Main {
  static int N;
  static int[] cost;
  static long[] costSum;
  static int [] parent;
  static ArrayList<Integer>[] tree;
  // 자식 서브트리의 관리비 합을 받아현재 노드에 누적
  public static long dfs(int cur, int par){
    // 상위 쉼터 기록
    parent[cur] = par;
    // 현재 쉼터 관리비로 시작
    costSum[cur] = cost[cur];
    // 연결된 하위 쉼터 탐색
    for(int next : tree[cur]){
      // 상위로 다시 올라가는거 방지
      if(next == par) continue;
      // 내려갔다가 서브트리의 관리비 합을 반환받아 누적
      // dfs(2) = cost[2] + dfs(4) = dfs(5)
      costSum[cur] += dfs(next, cur);
    }
    // 현재 쉼터를 루트로하는 관리구역의 총 관리비 반환
    // 서브트리 합을 반환
    return costSum[cur];
    
  }
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());
    
    cost = new int[N + 1];
    costSum = new long[N + 1];
    parent = new int[N + 1];

    StringTokenizer st = new StringTokenizer(br.readLine());
    for(int i = 1; i <= N; i++){
      cost[i] = Integer.parseInt(st.nextToken());
    }

    tree = new ArrayList[N + 1];
    for(int i = 1; i <= N; i++){
      tree[i] = new ArrayList<>();
    }

    for(int i = 0; i < N-1; i++){
      StringTokenizer line = new StringTokenizer(br.readLine());
      int u = Integer.parseInt(line.nextToken());
      int v = Integer.parseInt(line.nextToken());
      tree[u].add(v);
      tree[v].add(u);
    }
    dfs(1, 0);
      StringBuilder sb = new StringBuilder();
    for(int i = 1; i <= N; i++){
      sb.append(parent[i]).append(" ").append(costSum[i]).append("\n");
    }
    System.out.println(sb);
  }

}
