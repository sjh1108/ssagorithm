package week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//미세먼지 안녕!_골드4
public class Main_17144 {
    
    static int R, C, T;
    static int[] dr = {-1, 0, 1, 0}; 
    static int[] dc = {0, 1, 0, -1};
    
    static int[][] grid;     
    static int[][] addGrid; 
    
    static int cleanTop = -1;    
    static int cleanBottom = -1; 
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        R = Integer.parseInt(st.nextToken()); 
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken()); 
        
        grid = new int[R + 1][C + 1];

        boolean isTopFound = false;
        
        for (int i = 1; i <= R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= C; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                
                // 공기 청정기 위에 -1이 인식되면 바로 아래 부분은 자동으로 따라오기에 i+1
                if (grid[i][j] == -1 && !isTopFound) {
                    cleanTop = i;
                    cleanBottom = i + 1;
                    isTopFound = true; 
                }
            }
        }
        
        
        // 미세먼지가 사이드로 흩뿌려지는 것부터 계산하고, 그걸 임시 배열인 addGrid에 넣음
        // 중심부 빠진값 빼주기.  cnt로 미세먼지 흩뿌려진게 몇 번 처리됐나 확인하기.
        // 미세먼지 작업이 끝난 후 일괄적으로 addGrid룰 Grid에 넣기.  
        // 핵심 로직이 4부분(사이드로 흩뿌려지는 미세먼지, 중심부 미세먼지 영향, cnt, 마무리 미세먼지 한번에 정리하기 등 로직을 잘 정리해두기)
        while (T-- > 0) {
            addGrid = new int[R + 1][C + 1]; 

            for (int i = 1; i <= R; i++) {
                for (int j = 1; j <= C; j++) {
                    if(grid[i][j] > 0) {
                        int amount = grid[i][j] / 5;
                        int cnt = 0; 
                        
                        for (int d = 0; d < 4; d++) {
                            int nr = i + dr[d];
                            int nc = j + dc[d];
                            
                            if(nr >= 1 && nr <= R && nc >= 1 && nc <= C && grid[nr][nc] != -1) {
                                addGrid[nr][nc] += amount; 
                                cnt++; 
                            }
                        }
                        grid[i][j] -= (amount * cnt); 
                    }   
                }
            }
            
            for (int i = 1; i <= R; i++) {
                for (int j = 1; j <= C; j++) {
                    grid[i][j] += addGrid[i][j];
                }
            }
         
            operateAirCleaner();
        }
        
        int result = 0;
        for (int i = 1; i <= R; i++) {
            for (int j = 1; j <= C; j++) {
                if (grid[i][j] > 0) {
                    result += grid[i][j];
                }
            }
        }
        System.out.println(result);
    }
    
    static void operateAirCleaner() {
    	
    	// 시작점이 중요함. -> 시작점을 잘 못 잡으면 과정 상에서 값이 i+/-1에 덮여져서 에 답이 바뀜. 
    	// 시계반대방향으로 공기청정기 돌리기. 
    	

        for (int i = cleanTop - 1; i > 1; i--) {
            grid[i][1] = grid[i-1][1];
        }
        for (int j = 1; j < C; j++) {
            grid[1][j] = grid[1][j+1];
        }
        for (int i = 1; i < cleanTop; i++) {
            grid[i][C] = grid[i + 1][C];
        }
        for (int j = C; j > 2; j--) {
            grid[cleanTop][j] = grid[cleanTop][j - 1];
        }
        grid[cleanTop][2] = 0;

        
        // 시작점이 중요함.  
    	// 시계방향으로 공기청정기 돌리기. 
      
        for (int i = cleanBottom + 1; i < R; i++) {
            grid[i][1] = grid[i + 1][1];
        }
        for (int j = 1; j < C; j++) {
            grid[R][j] = grid[R][j + 1];
        }
        for (int i = R; i > cleanBottom; i--) {
            grid[i][C] = grid[i - 1][C];
        }
        for (int j = C; j > 2; j--) {
            grid[cleanBottom][j] = grid[cleanBottom][j - 1];
        }
        grid[cleanBottom][2] = 0;
    }
}
