package _20261012.thdwngjs.OJ_92;

import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    
    int[][] arr = new int[N][2];

    StringTokenizer st;
    for(int i = 0; i < N; i++){
      st = new StringTokenizer(br.readLine());
      arr[i][0] = Integer.parseInt(st.nextToken());
      arr[i][1] = Integer.parseInt(st.nextToken());
    }

    long min = Long.MAX_VALUE;
    for(int i = 0; i < N-1; i++){
      for(int j = i+1; j < N; j++){
        long x = arr[i][0] - arr[j][0];
        long y = arr[i][1] - arr[j][1];

        long dist = (x * x) + (y * y);

        min = Math.min(dist, min);
      }
    }

    System.out.println(min);
  }
}
