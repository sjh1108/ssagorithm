import java.util.*;
import java.io.*;

public class Main {
  static int[][] dt = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}}; // 12시 부터 시계방향
  static int R, C;
  static char[][] map;

  public static int bfs(int r, int c){

    Queue<int[]> q = new ArrayDeque<>();
    int area = 1;
    map[r][c] = '.';
    q.offer(new int[] {r, c});

    while(!q.isEmpty()){
      int[] cur = q.poll();
      for(int d = 0; d < 8; d++){
        int nr = cur[0] + dt[d][0];
        int nc = cur[1] + dt[d][1];
        if(nr < 0 || nc < 0 || nr >= R || nc >= C) continue;
        if(map[nr][nc] != '#') continue;
        area++;
        map[nr][nc] = '.';
        q.offer(new int[] {nr,nc});
      }
    }
    return area;
  }
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    
    map = new char[R][C];
    for(int row = 0; row < R; row++){
      String line = br.readLine();
      for(int col = 0; col < C; col++){
        map[row][col] = line.charAt(col);
      }
    }
    int islandCnt = 0;
    int largestArea = 0;

    for(int row = 0; row < R; row++){
      for(int col = 0; col < C; col++){
        if(map[row][col] == '#'){
          int area = bfs(row, col);
          islandCnt++;
          largestArea = Math.max(largestArea, area);
        }
      }
    }
    System.out.println(islandCnt + " " + largestArea);
  }
}
