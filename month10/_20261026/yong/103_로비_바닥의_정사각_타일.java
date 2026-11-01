import java.util.*;
import java.io.*;

public class Main {
  public static void fill(int r, int c, int size, char value){
    for(int i = r; i < r + size; i++){
      for(int j = c; j < c + size; j++){
        board[i][j] = value;
      }
    }
  }

  // 타일 놓을수 있는 자리, 범위 인지 체크
  public static boolean canPlace(int r, int c, int size){
    if(r + size > h || c + size > w){
      return false;
    }
    for(int i = r; i < r + size; i++){
      for(int j = c; j < c + size; j++){
        if(board[i][j] == '.'){
          return false;
        }
      }
    }
    return true;
  }

  public static void back(int curCost){
    if(curCost >= result){
      return;
    }

    // 덮을 # 찾기
    int r = -1;
    int c = -1;

    loop1:
    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        if(board[i][j] == '#'){
          r = i;
          c = j;
          break loop1;
        }
      }
    }

    // 다 덮었을때
    if(r == -1){
      if(result > curCost){
        result = curCost;
      }
      return;
    }

    // 큰 타일부터 사용
    for(int k = 5; k > 0; k--){
      // 재고 확인
      if(tiles[k] == 0){
        continue;
      }

      // 타일 놓을수 있는지 체크
      if(!canPlace(r, c, k)){
        continue;
      }

      // 타일 배치
      fill(r, c, k, '.');
      tiles[k]--;
      back(curCost + cost[k]);
      
      // 타일 회수
      fill(r, c, k, '#');
      tiles[k]++;
    }
  }

  static char[][] board;
  static int h, w;
  static int[] tiles = new int[6];
  static int[] cost = new int[6];
  static int result = Integer.MAX_VALUE;

  public static void main(String[] args) throws IOException{
    BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    h = Integer.parseInt(st.nextToken());
    w = Integer.parseInt(st.nextToken());

    st = new StringTokenizer(br.readLine());
    for(int i = 1; i <= 5; i++){
      tiles[i] = Integer.parseInt(st.nextToken());
    }

    st = new StringTokenizer(br.readLine());
    for(int i = 1; i <= 5; i++){
      cost[i] = Integer.parseInt(st.nextToken());
    }

    board = new char[h][w];

    for(int i = 0; i < h; i++){
      board[i] = br.readLine().toCharArray();
    }

    back(0);

    if(result == Integer.MAX_VALUE){
      System.out.println(-1);
    }
    else{
      System.out.println(result);
    }
    
  }
}
