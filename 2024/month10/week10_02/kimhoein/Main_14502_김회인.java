package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_14502_김회인 {
	
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
	
	static List<Node> blankSpace_list = new ArrayList<>();
	static List<Node> virus_list = new ArrayList<>();
	static int r;
	static int c;
	static int max = 0;
	static int[][]delta = {{1,0},{-1,0},{0,1},{0,-1}};
	static int[][] map;
	static int[][] map_copy;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new int[r][c];
		map_copy = new int[r][c];
		
		for(int i=0; i<r; i++)
		{
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<c;j++)
			{
				map[i][j] = Integer.parseInt(st.nextToken());
				
				if(map[i][j] == 0) blankSpace_list.add(new Node(i,j));
				if(map[i][j] == 2) virus_list.add(new Node(i,j));
			}
		}
		
		List<Node> wall_list = new LinkedList<>();
		combine(0,0,wall_list);
		bw.write(Integer.toString(max));
		bw.flush();
		bw.close();
		br.close();
	}
	
	static public void combine(int wall_count,int depth, List<Node> wall_list)
	{
		//System.out.println("asd");
		if(wall_count == 3)
		{
			//System.out.println("wall");
			int m = bfs(wall_list);
			
			if(max <m) max = m;
			return;
		}
		
		if(depth == blankSpace_list.size()) return;
		
		//System.out.println("asd");
		
		wall_list.add(blankSpace_list.get(depth));
		combine(wall_count + 1, depth+1, wall_list);
		
		wall_list.remove(blankSpace_list.get(depth));
		combine(wall_count, depth+1, wall_list);
		
	}
	
	static public int bfs(List<Node> wall_list)
	{
		boolean visit[][] = new boolean[r][c];
		copy_map();
		
		for(Node wall : wall_list)
		{
			visit[wall.x][wall.y] = true;
			map_copy[wall.x][wall.y] = 1;
		} 
		
		Queue<Node> q = new LinkedList<>();
		
		for(Node virus : virus_list)
		{
			q.add(virus);
		}
		
		
		while(!q.isEmpty())
		{
			//System.out.println("waile");
			
			Node cur= q.poll();
			
			for(int i=0; i<4; i++)
			{
				int dx = cur.x + delta[i][0];
				int dy = cur.y + delta[i][1];
				
				if(!(dx >= 0 && dx<r && dy>=0 && dy <c)) continue;
				if(visit[dx][dy] || map[dx][dy] == 1) continue;
				
				visit[dx][dy] = true;
				map_copy[dx][dy] = 2;
				q.add(new Node(dx,dy));
			}
		}
		
		int count=0;
		
		for(int i=0; i<r; i++)
		{
			for(int j=0; j<c; j++)
			{
				if(map_copy[i][j] == 0) count++;
			}
		}
		
		return count;
	}
	
	static public void copy_map()
	{
		for(int i=0;i<r; i++)
		{
			System.arraycopy(map[i], 0, map_copy[i], 0, c);
		}
	}
}
