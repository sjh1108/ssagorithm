package study;

import java.util.*;
import java.io.*;

public class Main_1005_김회인{
	
    public static void main(String[] args)throws IOException{
       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        
        int t = Integer.parseInt(br.readLine());
        
        for(int T=1; T<=t; T++)
        {
        	
        	st = new StringTokenizer(br.readLine());
        	
        	int N = Integer.parseInt(st.nextToken());
        	int K = Integer.parseInt(st.nextToken());
        	int depth[] = new int[N+1];
        	int W;
        	int build_time[]  = new int[N+1];
        	int total_time[] = new int[N+1];
        	
        	ArrayList<ArrayList<Integer>> arraylist = new ArrayList<>();
        	
        	for(int i=0; i<=N; i++)
        	{
        		arraylist.add(new ArrayList<Integer>());
        	}
        	
        	st = new StringTokenizer(br.readLine());
        	for(int i=1; i<=N; i++)
        	{
        		build_time[i] = Integer.parseInt(st.nextToken());
        	}
        	
        	for(int i=0; i<K; i++)
        	{
        		st = new StringTokenizer(br.readLine());
        		int a = Integer.parseInt(st.nextToken());
        		int b = Integer.parseInt(st.nextToken());
        		
        		arraylist.get(a).add(b);
        		depth[b]++;
        	}
        	W = Integer.parseInt(br.readLine());
        	
        	Queue<Integer> q = new LinkedList<Integer>();
        	
        	for(int i=1; i<=N; i++)
        	{
        		if(depth[i] == 0)
        		{
        			q.add(i);
        			total_time[i] = build_time[i];
        		}
        	}
        	
        	while(!q.isEmpty())
        	{
        		int now = q.poll();
        		
        		for(int next : arraylist.get(now))
        		{
        			depth[next]--;
        			if(total_time[next] < total_time[now] + build_time[next])
        			{
        				total_time[next] = total_time[now] + build_time[next];        			
        			}
        			
        			if(depth[next] == 0)
        			{
        				q.add(next);
        			}
        		}
        	}
        	
        	bw.write(Integer.toString(total_time[W])+"\n");
        }
        
        
        bw.flush();
        bw.close();
        br.close();
    }
    
    
    
}