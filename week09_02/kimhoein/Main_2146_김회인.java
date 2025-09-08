package study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2146_김회인 {
	static class Node implements Comparable<Node>
	{
		int x;
		int y;
		int count;
		Node(int x, int y, int count)
		{
			this.x = x;
			this.y = y;
			this.count = count;
		}
		
		@Override
		public int compareTo(Node o) {
			return this.count - o.count;
		}
		
		
		
	}
	static int N;
	static int map[][];
	static int delta[][] = {{-1,0},{1,0},{0,-1},{0,1}};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;//= new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		
		for(int i=0; i<N; i++)
		{
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<N; j++)
			{
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int count=2;
		
		for(int i=0; i<N; i++)
		{		
			for(int j=0; j<N; j++)
			{
				if(map[i][j] == 1)
				{
					bfs_count(i,j,count++);
				}
			}
		}
		
//		
//		for(int i=0; i<N; i++)
//		{		
//			for(int j=0; j<N; j++)
//			{
//				System.out.print(map[i][j] + " ");
//			}
//			System.out.println("");
//		}
		
		int min = Integer.MAX_VALUE -1;
		
		for(int i=2; i<count; i++)
		{
			boolean visit[][] = new boolean[N][N];
			int b_length = bfs_bridge(visit,i);
			if(min > b_length) min = b_length; 
		}
		
		bw.write(Integer.toString(min));
		bw.flush();
		bw.close();
		br.close();
		
	}
	
	static void bfs_count(int x, int y,int count)
	{
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(x,y,0));
		map[x][y] = count;
		
		while(!q.isEmpty())
		{
			Node node = q.poll();
			
			for(int i=0; i<4; i++)
			{
				int dx = node.x + delta[i][0];
				int dy = node.y + delta[i][1];
				
				if(!(dx>=0 && dx <N && dy>=0 && dy<N)) continue;
				
				//System.out.println(dx + " " + dy);
				
				if(map[dx][dy]!=1) continue;
				map[dx][dy] = count;
				q.add(new Node(dx,dy,0));
			}
		}
		
	}
	
	static int bfs_bridge(boolean visit[][],int count)
	{
		Queue<Node> q = new LinkedList<>();
		//q.add(new Node(x,y,0));
		
//		for(int i=0; i<N; i++)		// 섬의 바깥을 구하는 거
//		{		
//			for(int j=0; j<N; j++)
//			{
//				if(count!=map[i][j])
//					
//				for(int i2=0; i2<4; i2++)
//				{
//					int dx = i + delta[i2][0];
//					int dy = j + delta[i2][1];
//					
//					if(!(dx>=0 && dx <N && dy>=0 && dy<N)) continue;
//					
//					//System.out.println(dx + " " + dy);
//					
//					if(map[dx][dy]!=0) continue;
//					q.add(new Node(dx,dy,0));
//					break;
//				}
//			}
//		}
//		
		for(int i=0; i<N; i++)
		{
			for(int j=0; j<N; j++)
			{
				if(map[i][j] == count)
				{
					q.add(new Node(i,j,0));
					//visit[i][j] = true;
				}
			}
		}
		//System.out.println("size : " + q.size());
		
		
		while(!q.isEmpty())
		{
			Node node = q.poll();
			
			for(int i=0; i<4; i++)
			{
				int dx = node.x + delta[i][0];
				int dy = node.y + delta[i][1];
				
				if(!(dx>=0 && dx <N && dy>=0 && dy<N)) continue;
				if(visit[dx][dy]) continue;
				//if(map[dx][dy]!=0) continue;
				
				if(map[dx][dy] !=0 && map[dx][dy] !=count) return node.count;
				
				if(map[dx][dy] == 0)
				{
					
					visit[dx][dy] = true;
					q.add(new Node(dx,dy,node.count+1));
				}
				
			}
		}
		
		return -1;
	}

}
