import java.io.*;
import java.util.*;

public class Main {
  private static int R, C;
  private static int max;

  private static int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
  private static int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};
  private static char[][] map;
  private static Queue<int[]> q;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());

    map = new char[R][];

    for(int i = 0; i < R; i++){
      map[i] = br.readLine().toCharArray();
    }

    int cnt = 0;
    max = 0;

    q = new ArrayDeque<>();
    for(int i = 0; i < R; i++){
      for(int j = 0; j < C; j++){
        if(map[i][j] == '#'){
          bfs(i, j);
          cnt++;
        }
      }
    }

    System.out.print(cnt + " " + max);
  }

  private static void bfs(int x, int y){
    int cnt = 0;

    q.clear();
    map[x][y] = '.';
    q.add(new int[]{x, y});

    while(!q.isEmpty()){
      int[] cur = q.poll();
      cnt++;

      for(int d = 0; d < 8; d++){
        int nx = cur[0] + dx[d];
        int ny = cur[1] + dy[d];

        if(isOut(nx, ny)) continue;
        if(map[nx][ny] == '.') continue;

        map[nx][ny] = '.';
        q.add(new int[]{nx, ny});
      }
    }

    max = Math.max(max, cnt);
  }

  private static boolean isOut(int x, int y){
    return x < 0 || x >= R || y < 0 || y >= C;
  }
}
