package study;
import java.io.*;
import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.*;

public class Main_2307_김회인 {
    
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
		public int compareTo(Node o)
		{
			return Integer.compare(this.dist, o.dist);
			
		}
	}
	
	static ArrayList<ArrayList<Node>> array;
	static int N;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        array = new ArrayList<>();
        int[] dist = new int[N+1];
        int[] parent = new int[N+1];
        
        
        
        for(int i=0; i<=N; i++)
        {
        	array.add(new ArrayList<Node>());
        }
        
        for(int i=0; i<M; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	int c = Integer.parseInt(st.nextToken());
        	
        	array.get(a).add(new Node(b,c));
        	array.get(b).add(new Node(a,c));
        }
        
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        
        pq.add(new Node(1,0));
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        
        
        while(!pq.isEmpty())
        {
        	Node node = pq.poll();
        	
        	//System.out.println("cur : "  + 1);
        	
        	if(dist[node.to] < node.dist) continue;
        	//System.out.println("cur : "  + node.to);
        	
        	for(Node cur : array.get(node.to))
        	{
        		//System.out.println("cur : "  + cur.to);
        		
        		if(dist[cur.to] <= node.dist + cur.dist) continue;
        		
        		dist[cur.to] = node.dist + cur.dist;
        		parent[cur.to] = node.to;
        		
        		pq.add(new Node(cur.to, dist[cur.to]));
        	}
        }
        
        //System.out.println("count : " + dist[N]);
        
        int count = N;
        int time =0;
        parent[0] = 1;
//        for(int i : parent)
//        {
//        	System.out.println("i : " + i);
//        }
        
        
        while(true)
        {
        	int cur_time = dijkstra(parent[count],count);
        	//System.out.println("count : " + count);
        	
        	if(cur_time == Integer.MAX_VALUE){
        		time = -1;
        		break;
        	}
        	time = Math.max(cur_time - dist[N], time);
        
        	if(count == 0) break;
        	count = parent[count];
        	
        }
        
      
        
        bw.write(Integer.toString(time));
        bw.flush();
        bw.close();
        
    }
      
    static int dijkstra(int a, int b)	// 출발지가 a 이고 도착지가 b인경우 pass
    {
    	PriorityQueue<Node> pq = new PriorityQueue<Node>();
    	int[] dist = new int[N+1];
        pq.add(new Node(1,0));
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        
        
        while(!pq.isEmpty())
        {
        	Node node = pq.poll();
        	
        	if(dist[node.to] < node.dist) continue;
        	
        	for(Node cur : array.get(node.to))
        	{
        		if((node.to == a && cur.to == b) || (node.to == b && cur.to == a)) continue;
        		if(dist[cur.to] <= node.dist + cur.dist) continue;
        		
        		dist[cur.to] = node.dist + cur.dist;
        		
        		
        		pq.add(new Node(cur.to, dist[cur.to]));
        	}
        }
    	return dist[N];
    }
    
}