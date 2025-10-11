package study;

import java.io.*;
import java.util.*;

public class Main_10282_김회인 {
   
   static class Node implements Comparable<Node>
   {
      int to;
      int cost;
      
      Node(int to,int cost)
      {
         this.to = to;
         this.cost = cost;
      }

	  @Override
	  public int compareTo(Node o) {
		return this.cost - o.cost;
	  }
   }
 
   static int distance[];
   static ArrayList<Node>[] clist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        int t = Integer.parseInt(br.readLine());
        
        for(int i=0; i<t; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int n = Integer.parseInt(st.nextToken());
        	int d = Integer.parseInt(st.nextToken());
        	int c = Integer.parseInt(st.nextToken());
        	distance = new int[n+1];
        	clist = new ArrayList[n+1];
        	
        	for(int j=0; j<=n ;j++)
        	{
        		clist[j] = new ArrayList<>();
        	}
        	
        	for(int j=0; j<d;j++)
        	{
        		st = new StringTokenizer(br.readLine());
            	int a = Integer.parseInt(st.nextToken());
            	int b = Integer.parseInt(st.nextToken());
            	int s = Integer.parseInt(st.nextToken());
            	
            	clist[b].add(new Node(a,s));
            	
        	}
        	

        	Arrays.fill(distance, Integer.MAX_VALUE);
        	
        	da(c);
        	int max=0;
        	int count=0;
        	for(int j=0; j<distance.length;j++)
        	{
        		if(distance[j] != Integer.MAX_VALUE && max < distance[j]) max = distance[j];
        		
        		if(distance[j] != Integer.MAX_VALUE) count++;
        	}
        	bw.write(Integer.toString(count) + " " +Integer.toString(max)+"\n");
        }
        
        bw.flush();
        bw.close();
        br.close();
    }
    
    static public void da(int n)
    {
    	PriorityQueue<Node> pq = new PriorityQueue<>();
    	
    	pq.add(new Node(n,0));
    	distance[n] = 0;
    	while(!pq.isEmpty())
    	{
    		Node node = pq.poll();
    		
    		for(Node next : clist[node.to])
    		{
    			if(distance[next.to] <= distance[node.to] + next.cost) continue;
    			distance[next.to] = distance[node.to] + next.cost;
    			pq.add(new Node(next.to,distance[next.to]));
    		}
    	}
    }
}
