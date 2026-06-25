import java.util.*;

class Solution {
    
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    
    public int solution(int[][] maps) {
        int n = maps.length;
        int m = maps[0].length;
        
        int[][] dist = new int[n][m];
        
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0,0});
        dist[0][0] = 1;
        
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                
                if(nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
                if(maps[nr][nc] == 0) continue;
                if(dist[nr][nc] != 0) continue;
                
                dist[nr][nc] = dist[r][c] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }
        int answer = dist[n-1][m-1];
        
        if (answer == 0) {
            return -1;
        }
        return answer;
    }
}