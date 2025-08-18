

import java.io.*;
import java.util.*;


public class Solution_1949_김회인 {
	static int max_count=0;
	static int delta[][] = {{1,0},{0,1},{-1,0},{0,-1}};
	static int N;
	static int K;
	//static boolean construction;
	static boolean visit[][];
    public static void main(String[] args) throws IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st;// = new StringTokenizer(br.readLine());
       int t = Integer.parseInt(br.readLine());
       
       for(int T=1; T<=t; T++)
       {
    	   st = new StringTokenizer(br.readLine());
    	   N = Integer.parseInt(st.nextToken());
    	   K = Integer.parseInt(st.nextToken());
    	   //construction = false;
    	   int array[][] = new int[N][N];
    	   visit = new boolean[N][N];
    	   int max=0;
    	   
    	   for(int i=0; i<N; i++)
    	   {
    		   st = new StringTokenizer(br.readLine());
    		   for(int j=0; j<N; j++)
    		   {
    			   array[i][j] = Integer.parseInt(st.nextToken());
    			   
    			   if(max < array[i][j]) max = array[i][j];
    		   }
    	   }
    	   
    	   for(int i=0; i<N; i++)		// 저장된 높은곳 이용해서 등산 시작
    	   {
    		   for(int j=0; j<N; j++)
    		   {
    			   if(max == array[i][j])
    			   {
    				   visit = new boolean[N][N];
    				   visit[i][j] = true;
    				   dfs(array,i,j,1,false);
    				   visit[i][j] = false;
    			   }
    		   }
    	   }
    	   
    	   bw.write("#" + Integer.toString(T) + " " + Long.toString(max_count)+"\n");
    	   max_count=0;
       }
       bw.flush();
       bw.close();
   
       
    }
    
    static void dfs(int array[][],int x,int y,int depth,boolean construction)
    {
    	//depth++;
    	//System.out.println("depth : " + depth);
    	if(depth > max_count) max_count = depth;
    	
    	for(int i=0; i<delta.length; i++)
    	{
    		int dx = x + delta[i][0];
    		int dy = y + delta[i][1];
    		
    		if(dx >=0 && dx <N && dy >=0 && dy <N && visit[dx][dy] == false)
    		{
    			if(array[x][y] > array[dx][dy])		// 기존 등산로보다 낮은곳 탐색
    			{
    				visit[dx][dy] = true;
    				dfs(array,dx,dy,depth+1,construction);
    				visit[dx][dy] = false;
    			}
    			else if(construction == false)		// 공사 할때
    			{

    				int temp = array[dx][dy];
    				
    				if(array[x][y] > array[dx][dy] - K)
    				{
    					array[dx][dy] = array[x][y]-1;
    					visit[dx][dy] = true;

    					
    					dfs(array,dx,dy,depth+1,true);
    					
    					array[dx][dy] = temp;
    					visit[dx][dy] = false;

    				}
    			}

    		}
    	}
    }
}

