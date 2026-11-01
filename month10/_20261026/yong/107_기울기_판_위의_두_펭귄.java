import java.util.*;
import java.io.*;
/*
두 펭귄 안부딪히도록 격자 시뮬
기울이는 방향에 맞춰서 펭귄들 위치 비교 후 순서 정하기
*/
public class Main{
  public static class Penguin{
    int r; int c;
    public Penguin(int r, int c){
      this.r = r;
      this.c = c;
    }
  }
  public static void move(Penguin cur, Penguin obstaclePenguin, int dir){
    // 벽이나 장애물 펭귄 만날때까지 이동
    while(true){
      int nr = cur.r + dt[dir][0];
      int nc = cur.c + dt[dir][1];

      // 벽
      if(iceMap[nr][nc] == '#') break;
      // 다른 장애물 펭귄
      if(nr == obstaclePenguin.r && nc == obstaclePenguin.c) break;
      cur.r = nr;
      cur.c = nc;
    }
  }
  public static void simulate(char[] commands, Penguin p1, Penguin p2){
    // 순서 정하고 방향 입력
    for(char c : commands){
      Penguin first; // 먼저 이동할 펭귄
      Penguin second; // 다음에 이동할 펭귄
      int dir;
      if(c == 'U'){
        // p1이 더 위에 있으면 p1먼저
        dir = 0;
        if(p1.r < p2.r){
          first = p1; second = p2;
        }
        else{
          first = p2; second = p1;
        }
      }
      // R
      else if(c == 'R'){
        dir = 1;
        if(p1.c > p2.c){
          first = p1; second = p2;
        }
        else{
          first = p2; second = p1;
        }
      }
      // D
      else if(c == 'D'){
        dir = 2;
        if(p1.r > p2.r){
          first = p1; second = p2;
        }
        else{
          first = p2; second = p1;
        }
      }
      // L
      else{
        dir = 3;
        if(p1.c < p2.c){
          first = p1; second = p2;
        }
        else{
          first = p2; second = p1;
        }
      }
      // 펭귄 움직이기
      move(first, second, dir); // (먼저움직이는 펭귄, 움직이는 방향)
      move(second, first, dir); // (두번째로 움직이는 펭귄, 움직이는 방향)
    }
  }
  static int h, w, t;
  static char[][] iceMap;
  static int[][] dt = {{-1,0},{0,1},{1,0},{0,-1}}; // 위 오른쪽 아래 왼쪽
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    h = Integer.parseInt(st.nextToken());
    w = Integer.parseInt(st.nextToken());
    t = Integer.parseInt(st.nextToken());

    iceMap = new char[h + 1][w + 1];
    Penguin p1 = null;
    Penguin p2 = null;
    // 맵 입력받기
    for(int i = 1; i <= h; i++){
      String line = br.readLine();
      for(int j = 1; j <= w; j++){
        iceMap[i][j] = line.charAt(j - 1);
        if(iceMap[i][j] == '1'){
          p1 = new Penguin(i, j);
        }
        else if(iceMap[i][j] == '2'){
          p2 = new Penguin(i, j);
        }
      }
    }
    String commands = br.readLine();

    simulate(commands.toCharArray(), p1, p2);

    System.out.println(p1.r + " " + p1.c);
    System.out.println(p2.r + " " + p2.c);
  }
}
