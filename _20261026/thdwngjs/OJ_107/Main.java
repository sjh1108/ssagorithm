import java.io.*;
import java.util.*;

public class Main {
  private static int H, W;
  private static char[][] map;

  private static Map<Character, Integer> direction;

  private static int[] dx = {-1, 1, 0, 0};
  private static int[] dy = {0, 0, -1, 1};
  static {
    direction = new HashMap<>();
    direction.put('U', 0);
    direction.put('D', 1);
    direction.put('L', 2);
    direction.put('R', 3);
  }

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    H = Integer.parseInt(st.nextToken());
    W = Integer.parseInt(st.nextToken());

    int[] p1 = new int[2];
    int[] p2 = new int[2];
    map = new char[H][];
    for(int i = 0; i < H; i++){
      map[i] = br.readLine().toCharArray();

      for(int j = 0; j < W; j++){
        if(map[i][j] == '1'){
          p1[0] = i;
          p1[1] = j;
        }
        else if(map[i][j] == '2'){
          p2[0] = i;
          p2[1] = j;
        }
      }
    }

    char[] commands = br.readLine().toCharArray();
    for(char cmd : commands){
      boolean moveFlag = false;
      do{
        moveFlag = false;
        if(move(direction.get(cmd), p1)) moveFlag = true;
        if(move(direction.get(cmd), p2)) moveFlag = true;
      } while(moveFlag);
    }

    StringBuilder sb = new StringBuilder();
    sb.append(p1[0] + 1).append(' ').append(p1[1] + 1);
    sb.append('\n');
    sb.append(p2[0] + 1).append(' ').append(p2[1] + 1);
    System.out.println(sb);
  }

  private static boolean move(int d, int[] point){
    int nx = point[0] + dx[d];
    int ny = point[1] + dy[d];

    if(isOut(nx, ny) || isCrash(nx, ny)) return false;

    map[nx][ny] = map[point[0]][point[1]];
    map[point[0]][point[1]] = '.';
    point[0] = nx;
    point[1] = ny;

    return true;
  }

  private static boolean isCrash(int x, int y){
    return map[x][y] != '.';
  }

  private static boolean isOut(int x, int y){
    return x < 0 || x >= H || y < 0 || y >= W;
  }
}
