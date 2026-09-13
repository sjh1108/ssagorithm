import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());

    Queue<int[]> q = new ArrayDeque<>();
    char[][] map = new char[N][M];
    for(int i = 0; i < N; i++){
      map[i] = br.readLine().toCharArray();
      for(int j = 0; j < M; j++){
        if(map[i][j] == 'S'){
          q.add(new int[]{i, j, 0});
        }
      }
    }

    boolean[][] visited = new boolean[N][M];
    
    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, 1, 0, -1};
    while(!q.isEmpty()){
      int[] cur = q.poll();

      int x = cur[0];
      int y = cur[1];
      int c = cur[2];
      
      if(map[x][y] == 'E'){
        System.out.println(c);
        return;
      }
      if(visited[x][y] || map[x][y] == '#') continue;
      visited[x][y] = true;

      for(int d = 0; d < 4; d++){
        int nx = x + dx[d];
        int ny = y + dy[d];

        if(isOut(nx, ny, N, M)) continue;

        q.add(new int[]{nx, ny, c + 1});
      }
    }

    System.out.println(-1);
  }

  private static boolean isOut(int x, int y, int N, int M){
    return x < 0 || x >= N || y < 0 || y >= M;
  }
}
