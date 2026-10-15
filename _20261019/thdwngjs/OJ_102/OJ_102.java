import java.io.*;
import java.util.*;

class Main {
  private static int H, W;
  private static int cnt;
  private static int total;
  
  private static boolean[][] map;

  private static int[] dx = {1, 0};
  private static int[] dy = {0, 1};

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    H = Integer.parseInt(st.nextToken());
    W = Integer.parseInt(st.nextToken());

    map = new boolean[H][W];
    total = 0;
    char[] input;
    for(int i = 0; i < H; i++){
      input = br.readLine().toCharArray();
      for(int j = 0; j < W; j++){
        map[i][j] = input[j] == '#';

        if(map[i][j]) total++;
      }
    }
    if(total % 2 != 0){
      System.out.println(0);
      return;
    }

    dfs();
    System.out.println(cnt);
  }

  private static void dfs(){
    if(total == 0){
      cnt++;
      return;
    }

    for(int i = 0; i < H; i++){
      for(int j = 0; j < W; j++){
        if(!map[i][j]) continue;
        map[i][j] = false;

        for(int d = 0; d < 2; d++){
          int x = i + dx[d];
          int y = j + dy[d];

          if(isOut(x, y) || !map[x][y]) continue;

          map[x][y] = false;
          total -= 2;
          dfs();
          total += 2;
          map[x][y] = true;
        }

        map[i][j] = true;
        return;
      }
    }
  }

  private static boolean isOut(int x, int y){
    return x >= H || y >= W;
  }
}
