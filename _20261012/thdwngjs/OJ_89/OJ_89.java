import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int H = Integer.parseInt(st.nextToken());
    int W = Integer.parseInt(st.nextToken());

    char[][] map = new char[H][W];
    boolean[][] visited = new boolean[H][W];

    int bx = -1;
    int by = -1;
    int bc = -1;

    Queue<int[]> q = new ArrayDeque<>();
    for(int i = 0; i < H; i++){
      map[i] = br.readLine().toCharArray();

      for(int j = 0; j < W; j++){
        if(map[i][j] =='*'){
          if(bx == -1){
            bx = i;
            by = j;
            bc = 0;
          }
          q.add(new int[]{i, j, 0});
        }
      }
    }

    int[] dx = {-1, 0, 1, 0}, dy = {0, -1, 0, 1};
    while(!q.isEmpty()){
      int[] cur = q.poll();

      int x = cur[0], y = cur[1], c = cur[2];
      if(visited[x][y]) continue;
      visited[x][y] = true;

      if(bc < c){
        bx = x;
        by = y;
        bc = c;
      }

      for(int d = 0; d < 4; d++){
        int nx = x + dx[d];
        int ny = y + dy[d];

        if(isOut(nx, ny, H, W)) continue;
        if(visited[nx][ny] || map[nx][ny] == '#') continue;

        q.add(new int[]{nx, ny, c+1});
      }
    }

    if(bc == 0){
      System.out.println(-1);
    } else{
      System.out.println(bc);
      System.out.println((bx + 1) + " " + (by + 1));
    }
  }

  private static boolean isOut(int x, int y, int H, int W){
    return x < 0 || x >= H || y < 0 || y >= W;
  }
}
