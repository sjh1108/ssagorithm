import java.util.*;
import java.io.*;
public class Main {

  static int[][] dt = {{-1,0},{0,1},{1,0},{0,-1}};
  static int N, M;
  static char[][] map;
  static int[] start = new int[2];

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());

    map = new char[N][M];

    for(int i = 0; i < N; i++){
      String line = br.readLine();

      for(int j = 0; j < M; j++){
        map[i][j] = line.charAt(j);

        if(map[i][j] == 'S'){
          start[0] = i;
          start[1] = j;
        }
      }
    }

    int result = bfs();
    System.out.println(result);

  }

  static class Node{
    int x; int y; int cnt;
    Node(int x, int y, int cnt){
      this.x = x;
      this.y = y;
      this.cnt = cnt;
    }
  }

  static int bfs(){
    boolean[][] visited = new boolean[N][M];

    Queue<Node> q = new ArrayDeque<>();
    q.offer(new Node(start[0],start[1],0));
    visited[start[0]][start[1]] = true;

    while(!q.isEmpty()){
      Node now = q.poll();
      // System.out.println("x = "+ now.x + " : y = " + now.y +" : cnt = " + now.cnt);
      if(map[now.x][now.y] == 'E'){
        return now.cnt;
      }

      for(int i = 0; i < 4; i++){
        int nx; int ny;
        nx = now.x + dt[i][0];
        ny = now.y + dt[i][1];
        int cnt = now.cnt + 1;
        if(nx >= N || ny >= M || nx < 0 || ny < 0) continue;
        if(visited[nx][ny] || map[nx][ny] == '#') continue;

        q.offer(new Node(nx,ny,cnt));
        visited[nx][ny] = true;
      }
    }
    return -1;
  }
}
