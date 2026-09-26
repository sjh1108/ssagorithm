package _20260928.thdwngjs.A2;

import java.io.*;
import java.util.*;

public class Main {
  private static int N;
  private static long cnt;

  private static int[] arr, tmp;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());

    arr = new int[N];
    tmp = new int[N];
    
    StringTokenizer st = new StringTokenizer(br.readLine());
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }

    cnt = 0L;
    sort(0, N-1);

    System.out.println(cnt);
  }

  private static void sort(int left, int right){
    if(left >= right) return;

    int mid = (left + right) / 2;
    sort(left, mid);
    sort(mid + 1, right);

    merge(left, mid, right);
  }

  private static void merge(int left, int mid, int right){
    int i = left;
    int j = mid + 1;
    int k = left;

    while (i <= mid && j <= right) {
      if (arr[i] <= arr[j]) {
        tmp[k++] = arr[i++];
      } else {
        cnt += (mid - i + 1);
        tmp[k++] = arr[j++];
      }
    }

    while (i <= mid) tmp[k++] = arr[i++];
    while (j <= right) tmp[k++] = arr[j++];

    for (int idx = left; idx <= right; idx++) {
      arr[idx] = tmp[idx];
    }
  }
}
