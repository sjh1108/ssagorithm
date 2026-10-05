package _20260921.thdwngjs.A3;

import java.io.*;
import java.util.*;

public class Main {
  private static int N, K;
  private static long min, low;

  private static int[] arr;
  private static long[] pack;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());

    arr = new int[N];

    long sum = 0L;
    st = new StringTokenizer(br.readLine());
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
      sum += arr[i];
    }
    
    pack = new long[K];
    Arrays.sort(arr);
    for(int i = 0, j = N-1; i < j; i++, j--){
      int tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
    }

    low = Math.max(arr[0], (sum + K - 1) / K);
    min = sum;
    
    dfs(0);
    System.out.println(min);
  }

  private static void dfs(int idx){
    long cur = arr[idx];
    if(idx == N-1){

      long m = pack[0];
      long max = pack[0];
      for(int i = 1; i < K; i++){
        if(pack[i] < m) m = pack[i];
        max = Math.max(max, pack[i]);
      }

      max = Math.max(m + cur, max);
      if(max < min) min = max;

      return;
    }

    long[] copy = pack.clone();
    Arrays.sort(copy);
    long prev = -1;
    for(int i = 0; i < K; i++){
      long tmp = copy[i];

      if(tmp == prev) continue;
      prev = tmp;

      long sum = tmp + cur;
      if(sum >= min) break;
      
      int j = 0;
      while(pack[j] != tmp) j++;

      pack[j] = sum;
      dfs(idx+1);
      pack[j] = tmp;

      if(min == low) return;
    }
  }
}
