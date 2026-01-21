package study;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

class Solution_2115_김회인
{   
	static class honey implements Comparable<honey>
	{
		int row;
		int col_start;
		int col_end;
		int cost;
		
		honey(int row, int col_start, int col_end, int cost)
		{
			this.row = row;
			this.col_start = col_start;
			this.col_end = col_end;
			this.cost = cost;
			
		}

		@Override
		public int compareTo(honey o) {
			return this.cost - o.cost;
		}
	}
	static int array[];
	static int N,M,C;
	static List<Integer> list = new LinkedList<>();
	static boolean visit[];
	static int max=0;
	static int map[][];
	static PriorityQueue<honey> honeys;
	
    public static void main(String[] args) throws Exception
    {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st;// = new StringTokenizer(br.readLine());
       
       int t = Integer.parseInt(br.readLine());
       
       for(int T=1; T<=t; T++)
       {
    	   
    	   st = new StringTokenizer(br.readLine());
    	   honeys = new PriorityQueue<>(Collections.reverseOrder());
    	   
    	   N = Integer.parseInt(st.nextToken());
    	   M = Integer.parseInt(st.nextToken());
    	   C = Integer.parseInt(st.nextToken());
    	   
    	   map = new int[N][N];
    	   
    	   for(int i=0; i<N; i++)		// 입력
    	   {
    		   st = new StringTokenizer(br.readLine());
    		   for(int j=0; j<N; j++)
    		   {
    			   map[i][j] = Integer.parseInt(st.nextToken());
    		   }
    	   }
    	   
    	   
    	   for(int i=0; i<N; i++)
    	   {
    		   list = new LinkedList<>();
    		   
    		   for(int j=0; j<N; j++)
    		   {
    			   if(list.size() == M)
    			   {
    				   list.remove(0);
    			   }
    			   
    			   int num = map[i][j];
    			   
    			   list.add(num);
    			   
    			   if(list.size() <M) continue;
    			   
    			   array = new int[N];
    			   visit = new boolean[list.size()];	// 후에 list size에 맞게 다시 재 생성
    			   max =0;
    			   
    			   depth(0,C,array);
    			   
    			   honey h = new honey(i, j-M+1, j, max);
    			   honeys.add(h);
    		   }
    	   }
    	   
    	   //System.out.println("honeys : " + honeys.size());
    	   
    	   honey h1 = honeys.poll();
    	   honey h2 = null;
    	   //System.out.println("h1.row : " + h1.row);
    	   
    	   while(!honeys.isEmpty())
    	   {
    		   honey h_temp = honeys.poll();
    		   //System.out.println("h_temp.row : " + h_temp.row);
    		   
    		   if(h1.row == h_temp.row) 
    		   {
    			   if((h1.col_start<= h_temp.col_start && h_temp.col_start<= h1.col_end)||(h1.col_start<= h_temp.col_end && h_temp.col_end<= h1.col_end))
    			   {
    				   // 만약 범위가 겹친다면 패스
    				   // System.out.println("범위가 같다" + (h1.row == h_temp.row));
    				   continue;
    			   }
    		   }
    		   // 범위가 겹치지 않는다면 값 넣어주고 와일문 탈출
    		   //System.out.println("값 넣어줌");
    		   h2 = h_temp;
    		   break;
    	   }
    	   
    	   
    	   int result = h1.cost + h2.cost;
    	   
    	   // System.out.println("h1.cost : " + h1.cost);
    	   //System.out.println("h2.cost : " + h2.cost);
    	   
    	   bw.write("#" +Integer.toString(T) + " " + Integer.toString(result) + "\n");
       }
       
       bw.flush();
       bw.close();
       br.close();
       
    }
    
    static void depth(int d, int c,int array[])		// C는 향후에 가지 치기용
    {
    	if(d == list.size())		// 그 조합에서 max 값 찾기
    	{
    		int temp =0;
    		for(int i : array)
    		{
    			if(c - i < 0) break;
    			c-=i;
    			temp += i*i;
    				
    		}
    		if(max < temp ) max = temp;
    		
    		return;
    	}
    	for(int i=0; i<list.size(); i++)		// 
    	{
    		if(visit[i]) continue;
    		visit[i] = true;
    		array[d] = list.get(i);
    		depth(d+1,c,array);
    		visit[i] = false;
    		
    	}
    }
    
    
    
}