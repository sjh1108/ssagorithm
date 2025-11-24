package study;

import java.io.*;
import java.util.*;

class Main_1725_김회인 {
	
	static class Node implements Comparable<Node>
	{
		int h;
		int index;
		
		Node(int h,int index)
		{
			this.h = h;
			this.index = index;
		}

		@Override
		public int compareTo(Node o) {
			
			if(this.h == o.h) return this.index - o.index;
			return this.h - o.h;
		}

	}
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        int[] arr_r = new int[N];
        
        PriorityQueue<Node> pq = new PriorityQueue<>(Collections.reverseOrder());
        
        int total = 0;
        
        for(int i=0; i<N; i++)
        {
        	int n = Integer.parseInt(br.readLine());
        	arr[i] = n;
        	while(!pq.isEmpty() && n<pq.peek().h)
        	{

    			Node node = pq.poll();
    			//System.out.println("node.h * (i - node.index+1) : " + " node.h " + node.h + " i " + i + " node.index " + node.index + " " + (node.h * (i - node.index)));
    			//total = Math.max(total,node.h * (i - node.index));
    			arr_r[node.index] = i-node.index - 1; 
        	}
        	pq.add(new Node(n,i));
        }
        
        while(!pq.isEmpty())
    	{
        	//System.out.println("b");
			Node node = pq.poll();
			//System.out.println("N : " + N + " node.indext : " + node.index + " " + node.h + " " + (N - node.index+1) + " " + (node.h * (N - node.index+1)));
			//total = Math.max(total,node.h * (N - node.index+1));
			arr_r[node.index] = N - node.index-1;
    	}
        
        //System.out.println("total : " + total);
        
//        for(int i=0; i<N; i++)
//        {
//        	System.out.println(" arr[i] * arr_r[i]" + arr[i] + " " +  arr_r[i] + " " +  (arr[i] * (arr_r[i]+1)));
//        	//total = Math.max(total, arr[i] * (arr_r[i]+1));
//        }
//        System.out.println("");
        for(int i=N-1; i>=0; i--)
        {
        	int n = arr[i];
        	while(!pq.isEmpty() && n<pq.peek().h)
        	{

    			Node node = pq.poll();
    			//System.out.println("node.h * (i - node.index+1) : " + (node.h * (i - node.index)));
    			//total = Math.max(total,node.h * (node.index - i));
    			arr_r[node.index] += node.index - i - 1;
        	}
        	pq.add(new Node(n,i));
        }
        
        while(!pq.isEmpty())
    	{
        	//System.out.println("b");
			Node node = pq.poll();
			//System.out.println("N : " + N + " node.indext : " + node.index + " " + node.h + " " + (N - node.index+1) + " " + (node.h * (N - node.index+1)));
			//total = Math.max(total,node.h * (node.index+1));
			arr_r[node.index] += node.index;
    	}
        
        for(int i=0; i<N; i++)
        {
        	//System.out.println(" arr[i] * arr_r[i]" + arr[i] + " " +  arr_r[i] + " " +  (arr[i] * (arr_r[i]+1)));
        	total = Math.max(total, arr[i] * (arr_r[i]+1));
        }
        bw.write(Long.toString(total));
        bw.flush();
        bw.close();
        br.close();

    }
    
}