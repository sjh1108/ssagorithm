package study;
import java.io.*;
import java.util.*;

public class Main_16928_김회인 {
   
	static class node
	{
		int x;
		int y;
		
		node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	static int[] count = new int[101];
	static HashMap<Integer,Integer> hash;
   
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        hash = new HashMap<Integer, Integer>();
        
        for(int i=0; i<n+m; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	
        	hash.put(a,b);
        }
        Arrays.fill(count, Integer.MAX_VALUE);
        int mcount = bfs();
        
        
        
        bw.write(Long.toString(mcount));
        bw.flush();
        bw.close();
        br.close();
    }
 
    
    static int bfs()
    {
    	Queue<node> q = new LinkedList<>();
    	
    	q.add(new node(1,0));
    	count[1] = 0;
    	
    	while(true)
    	{
    		node cur_node = q.poll();
    		
    		int position = cur_node.x;
    		int mcount = cur_node.y;
    		int new_position=0;
    		for(int i=1; i<=6; i++)
    		{ 
    			if(hash.containsKey(position + i))new_position = hash.get(position+i);
    			else new_position = i+position;
    			
    			if(new_position == 100) return mcount+1;
    			if(count[new_position] <= mcount+1) continue;
    			count[new_position] = mcount+1;
    			q.add(new node(new_position,mcount+1));
    		}
    		
    	}
    }
   
}
