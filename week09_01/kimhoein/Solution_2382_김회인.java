package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.AbstractMap.SimpleEntry;

import org.xml.sax.InputSource;

public class Solution_2382_김회인 {
	
	static class Node
	{
		int dir=0;
		int count=0;
		int t=0;			// 몇턴인지
		int check_num=0;
		Node(int count,int dir, int t)
		{
			this.dir=dir;
			this.count = count;
			this.t = t;
		}
		
	}
	
	static int delta[][] = {{-1,0},{1,0},{0,-1},{0,1}};
	static int N;
	static Node[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		int t= Integer.parseInt(br.readLine());
		
		for(int T=1 ; T<=t; T++)
		{
			
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			
			map = new Node[N][N];
			
			int max_count=0;
			for(int i=0; i<K; i++)
			{
				st = new StringTokenizer(br.readLine());
				int h = Integer.parseInt(st.nextToken());
				int w = Integer.parseInt(st.nextToken());
				int cell = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken())-1;
				
				map[h][w] = new Node(cell,dir,0);
			}
			
			/*
		for(int i=0; i<N; i++)
		{
			
			for(int j=0; j<N; j++)
			{
				if(map[i][j] == null)
				{
					System.out.print("0 ");					
				}
				else
				{
					System.out.print(map[i][j].count + " ");
				}
			}
			System.out.println("");
		}
		System.out.println("");
			 */
			
			for(int I=0; I<M; I++)
			{
				Node[][] temp = new Node[N][N];
				
				move(temp);
				/*
				for(int i=0; i<N; i++)
				{
					for(int j=0; j<N; j++)
					{
						if(map[i][j] == null)
						{
							System.out.print("0 ");					
						}
						else
						{
							System.out.print(map[i][j].count + " ");
						}
					}
					System.out.println("");
				}
				System.out.println("");
				 */
			}
			
			for(int i=0; i<N; i++)
			{
				for(int j=0; j<N; j++)
				{
					if(map[i][j] != null)
					{
						max_count+= map[i][j].count;			
					}
				}
			}
			
			bw.write("#"+Integer.toString(T) + " " +Integer.toString(max_count)+"\n");

		}
		bw.flush();
		bw.close();
		br.close();
		
	}
	
	static void move(Node[][] temp)
	{
		
		for(int i=0; i<N; i++)
		{
			for(int j=0; j<N; j++)
			{
				if(map[i][j] == null)continue;
				//System.out.println("map[i][j].dir : " + map[i][j].dir);
				int dx = i + delta[map[i][j].dir][0];
				int dy = j + delta[map[i][j].dir][1];
				
				if(dx==N-1 || dy== N-1 || dx == 0 || dy == 0)		// 맵 끝일 때?
				{
					//System.out.println("전 map[i][j].dir : " + map[i][j].dir);
					//System.out.println("dx : " + dx + " dy : " + dy + "count" + map[i][j].count);
					if(map[i][j].dir%2 ==0)  map[i][j].dir++;	// 방향 전환
					else  map[i][j].dir--;
					//System.out.println("후 map[i][j].dir : " + map[i][j].dir +"\n");
					map[i][j].count/=2;
				}
				
				if(temp[dx][dy] != null && temp[dx][dy].t == map[i][j].t+1)	// 같은 위치 탐지
				{
					temp[dx][dy].count += map[i][j].count;
					if(temp[dx][dy].check_num < map[i][j].count)
					{
						temp[dx][dy].dir = map[i][j].dir;
						temp[dx][dy].check_num = map[i][j].count;
					}
					
				}
				else
				{
					temp[dx][dy] = map[i][j];
					temp[dx][dy].t++;
					temp[dx][dy].check_num = map[i][j].count;
				}
				map[i][j] = null; // 이전 위치 없에 주기
			}
		}
		
		for(int i=0; i<N; i++)
		{
			for(int j=0; j<N; j++)
			{
				if(temp[i][j]!=null) temp[i][j].check_num = 0;
			}
		}
		map = temp;
		
	}

}
