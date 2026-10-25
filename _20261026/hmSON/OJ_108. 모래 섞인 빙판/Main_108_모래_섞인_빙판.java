import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : BFS
     *
     * 미끄러져서 이동해야 하며 벽에 부딪히거나 모래를 밟아서 멈춘 위치만 방문 처리를 해야 함
     * 도착점은 반드시 얼음이므로 지나가야만 하는 경우도 존재할 수 있음
     * 도착접에 도달한 경우 미끄러져 이동한 횟수를, 그렇지 못한 경우 -1을 출력
     */

    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        char[][] map = new char[r][c]; // 맵 정보
        boolean[][] visited = new boolean[r][c]; // 방문 처리
        Queue<int[]> q = new ArrayDeque<>(); // BFS용 큐
        int ey = -1, ex = -1; // 도착점 좌표 저장

        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                char ch = line.charAt(j);
                // 시작점은 큐에 넣고 방문 처리한 뒤 얼음으로 변경
                if(ch == 'S') {
                    q.add(new int[]{i, j, 0});
                    ch = '.';
                    visited[i][j] = true;
                }
                // 도착점은 따로 변수로 기록하고 얼음으로 변경
                else if(ch == 'E') {
                    ey = i;
                    ex = j;
                    ch = '.';
                }

                map[i][j] = ch;
            }
        }

        // BFS
        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<4; i++) {
                int y = cur[0], x = cur[1];

                // 다음 칸이 벽이 아니라면 한 칸씩 이동
                while(map[y+dy[i]][x+dx[i]] != '#') {
                    y += dy[i];
                    x += dx[i];
                    // 밟은 칸이 모래면 정지
                    if(map[y][x] == 'O') break;
                }

                // 지금 밟고 있는 칸이 도착점이면 이동 횟수 출력하고 종료
                if(y == ey && x == ex) {
                    System.out.println(cur[2] + 1);
                    return;
                }

                if(visited[y][x]) continue;
                visited[y][x] = true;
                q.add(new int[]{y, x, cur[2] + 1});
            }
        }

        // 도착점에 정확히 멈출 방법이 없음
        System.out.println(-1);
    }

}