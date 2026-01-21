
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class Main_13549_김회인 {

	static class Edge implements Comparable<Edge>
	{
		int to;
		int cost;
		
		Edge(int to, int cost)
		{
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return this.cost - o.cost;
		}
		
	}
	
	static int distance[];
	static int M,N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//System.out.println("aa");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		distance = new int[M*2+1];
		if(N>M) distance = new int[N+1];
		
		Arrays.fill(distance, Integer.MAX_VALUE);
		da(N);
		
		bw.write(Integer.toString(distance[M]));
		bw.flush();
		bw.close();
		br.close();

	}
	
	static void da(int start)
	{
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		
		//pq.add(new Edge(start,0));
		//System.out.println("in da");
		for(int i=start; i<distance.length; i*=2)
		{
			//System.out.println("a22a");
			distance[i] = 0;
			pq.add(new Edge(i,0));
			if(i==0) break;
		}
		
		while(!pq.isEmpty())
		{
			Edge e = pq.poll();
			
			//System.out.println(e.to  + " " + e.cost);
			
			//if(distance[e.to] < e.cost) continue;
			
			if(distance.length > e.to+1 && distance[e.to+1] > e.cost+1)
			{
				//System.out.println("a2");
				for(int i=e.to+1; i<distance.length; i*=2)
				{
					if(distance[i] < e.cost+1) continue;
					distance[i] = e.cost+1;
					pq.add(new Edge(i,e.cost+1));
				}
			}
			
			
			if(e.to-1 > -1 &&distance[e.to-1] > e.cost-1)
			{
				//System.out.println("a3");
				for(int i=e.to-1; i<distance.length; i*=2)
				{
					if(distance[i] < e.cost+1) continue;
					distance[i] = e.cost+1;
					pq.add(new Edge(i,e.cost+1));
					if(i==0) break;
				}
				
			}
			
		}
	}

}
