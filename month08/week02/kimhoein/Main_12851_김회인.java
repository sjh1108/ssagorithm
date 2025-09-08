package month08.week02.kimhoein;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_12851_김회인 {
	static int count=0;
	static int second=-1;
	static int K;
	static Queue <SimpleEntry<Integer,Integer>> q = new LinkedList<>();
	static int visit[];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		

		int N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		visit = new int[100001];
		//second = K-N;
		
		//depth(N,0);
		bfs(N);
		second++;
		
		bw.write(Integer.toString(second) + "\n" + Integer.toString(count));
		bw.flush();
		bw.close();
	}	
	
	static void bfs(int N)
	{
		q.add(new SimpleEntry(0,N));
		visit[N] = 1;
		
		while(!q.isEmpty())
		{
			SimpleEntry s = q.poll();
			int d = (int) s.getKey();
			int start = (int) s.getValue();
			
			if(N == K)
			{
				second =-1;
				count = 1;
				break;
			}
			
			//System.out.println("d : " + d + "start" + start);
			if(second == -1 ||  d <= second)
			{
				if(K == start+1)
				{
					//System.out.println("+1 : " + start );
					count++;
					second = d;
				}
				
				if(K == start-1)
				{
					count++;
					//System.out.println("-1 : " + start + " : " + d + " : " + second);
					second = d;
				}
				
				if(K == start*2)
				{
					//System.out.println("*2 : " + start );
					count++;
					second = d;
				}
			}
			
			
			if(second == -1 ||  d < second)
			{
				if((start-1 >=0 && start-1 < 100001) && (visit[start-1] == 0 || visit[start-1] == d+1))
				{
					visit[start-1] = d+1;
					q.add(new SimpleEntry(d+1,start-1));					
				}
				
				if(start < K)
				{
					if((start+1 >=0 && start+1 < 100001) && (visit[start+1] == 0 || visit[start+1] == d+1))
					{
						visit[start+1] = d+1;
						q.add(new SimpleEntry(d+1,start+1));
					}
					
					if((start*2 >=0 && start*2 < 100001) && (visit[start*2] == 0 || visit[start*2] == d+1))
					{
						visit[start*2] = d+1;
						q.add(new SimpleEntry(d+1,start*2));			
					}
					
				}
				
			}
		}
	}
	
	static void depth(int N,int d)
	{
		if(N == K) // 동생을 찾음
		{
			if(d < second) // 더 빠른 방법 찾음
			{
				second = d;
				count = 1;
			}
			else if(d == second)
			{
				count++;
			}
		}
		
		if(d < second)
		{
			depth(N+1,d+1);
			
			depth(N-1,d+1);
			
			depth(N*2,d+1);			
		}
		
	}
	

}

