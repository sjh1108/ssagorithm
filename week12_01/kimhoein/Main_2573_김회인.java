package study;

import java.io.*;
import java.util.*;

/*
 * 빙산 주위로 delat 사용해서 바다와 맞닿은면 구하고 그거에 따라 빙산 높이 감소
 * 상태 값 변화로 인해 바다가 된 빙산이 값에 들어갈 수도 있으므로 iceberg로 빙산 체크해줘서 카운트 체크에 문제 없게 해줌
 * dfs 사용해서 빙산의 조각이 몇개인지 체크
 * 빙산이 0개 인지는 빙산 녹이는 거 체크할때 state 값이 변하지 않으면 while 탈출
 */
class Main_2573_김회인 {
	static int[][] delta = {{1,0},{-1,0},{0,1},{0,-1}};
	static int[][] map;
	static int n,m;
	static boolean[][] iceberg;
	static boolean[][] visit;
	static int state=0;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        
        for(int i=0; i<n; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	for(int j=0; j<m; j++)
        	{
        		map[i][j] = Integer.parseInt(st.nextToken());
        	}
        }
        int year=0;
        
        
        
        while(true)
        {
        	state=0;
        	iceberg = new boolean[n][m];
        	visit = new boolean[n][m];
        	
        	 for(int i=0; i<n; i++)
             {
             	for(int j=0; j<m; j++)
             	{
             		if(map[i][j] == 0 || visit[i][j]) continue;
             		visit[i][j] = true;
             		state++;
             		if(state == 2) {
             			bw.write(Integer.toString(year));
             			break;
             		}
             		dfs(i,j);
             	}
             }
        	 
        	 if(state > 1) break;
        	 state =0;
        	 
        	 for(int i=0; i<n; i++)
             {
             	for(int j=0; j<m; j++)
             	{
             		if(map[i][j] == 0) continue;
             		
             		iceberg[i][j] = true;
             		map[i][j] = Math.max(map[i][j] - melt(i,j), 0);
             		state++;
             	}
             }
        	 
        	 
        	 if(state == 0) {
        		 bw.write("0");
        		 break;
        	 }

        	 year++;
        }
        
       
        bw.flush();
        bw.close();
        br.close();
    }
    
    static int melt(int x, int y)		// 빙산 녹이는 함수
    {
    	int count=0;
    	
    	for(int i=0; i<4; i++)
    	{
    		int dx = x + delta[i][0];
    		int dy = y + delta[i][1];
    		
    		if(!(dx >= 0 && dx<n && dy>=0 && dy<m)) continue;
    		
    		if(map[dx][dy] == 0 && !iceberg[dx][dy]) count++;

    		
    	}
    
    	return count;
    }
    
    static void dfs(int x, int y)		// dfs를 통해서 빙산을 찾는다.
    {
    	for(int i=0; i<4; i++)
    	{
    		int dx = x + delta[i][0];
    		int dy = y + delta[i][1];
    		
    		if(!(dx >= 0 && dx<n && dy>=0 && dy<m)) continue;
    		if(map[dx][dy] == 0|| visit[dx][dy]) continue;
    		
    		visit[dx][dy] = true;
    		dfs(dx,dy);
    		
    	}
    }
}