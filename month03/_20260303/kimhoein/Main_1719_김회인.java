package study;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main_1719_김회인 {
    
    static class Node implements Comparable<Node>
    {
        int to;
        int dist;
        int start=0;
        
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
    static int[][] map;
    static int n;
    static ArrayList<ArrayList<Node>> array;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        map = new int[n+1][n+1];
        
        array = new ArrayList<>();
        
        for(int i=0; i<=n; i++)
        {
            array.add(new ArrayList<>());
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
        
        for(int i=1; i<=n; i++)
        {
        	dijkstra(i);
        }
        
        for(int i=1; i<=n; i++)
        {
        	for(int j=1; j<=n; j++)
        	{
        		if(i == j)
        		{
        			bw.write("- ");
        		}
        		else
        		{
        			bw.write(Integer.toString(map[i][j]) + " ");
        		}
        	}
        	
        	bw.write("\n");
        }
        
        //bw.write(Long.toString(total));
        bw.flush();
        bw.close();
        
    }
    
    static void dijkstra(int start)
    {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[n+1];
        pq.add(new Node(start, 0));
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        
        while(!pq.isEmpty())
        {
            Node node = pq.poll();
            
            if(dist[node.to] < node.dist) continue;
            
            for(Node cur : array.get(node.to))
            {
            	if(dist[cur.to] <=  cur.dist + node.dist) continue;
            	
            	dist[cur.to] = cur.dist + dist[node.to];
            	
            	Node n = new Node(cur.to,dist[cur.to]);
            	
            	if(node.start == 0) n.start = cur.to;
            	else n.start = node.start;
            	
            	map[start][cur.to] = n.start;
            	
            	pq.add(n);
            }
        }
        
    }
}