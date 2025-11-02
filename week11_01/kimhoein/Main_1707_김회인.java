package study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
/*
 * + 이분 그래프란 2개의 집합으로 나눌때 서로 닿지 않는 정점 끼리의 집합을 의미
 * 1. 모든 그래프 덩어리를 방문 한다
 * 2. 그래프 첫 방문지에는 1로 표시 하고 시작
 * 3. 그래프와 연결된곳으로 이동 출발지가 1일때는 2로 출발지가 2로 표시 되어있다면 1로 표시
 * 4. 이미 표시가 된곳인데 출발지와 도착지의 숫자가 같다? -> 이분 그래프가 아님
 * 5. 끝까지 아무 문제 없으면 이분 그래프
 * + 서로 연결 되지 않은 그래프 덩어리가 여러개 더라도 각각의 모든 덩어리가 이분그래프가 성립한다면 모든 그래프 덩어리로 이분 그래프 만드려고 해도 이분 그래프 성립
 */

public class Main_1707_김회인 {
	static int[] visit;
	static List<Integer>[] list;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	
    	int t = Integer.parseInt(br.readLine());
    	
    	for(int T=0; T<t; T++)
    	{
    		st = new StringTokenizer(br.readLine());
    		int v = Integer.parseInt(st.nextToken());
    		int e = Integer.parseInt(st.nextToken());
    		
    		list = new List[v+1];
    		visit = new int[v+1];
    		
    		for(int i=0; i<=v; i++)
    		{
    			list[i] = new LinkedList<Integer>();
    		}
    		
    		for(int i=0; i<e; i++)
    		{
    			st = new StringTokenizer(br.readLine());
    			
    			int a = Integer.parseInt(st.nextToken());
    			int b = Integer.parseInt(st.nextToken());
    			
    			list[a].add(b);
    			list[b].add(a);
    			
    		}
    		
    		boolean ans=true;
    		
    		for(int i=1; i<=v; i++)		// 독립된 덩어리가 있을 수 있으므로 방문 하지 않았다면 돌아준다.
    		{
    			if(visit[i] == 0) 
    			{
    				ans = bfs(i);
    				//System.out.println("ans : " + ans);
    				if(!ans) break;
    			}
    		}
    		
    		if(ans)
    		{
    			bw.write("YES\n");
    		}
    		else
    		{
    			bw.write("NO\n");
    		}
    	}
    	
    	
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
    static boolean bfs(int n)
    {
    	Queue<Integer> q = new LinkedList<>();
    	
    	q.add(n);
    	visit[n] = 1;
    	
    	while(!q.isEmpty())
    	{
    		int cur_q = q.poll();
    		
    		for(int i : list[cur_q])
    		{
    			if(visit[i] == 0)
    			{
    				q.add(i);
    				visit[i] = visit[cur_q] == 2 ? 1 : 2;
    			}
    			else if(visit[i] == visit[cur_q])
    			{
    				//System.out.println("ans : "+ i + " " + cur_q + " " + visit[i] + " " + visit[cur_q]);
    				return false;
    			}
    		}
    		
    	}
    	
    	return true;
    	
    }
}
