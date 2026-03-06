package study;
import java.io.*;
import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.*;

public class Main_1939_김회인 {
    
	static class Node implements Comparable<Node>
	{
		int to;
		int weight;
		
		Node(int to, int weight)
		{
			this.to = to;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Node o) {
			
			return Integer.compare(o.weight, this.weight);
		}
	}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        int[] max_weight = new int[n+1];
        
        ArrayList<ArrayList<Node>> array = new ArrayList<>();
        
        for(int i=0; i<=n; i++)
        {
        	array.add(new ArrayList<Node>());
        }
        
        for(int i=0; i<m; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	int c = Integer.parseInt(st.nextToken());
        	
        	array.get(a).add(new Node(b,c));
        	array.get(b).add(new Node(a,c));
        }
        
        st = new StringTokenizer(br.readLine());
        
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        
        pq.add(new Node(start,Integer.MAX_VALUE));
        
        Arrays.fill(max_weight, -1);
        max_weight[start] = Integer.MAX_VALUE;
        
        while(!pq.isEmpty())
        {
        	Node node=  pq.poll();
        	
        	if(max_weight[node.to] > node.weight) continue;
        	//System.out.println("node.to : " + node.to);
        	
        	for(Node cur : array.get(node.to))
        	{
        		//System.out.println("cur.to : " + cur.to);
        		
        		if(max_weight[cur.to] >= Math.min(cur.weight, max_weight[node.to])) continue;
        		
        		//System.out.println("cur.to : " + cur.to);
        		max_weight[cur.to] = Math.min(cur.weight, max_weight[node.to]);
        		
        		pq.add(new Node(cur.to,max_weight[cur.to]));
        	}
        		
        }
        
        bw.write(Integer.toString(max_weight[end]));
        
        bw.flush();
        bw.close();
        
    }
       
    
}