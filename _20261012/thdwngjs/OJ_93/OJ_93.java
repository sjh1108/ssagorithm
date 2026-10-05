package _20261012.thdwngjs.OJ_93;

import java.io.*;
import java.util.*;

class Main {
  private static int N, C, D;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    D = Integer.parseInt(st.nextToken());

    long d = (long)D*D;

    List<int[]> drone = new ArrayList<>();
    for(int i = 0; i < N; i++){
      st = new StringTokenizer(br.readLine());

      int x = Integer.parseInt(st.nextToken());
      int y = Integer.parseInt(st.nextToken());

      if(Math.abs(C - x) > D) continue;
      drone.add(new int[]{x, y, x > C ? 1 : 0});
    }

    drone.sort((o1, o2) -> o1[1] - o2[1]);
    int size = drone.size();

    long min = Long.MAX_VALUE;
    for(int i = 0; i < size - 1; i++){
      int[] o1 = drone.get(i);

      int x1 = o1[0];
      int y1 = o1[1];
      for(int j = i + 1; j < size; j++){
        int[] o2 = drone.get(j);

        int x2 = o2[0];
        int y2 = o2[1];
        if(o1[2] == o2[2] || Math.abs(y1 - y2) > D) break;

        long dist = getDistance(x1, y1, x2, y2);
        if(dist > d) continue;

        min = Math.min(min, dist);
      }
    }

    System.out.println(min == Long.MAX_VALUE ? -1 : min);
  }

  private static long getDistance(int x1, int y1, int x2, int y2){
    long x = x1 - x2;
    long y = y1 - y2;

    return (x*x) + (y*y);
  }
}
