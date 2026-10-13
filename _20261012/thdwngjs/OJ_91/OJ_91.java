package _20261012.thdwngjs.OJ_91;

import java.io.*;
import java.util.*;

class Main {
  private static final int MAX_DIST = 50_001;

  private static int H, W;
  private static int ans;
  private static int dis;

  private static int[] s, e;

  private static int[] dx = {-1, 0, 1, 0};
  private static int[] dy = {0, -1, 0, 1};

  private static int[][] dist;
  private static char[][] map;
  private static boolean[][] visited;

  private static Queue<int[]> q;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    H = Integer.parseInt(st.nextToken());
    W = Integer.parseInt(st.nextToken());

    map = new char[H][W];
    dist = new int[H][W];
    q = new ArrayDeque<>();

    s = new int[2]; e = new int[2];
    for(int i = 0; i < H; i++){
      String input = br.readLine();
      map[i] = input.toCharArray();

      Arrays.fill(dist[i], MAX_DIST);

      for(int j = 0; j < W; j++){
        if(map[i][j] == 'S'){
          s[0] = i; s[1] = j;
        } else if(map[i][j] == 'E'){
          e[0] = i; e[1] = j;
        } else if(map[i][j] == '*'){
          q.add(new int[]{i, j, 0});
        }
      }
    }

    bfs();
    ans = -1;
    visited = new boolean[H][W];
    binarySearch(0, Math.min(dist[s[0]][s[1]], dist[e[0]][e[1]]));

    if(ans == -1) System.out.println(-1);
    else{
      System.out.println(ans + " " + dis);
    }
  }

  // 각 가스 분출구부터 최소 거리 연산
  private static void bfs(){
    while(!q.isEmpty()){
      int[] cur = q.poll();
      int x = cur[0], y = cur[1], w = cur[2];

      if(dist[x][y] != MAX_DIST) continue;
      dist[x][y] = w;

      for(int d = 0; d < 4; d++){
        int nx = x + dx[d];
        int ny = y + dy[d];

        if(isOut(nx, ny)) continue;
        if(map[nx][ny] == '#') continue;
        if(dist[nx][ny] != MAX_DIST) continue;
        q.add(new int[]{nx, ny, w + 1});
      }
    }
  }

  private static void binarySearch(int lo, int hi){
    // System.out.println("binarySearch : " + lo + " " + hi);
    if(lo > hi){
      return;
    }
    int mid = (lo + hi) / 2;
    // System.out.println(mid);
    // System.out.println();

    for(boolean[] tmp: visited){
      Arrays.fill(tmp, false);
    }
    int cost = isPossible(mid);

    if(cost > 0){
      ans = Math.max(mid, ans);
      dis = cost;

      binarySearch(mid+1, hi);
    } else{
      binarySearch(lo, mid - 1);
    }
  }

  private static int isPossible(int min){
    q.clear();
    q.add(new int[]{s[0], s[1], 0});

    while(!q.isEmpty()){
      int[] cur = q.poll();
      int x = cur[0], y = cur[1], w = cur[2];

      if(visited[x][y]) continue;
      if(dist[x][y] < min) continue;
      visited[x][y] = true;

      for(int d = 0; d < 4; d++){
        int nx = x + dx[d];
        int ny = y + dy[d];
        int nw = w + 1;

        if(isOut(nx, ny)) continue;
        if(map[nx][ny] == '#') continue;
        if(map[nx][ny] == 'E') return nw;
        if(visited[nx][ny]) continue;

        q.add(new int[]{nx, ny, nw});
      }
    }

    return -1;
  }

  private static boolean isOut(int x, int y){
    return x < 0 || x >= H || y < 0 || y >= W;
  }
}
