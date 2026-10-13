package _20261012.thdwngjs.OJ_97;

import java.io.*;
import java.util.*;

class Main {
  private static int N, nl, nr, cnt;
  private static long W;

  private static long[][] arr;
  private static long[] hw, hv;
  
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    W = Long.parseLong(st.nextToken());

    nl = N / 2;
    nr = N - nl;

    arr = new long[nl][2];
    for(int i = 0; i < nl; i++){
      st = new StringTokenizer(br.readLine());
      arr[i][0] = Long.parseLong(st.nextToken());
      arr[i][1] = Long.parseLong(st.nextToken());
    }
    hw = new long[1 << nl];
    hv = new long[1 << nl];
    cnt = 0;
    dfs(0, nl, 0L, 0L);
    long[] lw = hw, lv = hv;


    arr = new long[nr][2];
    for(int i = 0; i < nr; i++){
      st = new StringTokenizer(br.readLine());
      arr[i][0] = Long.parseLong(st.nextToken());
      arr[i][1] = Long.parseLong(st.nextToken());
    }
    cnt = 0;
    hw = new long[1 << nr];
    hv = new long[1 << nr];
    dfs(0, nr, 0L, 0L);
    long[] rw = hw, rv = hv;
    Integer[] order = new Integer[cnt];
    for(int i = 0; i < cnt; i++){
      order[i] = i;
    }
    Arrays.sort(order, (o1, o2) -> Long.compare(rw[o1], rw[o2]));

    long[] max = new long[cnt];
    max[0] = rv[order[0]];
    for(int i = 1; i < cnt; i++){
      max[i] = Math.max(max[i-1], rv[order[i]]);
    }

    long ans = 0L;
    int len = lw.length;
    for(int i = 0; i < len; i++){
      if(lw[i] > W) continue;

      long cap = W - lw[i];
      
      int lo = 0, hi = cnt-1;

      while(lo < hi){
        int mid = (lo + hi + 1) >> 1;
        if(rw[order[mid]] <= cap) lo = mid;
        else hi = mid -1;
      }

      ans = Math.max(ans, lv[i] + max[lo]);
    }
    System.out.println(ans);
  }

  private static void dfs(int idx, int end, long sw, long sv){
    if(idx == end){
      hw[cnt] = sw;
      hv[cnt] = sv;

      cnt++;
      return;
    }

    dfs(idx+1, end, sw, sv);
    dfs(idx+1, end, sw + arr[idx][0], sv + arr[idx][1]);
  }
}
