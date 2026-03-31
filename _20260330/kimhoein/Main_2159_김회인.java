package study;

import java.io.*;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;


public class Main_2159_김회인 {
    /*
     * 현재 출발점이 될 수 있는 노드 5개에서
     * 도착 점이 될 수 있는 노드 5개
     * dp에 최소 값을 저장해줘서
     * 100000 * 25 로 시간내에 해결 한다
     */
	static class Node
	{
		int x;
		int y;
		
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	
	static int[][] delta = {{0,0},{1,0},{0,1},{-1,0},{0,-1}};
   
    
    public static void main(String[] args) throws InterruptedException, IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st;//  = new StringTokenizer(br.readLine());
       
       int N = Integer.parseInt(br.readLine());
       st = new StringTokenizer(br.readLine());
       
       int x = Integer.parseInt(st.nextToken());
       int y = Integer.parseInt(st.nextToken());
      
       Node start = new Node(x,y);
       
       long[][] dp = new long[N][5];
       Node[][] costumer = new Node[N][5];
       
       for(int i=0; i<N; i++)
       {
    	   st = new StringTokenizer(br.readLine());
           
           x = Integer.parseInt(st.nextToken());
           y = Integer.parseInt(st.nextToken());
           
           //Node node = new Node(x,y);
           
           for(int j=0; j<5; j++)
           {
        	   int dx = x + delta[j][0];
        	   int dy = y + delta[j][1];
        	   
        	   costumer[i][j] = new Node(dx,dy);
           }
           Arrays.fill(dp[i], Long.MAX_VALUE);
       }
       
       for(int i=0; i<5; i++)
       {
    	   dp[0][i] = calculate(start, costumer[0][i]);
       }
      
       
       for(int i=1; i<N; i++)
       {
    	   for(int s=0;s <5; s++)
    	   {
    		   for(int e=0; e<5; e++)
    		   {
    			   int dist = calculate(costumer[i-1][s],costumer[i][e]);
    			   
    			   dp[i][e] = Math.min(dp[i][e], dp[i-1][s] + dist);
    		   }
    	   }
       }
       
       long min = Long.MAX_VALUE;
       
       
       for(int i=0; i<5; i++)
       {
    	   min = Math.min(min, dp[N-1][i]);
       }
       
       bw.write(Long.toString(min));
       bw.flush();
       bw.close();

    }
    
    static int calculate(Node node1, Node node2)
    {
    	return Math.abs(node1.x - node2.x) + Math.abs(node1.y - node2.y);
    }
    
}
