package study;

import java.io.*;
import java.util.*;

public class Main_2643_김회인 {
	static class Node implements Comparable<Node>
	{
		int x;
		int y;
		
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Node o) {
			if(this.x != o.x)
			{
				return this.x - o.x;				
			}
			else
			{
				return this.y - o.y;		
			}
		}
	}
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(br.readLine());
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        
        for(int i=0; i<N; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	if(a < b)
        	{
        		int temp = a;
        		
        		a = b;
        		b = temp;
        	}
        	pq.add(new Node(a,b));
        }
        
        int array[] = new int[N];
        
        for(int i=0; i<N; i++)
        {
        	//System.out.println(pq.peek().x + " array[i] : " + pq.peek().y);
        	array[i] = pq.poll().y;
        	
        }
        //int n = array.length;
        int dp[] = new int[N];
        
        
        for(int i=0; i< N; i++) {
        	dp[i] = 1;
        	
            for(int j=0; j<i; j++) {
                if(array[j] <= array[i]) {
                	dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            
            //System.out.println(dp[i]);
        }
        
        Arrays.sort(dp);
        
        System.out.println(dp[N-1]);
        
        
        
        bw.flush();
        bw.close();
        br.close();
    }
    
}
