package _20260928.thdwngjs.A3;

import java.io.*;
import java.util.*;

public class Main {
  private static int N;
  private static int[] arr, idx, buf;
  private static long[] L, R;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());
    StringTokenizer st = new StringTokenizer(br.readLine());

    arr = new int[N];
    buf = new int[N];
    idx = new int[N];
    L = new long[N];
    R = new long[N];

    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
      idx[i] = i;
    }

    sort(0, N);

    long ans = 0L;
    for(int i = 0; i < N; i++){
      ans += L[i] * R[i];
    }

    System.out.println(ans);
  }

  private static void sort(int lo, int hi){
    if(hi - lo <= 1) return;

    int mid = (lo + hi) / 2;
    sort(lo, mid);
    sort(mid, hi);

    merge(lo, mid, hi);
  }

  private static void merge(int lo, int mid, int hi){
    int i = lo;
    int j = mid;
    int k = lo;
    int tmp = 0;

    while(i < mid && j < hi){
      int ii = idx[i], jj = idx[j];
      if(arr[jj] < arr[ii]){
        L[jj] += mid - i;
        buf[k++] = jj;
        j++;
        tmp++;
      } else {
        R[ii] += tmp;
        buf[k++] = ii;
        i++;
      }
    }

    while(i < mid){
      R[idx[i]] += tmp;
      buf[k++] = idx[i++];
    }
    while(j < hi){
      buf[k++] = idx[j++];
    }

    System.arraycopy(buf, lo, idx, lo, hi - lo);
  }
}