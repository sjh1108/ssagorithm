package study;
import java.io.*;
import java.util.*;

public class Main_11438_김회인 {
	static ArrayList<Integer>[] tree;
	static int[][] parent;
	static int[] depth;
	static boolean[] visited;
	static int N;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(br.readLine());
        
        tree = new ArrayList[N+1];
        parent = new int[21][N+1];
        depth = new int[N+1];
        visited = new boolean[N+1];
        
        for(int i=0; i<=N; i++)
        {
        	tree[i] = new ArrayList<>();
        }
        
        for(int i=0; i<N-1; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	tree[a].add(b);
        	tree[b].add(a);
        	
        }
        
        int M = Integer.parseInt(br.readLine());
        
        dfs(1,0);
        dp();
        
        for(int i=0; i<M; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	
        	int r = lca(a,b);
        	//if(r == 0) r=1;
        	bw.write(Integer.toString(r) + "\n");
        }
        
        bw.flush();
        bw.close();
        br.close();
    
    }
    
    static void dfs(int node, int d)
    {
    	visited[node] = true;
    	depth[node]= d;
    	//System.out.println("123node : " + node);
    	for(int next : tree[node])
    	{
    		//System.out.println("123node : " + node);
    		if(visited[next]) continue;
    		
    		parent[0][next] = node; 
    		//System.out.println("node : " + node);
    		dfs(next,d+1);
    	}
    }
    
    static void dp()
    {
    	for(int k=1; k<21; k++)
    	{
    		for(int v = 1; v<=N; v++)
    		{
    			if(parent[k-1][v] != 0) parent[k][v] = parent[k-1][parent[k-1][v]];
    		}
    	}
    }
    
    static int lca(int a, int b)
    {
    	if(depth[a] < depth[b])
    	{
    		int temp = a;
    		a = b;
    		b = temp;
    	}
    	
    	for(int k=20; k>=0; k--)
    	{
    		int jump = (int) Math.pow(2, k);
    		if(depth[a] - depth[b] >= jump)
    		{
    			a = parent[k][a];
    		}
    	}
    	//System.out.println("lca");
    	if(a == b) return a;
    	
    	for(int k=20; k>=0; k--)
    	{
    		if(parent[k][a] != 0 && parent[k][a] != parent[k][b])
    		{
    			a = parent[k][a];
    			b = parent[k][b];
    		}
    	}
    	
    	return parent[0][a];
    }
   
   
}
