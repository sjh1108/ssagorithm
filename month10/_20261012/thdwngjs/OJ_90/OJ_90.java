package _20261012.thdwngjs.OJ_90;

import java.io.*;
import java.util.*;

class Main {
  private static int H, W;

  private static int[] dx = {-1, 0, 1, 0};
  private static int[] dy = {0, -1, 0, 1};
  private static int[][] map, max;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    H = Integer.parseInt(st.nextToken());
    W = Integer.parseInt(st.nextToken());

    map = new int[H][W];
    max = new int[H][W];
    for(int i = 0; i < H; i++){
      st = new StringTokenizer(br.readLine());
      for(int j = 0; j < W; j++){
        map[i][j] = Integer.parseInt(st.nextToken());
      }

      Arrays.fill(max[i], -1);
    }

    System.out.println(bfs());
  }

  private static int bfs(){
    Queue<int[]> q = new ArrayDeque<>();
    q.add(new int[]{0, 0, map[0][0]});

    while(!q.isEmpty()){
      int[] cur = q.poll();

      int x = cur[0], y = cur[1], oxygen = cur[2];

      if(max[x][y] != -1 && max[x][y] >= oxygen) continue;
      max[x][y] = oxygen;

      for(int d = 0; d < 4; d++){
        int nx = dx[d] + x;
        int ny = dy[d] + y;

        if(isOut(nx, ny)) continue;
        
        int no = Math.min(oxygen, map[nx][ny]);
        if(max[nx][ny] != -1 && max[nx][ny] >= no) continue;

        q.add(new int[]{nx, ny, no});
      }
    }

    return max[H-1][W-1];
  }

  private static boolean isOut(int x, int y){
    return x < 0 || x >= H || y < 0 || y >= W;
  }
}

