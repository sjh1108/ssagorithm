package _20261026.thdwngjs.OJ_108;

import java.io.*;
import java.util.*;

public class Main {
  private static int H, W;
  private static char[][] map;
  private static boolean[][] visited;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    H = Integer.parseInt(st.nextToken());
    W = Integer.parseInt(st.nextToken());
    
    map = new char[H][];
    visited = new boolean[H][W];
    int[] penguin = new int[2];
    for(int i = 0; i < H; i++){
      map[i] = br.readLine().toCharArray();
      for(int j = 0; j < W; j++){
        if(map[i][j] == 'S'){
          penguin[0] = i;
          penguin[1] = j;
          map[i][j] = '.';
          visited[i][j] = true;
        }
      }
    }

    Queue<int[]> q = new ArrayDeque<>();
    q.add(new int[]{penguin[0], penguin[1], 0});

    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, 1, 0, -1};
    while(!q.isEmpty()){
      int[] cur = q.poll();
      
      int x = cur[0], y = cur[1], c = cur[2];
      // System.out.print("poll : ");
      // System.out.println(x + " " + y + " " + c);
      if(isEnd(x, y)){
        System.out.println(c);
        return;
      }

      for(int d = 0; d < 4; d++){
        int nx = x + dx[d];
        int ny = y + dy[d];

        while(true){
          if(isSand(nx, ny)){
            if(visited[nx][ny]) break;
            visited[nx][ny] = true;
            q.add(new int[]{nx, ny, c+1});
          }

          else if(isIce(nx, ny) || isEnd(nx, ny)){
            nx += dx[d]; ny += dy[d];
          }

          else if(isWall(nx, ny)){
            nx -= dx[d]; ny -= dy[d];

            if(visited[nx][ny]) break;
            visited[nx][ny] = true;

            q.add(new int[]{nx, ny, c+1});
          }
        }
      }
    }

    System.out.println(-1);
  }

  private static boolean isOut(int x, int y){
    return x < 0 || x >= H || y < 0 || y >= W;
  }

  private static boolean isEnd(int x, int y){
    return map[x][y] == 'E';
  }

  private static boolean isIce(int x, int y){
    return map[x][y] == '.';
  }

  private static boolean isWall(int x, int y){
    return map[x][y] == '#';
  }

  private static boolean isSand(int x, int y){
    return map[x][y] == 'O';
  }
}
