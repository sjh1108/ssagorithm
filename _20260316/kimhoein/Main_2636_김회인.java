package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2636_김회인 {
	
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
	
	static int delta[][] = {{-1,0},{1,0},{0,-1},{0,1}};
	static int col;
	static int row;
	static boolean visit[][];
	static int map[][];
	static int hour=0;
	static int count=0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		col = Integer.parseInt(st.nextToken());
		row = Integer.parseInt(st.nextToken());
		
		count=0;
		hour=0;
		
		map = new int[col][row];
		//visit = new boolean[col][row];
		
		for(int i=0; i<col; i++)
		{
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<row; j++)
			{
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0; i<col; i++)
		{
			for(int j=0; j<row; j++)
			{
				if(map[i][j] != 1) continue;
				visit = new boolean[col][row];
				//System.out.println("bfs !");
				hour++;
				bfs(0,0);
			}
		}
		
		bw.write(Integer.toString(hour) + "\n" + Integer.toString(count));
		
		bw.flush();
		bw.close();
		br.close();

	}
	
	
	static void bfs(int x, int y)
	{
		count=0;
		Queue<Node> q = new LinkedList<>();
		
		//Queue<Node> cheese = new Linked
		q.add(new Node(x,y));
		visit[x][y] = true;
		//System.out.println("bfs  시작 " + x + " " + y);
		
		while(!q.isEmpty())
		{
			Node node = q.poll();
			
			for(int i=0; i<4; i++)
			{
				int dx = node.x + delta[i][0];
				int dy = node.y + delta[i][1];
				
				if(!(dx>=0 && dx <col && dy >=0 && dy<row)) continue;
				
				if(visit[dx][dy]) continue;
				
				visit[dx][dy] = true;
				
				if(map[dx][dy] == 1) 
				{
					//System.out.println(dx + " " + dy);
					map[dx][dy] = 0;
					count++;
				}
				else q.add(new Node(dx,dy));
				
			}
		}
		
		
	}
}
