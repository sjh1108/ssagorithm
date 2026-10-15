package _20261019.thdwngjs.OJ_101;

import java.io.*;
import java.util.*;

class Main {
  private static int L;
  private static int cnt;

  private static int[] arr;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    L = Integer.parseInt(br.readLine());
    StringTokenizer st = new StringTokenizer(br.readLine());

    arr = new int[6];
    for(int i = 1; i < 6; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }

    dfs(L);

    System.out.println(cnt);
  }

  private static void dfs(int len){
    if(len == 0){
      cnt++;
      return;
    }

    for(int i = 1; i < 6; i++){
      if(arr[i] > 0 && len - i >= 0){
        arr[i]--;
        dfs(len - i);
        arr[i]++;
      }
    }
  }
}
