package study;


import java.util.*;
import java.io.*;

public class Main_4485_김회인{
	
	static class Node implements Comparable<Node>
	{
		int x;
		int y;
		int cost;
		
		public Node(int x, int y, int cost)
		{
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.cost - o.cost;
		}
		
	}
	static int array[][];
	static int total_cost[][];
	static int delta[][] = {{1,0},{-1,0},{0,1},{0,-1}};
	static int N;
	static int INF = Integer.MAX_VALUE;
	
    public static void main(String[] args)throws IOException{
       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        int count=0;
        
        while(true)
        {
        	
        	N = Integer.parseInt(br.readLine());
        	count++;
        	if(N==0) break;
        	
        	array = new int[N][N];
        	total_cost = new int[N][N];
        	
        	for(int i=0; i<N; i++)
        	{
        		st = new StringTokenizer(br.readLine());
        		for(int j=0; j<N; j++)
        		{
        			
        			array[i][j] = Integer.parseInt(st.nextToken());
        			total_cost[i][j] = INF;
        		}
        	}
        	
        	bfs();
        	
        	bw.write("Problem " +Integer.toString(count) + ": "+ Integer.toString(total_cost[N-1][N-1])+"\n");
        	
        }
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static void bfs()
    {
    	PriorityQueue<Node> pq = new PriorityQueue<>();
    	int x=0;
    	int y=0;
    	
    	pq.add(new Node(x,y,array[x][y]));
    	total_cost[x][y] = array[x][y];
    	
    	while(!pq.isEmpty())
    	{
    		Node node = pq.poll();
    		
    		for(int i=0; i<4; i++)
    		{
    			int dx = node.x + delta[i][0];
    			int dy = node.y + delta[i][1];
    			
    			if(dx>=0 && dx <N && dy >=0 && dy <N)
    			{
    				if(array[dx][dy] > total_cost[dx][dy]) continue;
    				
    				if(total_cost[dx][dy] > array[dx][dy] + total_cost[node.x][node.y])
    				{
    					total_cost[dx][dy] = array[dx][dy] + total_cost[node.x][node.y];
    					//System.out.println("total_cost[dx][dy] : " + total_cost[dx][dy]);
    					pq.add(new Node(dx,dy,total_cost[dx][dy]));
    				}
    			}
    		}
    	}
    }
    
    
}