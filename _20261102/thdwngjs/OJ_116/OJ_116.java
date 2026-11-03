import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int N = Integer.parseInt(st.nextToken());
    int S = Integer.parseInt(st.nextToken());

    boolean[] check = new boolean[N+1];

    List<List<Integer>> roadList = new ArrayList<>();
    for(int i = 0; i <= N; i++){
      roadList.add(new ArrayList<>());
    }

    for(int i = 0; i < N - 1; i++){
      st = new StringTokenizer(br.readLine());

      int u = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken());

      roadList.get(u).add(v);
      roadList.get(v).add(u);
    }

    int cnt = 1;
    int[] arr = new int[N+1];
    Queue<Integer> q = new ArrayDeque<>();

    q.add(S);
    check[S] = true;
    arr[0] = 1;

    while(!q.isEmpty()){
      
      int size = q.size();
      while(size-- > 0){
        int cur = q.poll();

        for(int nxt: roadList.get(cur)){
          if(check[nxt]) continue;
          check[nxt] = true;
          q.add(nxt);

          ++arr[cnt];
        }
      }

      ++cnt;
    }
    cnt-=2;

    StringBuilder sb = new StringBuilder();
    sb.append(cnt + "\n");
    for(int i = 0; i <= cnt; i++){
      sb.append(arr[i] + " ");
    }
    
    System.out.println(sb);
  }
}
