package study;

import java.io.*;
import java.util.*;

public class Main_11437_김회인 {
	public static int[] parent;
	public static int[] depth;
	public static ArrayList<Integer>[] tree;
	public static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(br.readLine());
        
        tree = new ArrayList[N+1];
        parent = new int[N+1];
        depth = new int[N+1];
        visited = new boolean[N+1];
    	
        for(int i=1; i<=N; i++)
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
        
        dfs(1,0);
        
        int M = Integer.parseInt(br.readLine());
        
        for(int i=0; i<M; i++) {
        	st = new StringTokenizer(br.readLine());
        	int a= Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	int r = lca(a,b);
        	bw.write(Integer.toString(r) + "\n");
        }
        
        
        bw.flush();
        bw.close();
        br.close();
    
    }
    
    static void dfs(int node, int d)
    {
    	visited[node] = true;
    	
    	depth[node] = d;
    	
    	for(int next : tree[node])
    	{
    		if(!visited[next])
    		{
    			parent[next] = node;
    			dfs(next, d+1);
    		}
    	}
    }
    
    public static int lca(int a, int b)
    {
    	if(depth[a] < depth[b])
    	{
    		int temp = a;
    		a = b;
    		b = temp;
    	}
    	while(depth[a] != depth[b]) {
    		a = parent[a];
    	}
    	
    	while(a != b)
    	{
    		a = parent[a];
    		b = parent[b];
    	}
    	return a;
    }
 
   
}
