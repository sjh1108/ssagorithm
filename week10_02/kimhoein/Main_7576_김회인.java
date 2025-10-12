package study;
import java.io.*;
import java.util.*;

public class Main_7576_김회인 {
   
	static class Node
	{
		int x;
		int y;
		int count=0;
		
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	static int r;
	static int c;
	static int map[][];
	static List<Node> ripe = new LinkedList<>();
	static int[][] delta = {{1,0},{-1,0},{0,1},{0,-1}};
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        c = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        map = new int[r][c];
        
        for(int i=0; i<r; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	for(int j=0; j<c; j++)
        	{
        		map[i][j] = Integer.parseInt(st.nextToken());
        		if(map[i][j] == 1) ripe.add(new Node(i,j));
        	}
        }
        //System.out.println("aa");
        bw.write(Long.toString(bfs()));
        bw.flush();
        bw.close();
        br.close();
    }
    
    static int bfs()
    {
    	//System.out.println("bfs");
    	Queue<Node> q = new LinkedList<>();
    	
    	for(Node n : ripe)
    	{
    		q.add(n);
    	}
    	int cur_count=0;
    	
    	while(!q.isEmpty())
    	{
    		//System.out.println("while");
    		Node n = q.poll();
    		
    		for(int i=0;i<4;i++)
    		{
    			int dx = n.x + delta[i][0];
    			int dy = n.y + delta[i][1];
    			
    			if(!(dx>=0 && dx <r && dy >=0 && dy<c)) continue;
    			if(map[dx][dy] != 0) continue;
    			
    			map[dx][dy] = 1;
    			Node node = new Node(dx,dy);
    			node.count = n.count+1;
    			cur_count = node.count;
    			q.add(node);
    				
    		}
    		
    	}
    	
    	return check(cur_count);
    }
    
    static int check(int count)
    {
    	
    	for(int i=0; i<r;i++)
    	{
    		for(int j=0; j<c;j++)
    		{
    			if(map[i][j] == 0) return -1;

    		}
    	}
    	
    	return count;
    }
 
   
}
