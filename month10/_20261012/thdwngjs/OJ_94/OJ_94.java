package _20261012.thdwngjs.OJ_94;

import java.io.*;
import java.util.*;

class Main {
  private static int N;

  private static int[][] drone;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine());

    StringTokenizer st;
    drone = new int[N][2];
    for(int i = 0; i < N; i++){
      st = new StringTokenizer(br.readLine());

      drone[i][0] = Integer.parseInt(st.nextToken());
      drone[i][1] = Integer.parseInt(st.nextToken());
    }

    Arrays.sort(drone, (o1, o2) -> Integer.compare(o1[0], o2[0]));

    System.out.println(conquer(0, N));
  }

  private static long conquer(int lo, int hi){
    if(hi - lo <= 3){
      long res = Long.MAX_VALUE;

      for(int i = lo; i < hi-1; i++){
        int x1 = drone[i][0], y1 = drone[i][1];
        for(int j = i + 1; j < hi; j++){
          int x2 = drone[j][0], y2 = drone[j][1];

          long d = getDistance(x1, y1, x2, y2);
          
          res = Math.min(res, d);
        }
      }

      return res;
    }

    int mid = (lo + hi) >>> 1;

    long minLeft = conquer(lo, mid);
    long minRight = conquer(mid + 1, hi);

    long min = Math.min(minLeft, minRight);
    int mx = drone[mid][0];

    int s = lo, e = hi;
    List<int[]> list = new ArrayList<>();
    for(int i = s; i < e; i++){
      int x1 = drone[i][0];

      int gap = mx - x1;

      if(gap * gap < min){
        list.add(drone[i]);
      }
    }

    list.sort(Comparator.comparingInt(o -> o[1]));

    int size = list.size();
    for(int i = 0; i < size-1; i++){
      int[] o1 = list.get(i);

      int x1 = o1[0], y1 = o1[1];
      for(int j = i+1; j < size; j++){
        int[] o2 = list.get(j);

        int x2 = o2[0], y2 = o2[1];

        long y = y2 - y1;
        if(y * y > min) break;

        long x = x2 - x1;
        long dist = x*x + y*y;
        min = Math.min(dist, min);
      }
    }1

    return min;
  }

  private static long getDistance(int x1, int y1, int x2, int y2){
    long x = x1 - x2;
    long y = y1 - y2;
    return (x*x) + (y*y);
  }
}