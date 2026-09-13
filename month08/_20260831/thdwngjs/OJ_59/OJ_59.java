import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
    int n = Integer.parseInt(br.readLine());

    long[] w = new long[n + 1];
    long total = 0;
    StringTokenizer st = new StringTokenizer(br.readLine());
    for (int i = 1; i <= n; i++) {
      w[i] = Long.parseLong(st.nextToken());
      total += w[i];
    }

    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      adj.add(new ArrayList<>());
    }

    for (int i = 0; i < n - 1; i++) {
      st = new StringTokenizer(br.readLine());

      int u = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken());

      adj.get(u).add(v);
      adj.get(v).add(u);
    }

    // 1번을 루트로 반복적 DFS (N=100,000이라 재귀는 스택 오버플로 위험)
    int[] parent = new int[n + 1];
    int[] order = new int[n];
    int[] stack = new int[n];
    boolean[] visited = new boolean[n + 1];
    int sp = 0, cnt = 0;

    stack[sp++] = 1;
    visited[1] = true;
    while (sp > 0) {
      int cur = stack[--sp];
      order[cnt++] = cur;
      for (int next : adj.get(cur)) {
        if (!visited[next]) {
          visited[next] = true;
          parent[next] = cur;
          stack[sp++] = next;
        }
      }
    }

    // 방문 역순으로 서브트리 합 누적
    long[] sub = new long[n + 1];
    for (int i = 1; i <= n; i++) {
      sub[i] = w[i];
    }
    for (int i = cnt - 1; i >= 1; i--) {
      int cur = order[i];
      sub[parent[cur]] += sub[cur];
    }

    // f(v) = max(자식 서브트리 합들, total - sub[v])
    long[] f = new long[n + 1];
    for (int v = 1; v <= n; v++) {
      f[v] = total - sub[v];
    }
    for (int v = 2; v <= n; v++) {
      int p = parent[v];
      if (sub[v] > f[p]) {
        f[p] = sub[v];
      }
    }

    long best = Long.MAX_VALUE;
    for (int v = 1; v <= n; v++) {
        best = Math.min(best, f[v]);
    }

    StringBuilder sb = new StringBuilder();
    sb.append(best).append('\n');
    boolean first = true;
    for (int v = 1; v <= n; v++) {
        if (f[v] == best) {
            if (!first) sb.append(' ');
            sb.append(v);
            first = false;
        }
    }
    sb.append('\n');
    System.out.print(sb);
  }
}
