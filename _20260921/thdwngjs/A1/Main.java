package _20260921.thdwngjs.A1;

import java.io.*;
import java.util.*;

public class Main {
  private static int N, M;
  private static long T;

  private static int cnt = 0;

  private static int[] arr;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    T = Long.parseLong(st.nextToken());

    st = new StringTokenizer(br.readLine());
    arr = new int[N];
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }

    for(int i = 0; i <= N - M; i++){
      combine(i, 1, arr[i]);
    }

    System.out.println(cnt);
  }

  private static void combine(int idx, int dep, long sum){
    // System.out.println(dep + " " + sum);
    if(sum > T) return;
    if(dep == M) {
      if(sum == T){
        cnt++;
      }
      return;
    }

    for(int i = idx+1; i <= N - (M - dep); i++){
      combine(i, dep+1, sum + arr[i]);
    }
  }
}
