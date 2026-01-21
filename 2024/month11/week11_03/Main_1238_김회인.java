import java.io.*;
import java.util.*;

public class Main_1238_김회인 {
	
	static class Node implements Comparable<Node>
	{
		int to;
		int distance;
		
		Node(int to, int distance)
		{
			this.to = to;
			this.distance = distance;
		}

		@Override
		public int compareTo(Node o) {
			
			return Integer.compare(this.distance, o.distance);
		}
		
		
		
	}
	
	static int N;
	static int M;
	static int X;
	
	static List<List<Node>> list;
	static List<List<Node>> list_r;
	
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	N = Integer.parseInt(st.nextToken());
    	M = Integer.parseInt(st.nextToken());
    	X = Integer.parseInt(st.nextToken());
    	
    	list = new LinkedList<List<Node>>();
    	list_r = new LinkedList<List<Node>>();
    	
    	int[] dist = new int[N+1];
    	int[] dist_r = new int[N+1];
    	int[] dist_total = new int[N+1];
    	
    	for(int i=0; i<=N; i++) {
    		list.add(new LinkedList<Node>());
    		list_r.add(new LinkedList<Node>());
    	}
    	
    	for(int i=0; i<M; i++)
    	{
    		st = new StringTokenizer(br.readLine());
    		int a = Integer.parseInt(st.nextToken());
    		int b = Integer.parseInt(st.nextToken());
    		int c = Integer.parseInt(st.nextToken());
    		list.get(a).add(new Node(b,c));
    		list_r.get(b).add(new Node(a,c));
    	}
    	Dijkstra(dist,list);
    	Dijkstra(dist_r,list_r);
    	
    	for(int i=0; i<=N; i++)
    	{
    		//System.out.println("i : " + dist[i] + " " + dist_r[i]);
    		if(dist[i] == Integer.MAX_VALUE) dist[i] =0;
    		if(dist_r[i] == Integer.MAX_VALUE) dist_r[i] = 0;
    		dist_total[i] = dist[i] + dist_r[i];
    	}
    	
    	Arrays.sort(dist_total);
    	
    	bw.write(Integer.toString(dist_total[N]));
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
    public static void Dijkstra(int dist[], List<List<Node>> list_temp)
    {
    	//dist = new int[N+1];
    	
    	Arrays.fill(dist, Integer.MAX_VALUE);
    	
    	PriorityQueue<Node> pq = new PriorityQueue<Main_1238_김회인.Node>();
    	
    	pq.add(new Node(X,0));
    	dist[X] = 0;
    	//System.out.println("a");
    	
    	while(!pq.isEmpty())
    	{
    		Node n = pq.poll();
    		//System.out.println("n : " + n.distance);
    		if(dist[n.to] < n.distance) continue;
    		
    		for(Node next : list_temp.get(n.to))
    		{
    			//System.out.println("n : " + next.to + " " + next.distance);
    			if(dist[next.to] <= next.distance + dist[n.to]) continue;
    			
    			//System.out.println("a");
    			dist[next.to] = next.distance + dist[n.to];
    			
    			
    			pq.add(new Node(next.to,dist[next.to]));
    				
    		}
    	}
    }

}
