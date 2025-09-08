package week09_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_11650 
{
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		
		int N = Integer.parseInt(br.readLine());
		
		int[][] arr = new int[N][2];
		
		for (int i = 0; i < N; i++) 
		{
			st = new StringTokenizer(br.readLine());
			// 오답: int arr[i][0]
			// 바로의문: 여기에다가 i말고, j도 해줘야 하나? 답: 놉!
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		// 위에까지 자료 다 넣었으니까 
		// 람다식
		Arrays.sort(arr, (e1, e2) ->
				{
					if(e1[0] == e2[0])
					{
						return e1[1] - e2[1];
					}
					
					else
					{
						return e1[0] - e2[0];
					}
				});
		
		
		for (int i = 0; i < N; i++) 
		{
			sb.append(arr[i][0] + " " + arr[i][1] + "\n");
		}
		System.out.print(sb);
	}
}


/*
* 1. st = new StringTokenizer(br.readLine());
*			// 오답: int arr[i][0]
*			// 바로의문: 여기에다가 i말고, j도 해줘야 하나? 답: 놉!
*			arr[i][0] = Integer.parseInt(st.nextToken());
*			arr[i][1] = Integer.parseInt(st.nextToken());
*
*2. Arrays.sort(arr, (e1, e2) ->
*				{
*					if(e1[0] == e2[0])
*					{
*						return e1[1] - e2[1];
*					}
*					else
*					{
*						return e1[0] - e2[0];
*				});
*
*
* 3. return 적자.. 제발..
*
*/



