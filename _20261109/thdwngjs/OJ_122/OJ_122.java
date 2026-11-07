package _20261109.thdwngjs.OJ_122;

import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());

    List<List<Integer>> list = new ArrayList<>(N+1);
    for(int i = 0; i <= N; i++){
      list.add(new ArrayList<>());
    }

    while(--M >= 0){
      st = new StringTokenizer(br.readLine());

      int u = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken());

      list.get(v).add(u);
    }

    int[] dist = new int[N+1];
    Arrays.fill(dist, -1);
    dist[N] = 0;

    Queue<Integer> q = new ArrayDeque<>();
    q.offer(N);

    while(!q.isEmpty()){
      int u = q.poll();

      for(int v: list.get(u)){
        if(dist[v] != -1) continue;
        dist[v] = dist[u] + 1;
        q.offer(v);
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

