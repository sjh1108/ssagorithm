package study;
import java.io.*;
import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.*;

public class Main_5719_김회인 {
    /*
     * n m 입력
	start end 입력
	
	다익한번 돌리고
	start end 제외 하고 못가게 막을것
	shortcut 체크 법은 parent 만들어서 
	짧은거 나올때마다 parent로 뒤로 올라가면서 shortcut 체크
	shortcut[] 만들어서 bool로 만들어서 체크
	
	다익-> parent 완성 -> shortcut 완성 -> shortcut 있는 채로 다익

     */
	
	static class Node implements Comparable<Node>
	{
		int to;
		int dist;
		
		Node(int to, int dist)
		{
			this.to = to;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			
			return this.dist - o.dist;
		}

	}
    
	static int n;
	static ArrayList<ArrayList<Node>> array; 
	static ArrayList<ArrayList<Node>> array_reverse;
	//static int[] parent;
	static boolean[][] shortcut;
	static int dist[];
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        //ArrayList<ArrayList<Node>> array; 
        
        
        while(true)
        {
        	st = new StringTokenizer(br.readLine());
        	
        	n = Integer.parseInt(st.nextToken());
        	int m = Integer.parseInt(st.nextToken());
        	
        	
        	if(n == 0 && m == 0) break;
        	st = new StringTokenizer(br.readLine());
        	
        	int start = Integer.parseInt(st.nextToken());
        	int end = Integer.parseInt(st.nextToken());
        	
        	array = new ArrayList<>();
        	array_reverse = new ArrayList<>();
        	
        	//int dist[] = new int[n];
        	//parent = new int[n];
        	shortcut = new boolean[n][n];
        	
        	for(int i=0; i<n; i++)
        	{
        		array.add(new ArrayList<Node>());
        		array_reverse.add(new ArrayList<Node>());
        	}
        	
        	for(int i=0; i<m; i++)
        	{
        		st = new StringTokenizer(br.readLine());
        		int a = Integer.parseInt(st.nextToken());
        		int b = Integer.parseInt(st.nextToken());
        		int c = Integer.parseInt(st.nextToken());
        				
        		array.get(a).add(new Node(b,c));
        		array_reverse.get(b).add(new Node(a,c));
        		
        		//array.get(b).add(new Node(a,c));
        	}
        	
        	Dijkstra_shortcut(start);
        	
        	if(dist[end] == Integer.MAX_VALUE)
        	{
        		bw.write("-1\n");
        		continue;
        	}
        	make_shortcut(start, end);
        	Dijkstra_shortcut(start);
        	
        	if(dist[end] == Integer.MAX_VALUE)
        	{
        		bw.write("-1\n");
        	}
        	else
        	{
        		bw.write(Integer.toString(dist[end]) + "\n");
        	}
        	
        }
        //bw.write(Long.toString(total));
        bw.flush();
        bw.close();
        
    }
    
    static void Dijkstra_shortcut(int start)
    {
    	PriorityQueue<Node> pq = new PriorityQueue<>();
    	
    	pq.add(new Node(start, 0));
    	dist = new int[n];
    	
    	Arrays.fill(dist, Integer.MAX_VALUE);
    	
    	dist[start] = 0;
    	
    	while(!pq.isEmpty())
    	{
    		Node node = pq.poll();
    		
    		//if(shortcut[node.to]) continue;
    		
    		if(dist[node.to] < node.dist) continue;
    		
    		for(Node cur : array.get(node.to))
    		{
    			if(shortcut[node.to][cur.to]) continue;
    			if(dist[cur.to] <= cur.dist + node.dist) continue;
    			
    			dist[cur.to] = cur.dist + node.dist;
    			//.cur.[cur.to] = node.to;
    			
    			pq.add(new Node(cur.to,dist[cur.to]));
    		}
    		
    	}
    	
    }
    
    static void make_shortcut(int start, int end)
    {
    	Queue<Node> pq = new LinkedList<>();
    	boolean[] visited = new boolean[n];
    	visited[end] = true;
    	
    	pq.add(new Node(end,0));
    	
    	while(!pq.isEmpty())
    	{
    		Node node = pq.poll();
    		
    		for(Node cur : array_reverse.get(node.to))
    		{	
    			if(dist[node.to] != cur.dist + dist[cur.to]) continue;
    			shortcut[cur.to][node.to] = true;
    			
    			if(visited[cur.to]) continue;
    			
    			
    			visited[cur.to] = true;
    			pq.add(new Node(cur.to,cur.dist));
    		}
    	}
    }
    
    
}