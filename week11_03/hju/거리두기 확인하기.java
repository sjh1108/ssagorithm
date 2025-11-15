class Solution {
    
    static char[][] room;
    static int dy[] = {-1, 0, 1, 0};
    static int dx[] = {0, 1, 0, -1};
    static boolean flag;
    static boolean[][] visited;
    
    public int[] solution(String[][] places) {
        int[] answer = new int[5];
        
        for(int tc = 0; tc < 5; tc++) {
            room = new char[5][5];
            
            for(int i = 0; i < 5; i++) {
                String line = places[tc][i];
                for(int j = 0; j < 5; j++)
                    room[i][j] = line.charAt(j);
            }
            
            flag = false;
            
            for(int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    if(room[i][j] == 'P') {
                        visited = new boolean[5][5];
                        visited[i][j] = true;
                        dfs(i, j, 0);
                    }
                    if(flag) break;
                }
                if(flag) break;
            }
            
            answer[tc] = flag ? 0 : 1;
        }  
        
        return answer;
    }
    
    static boolean isIn(int y, int x) {
        return y >= 0 && y < 5 && x >= 0 && x < 5;
    }
    
    static void dfs(int y, int x, int depth) {
        
        if(flag) return;
        
        if(depth == 2) {
            return;
        }
        
        for(int d = 0; d < 4; d++) {
            int ny = y + dy[d];
            int nx = x + dx[d];
            
            if(!isIn(ny, nx))   continue;
            if(visited[ny][nx]) continue;
            if(room[ny][nx] == 'X') continue;
            if(room[ny][nx] == 'P') {
                flag = true;
                return;
            }
            
            visited[ny][nx] = true;
            dfs(ny, nx, depth + 1);
        }

    }
}