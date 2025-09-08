package month08.week02.kimhoein;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.ArrayList;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */
class Solution_3421_김회인
{
	static int count=0;
	static int N;
	static ArrayList<Integer> al = new ArrayList();
	
	public static void main(String[] args) throws Exception
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		for(int t = 1; t<=T;t++)
		{
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			int array[][] = new int[N+1][N+1];
			
			for(int i=0; i<M;i++)
			{
				st = new StringTokenizer(br.readLine());
				int x=Integer.parseInt(st.nextToken());
				int y=Integer.parseInt(st.nextToken());
				
				array[x][y] = array[y][x] = 1;
			}
			
			dp(array,1);
			//bw.write(Integer.toString(count+1)+"\n");
			bw.write("#" + Integer.toString(t)+" "+Integer.toString(count+1)+"\n");
			count=0;
		}
		bw.flush();
		bw.close();
	}
	
	static void dp(int array[][],int n)
	{
		//System.out.println("1111 : n : " + n);
		
		for(int i=n; i<=N;i++)
		{
			//System.out.println("in : n : " + n + "i : " + i);
			if(arr(array,i))
			{
				al.add(i);
				count++;
				dp(array,i+1);	
				al.remove(al.size() - 1);
				//System.out.println("out -> n : " + n + "i : " + i);
			}

		}
	}
	
	static boolean arr(int[][] array,int i)
	{
		for(int a : al)
		{
			if(array[i][a] == 1) 
			{
				//System.out.println("a : " + a + "i : " + i);
				return false;
			}
		}
		
		return true;
		
	}
}