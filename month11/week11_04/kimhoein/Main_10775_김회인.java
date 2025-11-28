package study;
import java.io.*;
import java.util.*;

class Main_10775_김회인 {
	static boolean[] visit;
	static int[] parent;
	
	static int find(int x)
	{
		if(parent[x] == x) return x;
		return parent[x] = find(parent[x]);
	}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        
        int g = Integer.parseInt(br.readLine());
        int p = Integer.parseInt(br.readLine());
        
        parent = new int[g+1];
        visit = new boolean[g+1];
        
        for(int i=0; i<=g; i++)
        {
        	parent[i] = i;
        }
        int count=0;
        for(int i=0; i<p; i++)
        {
        	int n = Integer.parseInt(br.readLine());
        	
        	//System.out.print("parent[n] : " + parent[n] + " ");
        	int t = find(n);
        	if(t == 0) break;
        	count++;
        	parent[t] = find(t-1);
        	
        	//System.out.println("t : " + t);
        }
       
        //System.out.println("find 4 : " + find(4) + " " +  "parent[3] : " + parent[3]);
        bw.write(Long.toString(count));
        bw.flush();
        bw.close();
        br.close();

    }
    
}