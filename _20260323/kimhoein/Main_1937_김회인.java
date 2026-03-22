package study;

import java.io.*;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;


public class Main_1937_김회인 {
    
	static class Node implements Comparable<Node>
	{
		int x;
		int y;
		int bamboo;
		
		Node(int x,int y, int bamboo)
		{
			this.x = x;
			this.y = y;
			this.bamboo = bamboo;
		}

		@Override
		public int compareTo(Node o) {
			return o.bamboo - this.bamboo;
		}
		
		
	}
	
	static int[][] map;
	static int[][] map_count;
	static int[][] delta = {{1,0},{0,1},{-1,0},{0,-1}};
	
	static PriorityQueue<Node> pq = new PriorityQueue<>();
	
	
    public static void main(String[] args) throws InterruptedException, IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st;//  = new StringTokenizer(br.readLine());
       
       int N = Integer.parseInt(br.readLine());
       
       int max=0;
       
       map = new int[N][N];
       map_count = new int[N][N];
       
       
       for(int i=0; i<N; i++)
       {
    	   st = new StringTokenizer(br.readLine());
    	   
    	   for(int j=0; j<N; j++)
    	   {
    		   int n = Integer.parseInt(st.nextToken());
    		   map[i][j] = n;
    		   Node node = new Node(i,j,n);
    		   pq.add(node);
    	   }
       }
       
       while(!pq.isEmpty())
       {
    	   Node node = pq.poll();
    	   //System.out.println("n : " + node.bamboo);
    	   for(int i=0; i<4; i++)
    	   {
    		   int dx = node.x + delta[i][0];
    		   int dy = node.y + delta[i][1];
    		   
    		   //System.out.println("dx : " + dx + " dy : " + dy);
    		   
    		   if(!(dx >= 0 && dx <N && dy >= 0 && dy <N)) continue;
    		   
    		   //System.out.println("for 내부 : " + map[dx][dy] + " "+ node.bamboo);
    		   if(map[dx][dy] <= node.bamboo) continue;
    		   map_count[node.x][node.y] = Math.max(map_count[node.x][node.y], map_count[dx][dy] + 1);
    		  
    	   }
    	   if(map_count[node.x][node.y] == 0) map_count[node.x][node.y] = 1;
    	   max = Math.max(map_count[node.x][node.y], max);
       }
       
//       for(int i=0; i<N; i++)
//       {
//    	   for(int j=0; j<N; j++)
//    	   {
//    		   System.out.print(map_count[i][j] + " ");
//    	   }
//    	   System.out.println("");
//       }
       
       bw.write(Integer.toString(max));
       bw.flush();
       bw.close();

    }
    
    
}
