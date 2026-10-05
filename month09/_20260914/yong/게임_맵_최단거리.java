import java.util.ArrayDeque;
import java.util.Queue;

public class 게임_맵_최단거리 {
    static int[][] dt = {{-1,0},{0,1},{1,0},{0,-1}}; //위, 오른쪽, 아래, 왼쪽
    static int N,M;
    public static int solution(int[][] maps){
        int answer = 0;

        N = maps.length;
        M = maps[0].length;

        answer = bfs(0,0, maps);
        return answer;
    }
    public static class Node{
        int x; int y; int depth;
        Node(int x, int y, int depth){
            this.x = x;
            this.y = y;
            this.depth = depth;
        }

    }
    public static int bfs(int startX, int startY,int[][] maps){
        int result = -1;
        Queue<Node> q = new ArrayDeque<>();
        q.add(new Node(startX, startY,1));
        boolean[][] visited = new boolean[N][M];
        visited[startX][startY] = true;

        while (!q.isEmpty()) {
            Node now = q.poll();

            if(now.x == N-1 && now.y == M-1){
                result = now.depth;
                break;
            }

            for(int d = 0; d < 4; d++){
                int nx = now.x + dt[d][0];
                int ny = now.y + dt[d][1];
                // System.out.println("nowx: " + now.x + "|  nowy: " + now.y);
                // 맵 밖인지 체크
                if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                // 벽인지 이미 방문 했는지 체크
                if(maps[nx][ny] == 0 || visited[nx][ny]) continue;

                // 방문 후 체크
                visited[nx][ny] = true;
                q.add(new Node(nx,ny,now.depth + 1));
                // System.out.println("두번째 nx: " + nx + "|  ny: " + ny);
                
                
            }
        }
        return result;
    }
    public static void main(String[] args){

        int[][] map = {
            {1, 0, 1, 1, 1},
            {1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1},
            {1, 1, 1, 0, 1},
            {0, 0, 0, 0, 1}
        };

        int result = solution(map);
        System.out.println(result);
    }
}
