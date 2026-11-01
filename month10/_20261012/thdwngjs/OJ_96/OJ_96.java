package _20261012.thdwngjs.OJ_96;

import java.io.*;
import java.util.*;

class Main {
  private static int N, M;
  private static long B;

  private static int[][] weapon, armor;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    B = Long.parseLong(st.nextToken());

    weapon = new int[N][2];
    armor  = new int[M][2];
    for(int i = 0; i < N; i++){
      st = new StringTokenizer(br.readLine());
      int p = Integer.parseInt(st.nextToken());
      int a = Integer.parseInt(st.nextToken());

      weapon[i][0] = p;
      weapon[i][1] = a;
    }

    for(int i = 0; i < M; i++){
      st = new StringTokenizer(br.readLine());
      int q = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());

      armor[i][0] = q;
      armor[i][1] = b;
    }

    Arrays.sort(weapon, (o1, o2) -> {
      if(o1[0] == o2[0]){
        return Integer.compare(o1[1], o2[1]);
      }
      return Integer.compare(o1[0], o2[0]);
    });

    int[] max = new int[M];
    Arrays.sort(armor, (o1, o2) -> {
      if(o1[0] == o2[0]){
        return Integer.compare(o1[1], o2[1]);
      }
      return Integer.compare(o1[0], o2[0]);
    });
    max[0] = armor[0][1];
    for(int i = 1; i < M; i++){
      max[i] = Math.max(max[i-1], armor[i][1]);
    }

    int ap = M-1;
    long sum = -1L;

    for(int i = 0; i < N; i++){
      long p = weapon[i][0];
      long a = weapon[i][1];

      while(armor[ap][0] + p > B && ap > 0){
        ap--;
      }

      if(armor[ap][0] + p <= B){
        sum = Math.max(sum, max[ap] + a);
      }
    }

    System.out.println(sum);
  }
}
