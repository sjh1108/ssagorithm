import java.util.*;
import java.io.*;

/*
바라보는 방향 벽 아니면 전진, 벽이면 방향 전환(90도)
이동, 회전 각 1초
처음 위치 밟고 시작
*/
public class Main {
  static int[][] dt = {{-1,0},{0,1},{1,0},{0,-1}}; // U -> R -> D -> L
  static int R, C, K;
  static char[][] map;
  static int maxTile;
  static int nowTile;
  public static class Node{
    int r; int c; int dir;
    Node(int r, int c, int dir){
      this.r = r;
      this.c = c;
      this.dir = dir;
    }
  }
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken());
    C = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    // System.out.println("R: " + R + ", C: " + C + ", K: "+ K);

    // 맵 입력 받기    
    map = new char[R+1][C+1];
    maxTile = 0;

    for(int i = 0; i < R; i++){
      String line = br.readLine(); 
      for(int j = 0; j < C; j++){
        map[i+1][j+1] = line.charAt(j);
        if(map[i+1][j+1] == '.') maxTile++;
      }
    }

    // 시작 위치, 방향
    st = new StringTokenizer(br.readLine());
    int startR = Integer.parseInt(st.nextToken());
    int startC = Integer.parseInt(st.nextToken());
    char startDir = st.nextToken().charAt(0);
    int startDirToInt = 0;

    switch (startDir){
      case 'U':
        startDirToInt = 0;
        break;
      case 'R':
        startDirToInt = 1;
        break;
      case 'D':
        startDirToInt = 2;
        break;
      case 'L':
        startDirToInt = 3;
        break;
    }

    // System.out.println(startDirToInt);

    int result = simulate(startR, startC, startDirToInt);
    System.out.println(result);

  }

  public static int simulate(int startR, int startC, int startDir){
    Deque<Node> commands = new ArrayDeque<>();
    boolean[][] visited = new boolean[R+1][C+1];
    commands.offer(new Node(startR, startC, startDir));
    
    nowTile = 1;
    visited[startR][startC] = true;
    int time = 0;

    while(!commands.isEmpty() && time < K){
      Node now = commands.removeFirst();
      if(maxTile == nowTile) break;
      // 전진 가능한지
      if(canMove(now)){
        // System.out.println(now.r + " " + now.c);
        int nx = now.r + dt[now.dir][0];
        int ny = now.c + dt[now.dir][1];
        // 전진할 칸 방문처리
        if(!visited[nx][ny]){
          visited[nx][ny] = true;
          nowTile++;
        }
        // 시간 증가
        time++;
        commands.offer(new Node(nx, ny, now.dir));
      }
      else{
        int nxDir = (now.dir + 1) % 4;
        // System.out.println("now: " + now.dir + ", next: " + nxDir);
        commands.offer(new Node(now.r,now.c,nxDir));
        time++;
      }

    }
    return nowTile;
  }
  // 한칸 전진 가능한지 체크
  public static boolean canMove(Node now){
    int nx = now.r + dt[now.dir][0];
    int ny = now.c + dt[now.dir][1];

    if(nx < 1 || ny < 1 || nx > R || ny > C) return false;
    if(map[nx][ny] == '.') return true;
    return false;
  }
}
 