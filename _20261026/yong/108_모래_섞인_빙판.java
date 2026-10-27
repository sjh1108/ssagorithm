import java.util.*;
import java.io.*;

public class Main {
  public static class Node{
    int r; int c;

    public Node(int r, int c){
      this.r = r;
      this.c = c;
    }
  }
  
  static int[][] dt = {{-1,0},{0,1},{1,0},{0,-1}}; // 위, 오른쪽, 아래, 왼쪽
  public static int bfs(int sr, int sc, int er, int ec){
    Queue<Node> q = new ArrayDeque<>();
    int[][] dist = new int[h + 1][w + 1]; // 미끄러진 횟수체크 및 visited 체크용

    // 미끄러진 횟수 초기화
    for(int i = 1; i <= h; i++){
      Arrays.fill(dist[i],-1);
    }

    q.offer(new Node(sr,sc));
    dist[sr][sc] = 0;

    while(!q.isEmpty()){
      Node cur = q.poll();

      if(cur.r == er && cur.c == ec){ // E 도착
        return dist[er][ec];
      }

      for(int d = 0; d < 4; d++){
        int nr = cur.r;
        int nc = cur.c;
        // 미끄러지기
        while(true){
          int nextR = nr + dt[d][0];
          int nextC = nc + dt[d][1];
          
          // 다음칸 벽이면 현재위치에서 정지
          if(iceMap[nextR][nextC] == '#') break;
          // 미끄러지기(이동)
          nr = nextR;
          nc = nextC;
          // 모래는 이동 후 모래칸에서 정지
          if(iceMap[nr][nc] == 'O') break;
        }
        // 움직이지 않은경우
        if(cur.r == nr && cur.c == nc) continue;
        // 이전에 방문했던곳
        if(dist[nr][nc] != -1) continue;

        dist[nr][nc] = dist[cur.r][cur.c] + 1;
        q.offer(new Node(nr,nc));
      }

    }
    return -1;
  }

  static int h, w;
  static char[][] iceMap;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    h = Integer.parseInt(st.nextToken());
    w = Integer.parseInt(st.nextToken());
    
    iceMap = new char[h + 1][w + 1];

    int sr = 0, sc = 0, er = 0, ec = 0;
    for(int i = 1; i <= h; i++){
      String line = br.readLine();
      for(int j = 1; j <= w; j++){
        iceMap[i][j] = line.charAt(j - 1);
        if(iceMap[i][j] == 'S'){
          sr = i;
          sc = j;
        }
        else if(iceMap[i][j] == 'E'){
          er = i;
          ec = j;
        }
      }
    }

    System.out.println(bfs(sr, sc, er, ec));


  }
}
