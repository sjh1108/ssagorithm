package _20261116.thdwngjs.OJ_125;

import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());

    int cnt = 0;
    st = new StringTokenizer(br.readLine());
    int[] arr = new int[N];
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
      if(arr[i] == K) cnt++;

      for(int j = i-1; j >= 0; j--){
        arr[j] ^= arr[i];
        if(arr[j] == K) cnt++;
      }
    }

    System.out.println(cnt);
  }
}
