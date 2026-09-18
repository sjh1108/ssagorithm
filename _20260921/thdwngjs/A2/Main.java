package _20260921.thdwngjs.A2;

import java.io.*;
import java.util.*;

public class Main {
  private static int N, C;
  private static int min;
  private static long total;

  private static int[] arr;
  private static long[] pack, sum;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());

    arr = new int[N];
    pack = new long[N];
    st = new StringTokenizer(br.readLine());
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }

    Arrays.sort(arr);
    for(int i = 0, j = N-1; i < j; i++, j--){
      int tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
    }

    sum = new long[N+1];
    for(int i = N-1; i >= 0; i--){
      sum[i] = sum[i+1] + (long)arr[i];
    }
    total = sum[0];
    
    min = N;
    solve(0, 0);

    System.out.println(min);
  }

  private static void solve(int i, int cnt){
    if(i == N){
      min = cnt;
      return;
    }

    long free = (long)cnt * C - (total - sum[i]);
    long need = sum[i] - free;
    if(need > 0 && cnt + (need + C - 1) / C >= min) return;

    long cur = arr[i];
    for(int j = 0; j < cnt; j++){
      boolean dup = false;
      for(int t = 0; t < j; t++){
        if(pack[t] == pack[j]){
          dup = true;
          break;
        }
      }
      
      if(dup) continue;

      if(pack[j] + cur <= C){
        pack[j] += cur;
        solve(i+1, cnt);
        pack[j] -= cur;
      }
    }

    if(cnt+1 < min){
      pack[cnt] = cur;
      solve(i+1, cnt+1);
      pack[cnt] = 0;
    }
  }
}
