package algo2025_11_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main_11559_ppuyo {
    static char[][] map;
    static boolean[][] visited;
    static int[] dx = {0,1,0,-1};
    static int[] dy = {1,0,-1,0};
    static final int n = 12;
    static final int m = 6;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        map = new char[n][m];
        
        for(int i = 0; i < n; i++) {
            String line = br.readLine();
            for(int j = 0; j < m; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        int cnt = 0;

        while(true) {
            visited = new boolean[n][m];
            boolean clearPuyo = false;

            // 1. BFS로 모든 뿌요 그룹 확인
            for(int i = 0; i < n;i++) {
                for(int j = 0; j < m;j++) {
                    if(map[i][j] != '.' && !visited[i][j]) {
                        List<int[]> group = bfs(i,j,map[i][j]);
                        if(group.size() >= 4) {
                        	clearPuyo = true;
                            for(int[] p : group) {
                                map[p[0]][p[1]] = '.';
                            }
                        }
                    }
                }
            }

            if(!clearPuyo) break; // 더 이상 터질 뿌요 없음

            cnt++;           // 연쇄 1 증가
            downPuyo();    // 중력 적용
        }

        System.out.println(cnt);
    }

    // BFS로 같은 색 그룹 찾기
    static List<int[]> bfs(int x, int y, char color) {
        List<int[]> group = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x,y});
        visited[x][y] = true;
        group.add(new int[]{x,y});

        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0];
            int cy = cur[1];

            for(int d=0; d<4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if(nx<0 || ny<0 || nx>=n || ny>=m) continue;
                if(visited[nx][ny]) continue;
                if(map[nx][ny] != color) continue;

                visited[nx][ny] = true;
                queue.add(new int[]{nx,ny});
                group.add(new int[]{nx,ny});
            }
        }

        return group;
    }

    // 중력 처리
    static void downPuyo() {
        for(int col = 0; col  <m; col++) {
            int empty = 11;
            for(int row = 11; row >= 0; row--) {
                if(map[row][col] != '.') {
                    char temp = map[row][col];
                    map[row][col] = '.';
                    map[empty][col] = temp;
                    empty--;
                }
            }
        }
    }
}
