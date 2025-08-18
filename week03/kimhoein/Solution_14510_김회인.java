
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution_14510_김회인 {

	
	public static void main(String[] args) throws IOException {
		BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(br.readLine());
		
		for(int t =1 ; t<=T; t++)
		{
			
			int N = Integer.parseInt(br.readLine());
			int array[] = new int[N];

			st = new StringTokenizer(br.readLine());
			
			for(int i=0; i<N; i++)
			{
				array[i] = Integer.parseInt(st.nextToken());
			}
			
			Arrays.sort(array);
			int target = array[N-1];
			int oneGrowth =0;
			int twoGrowth =0;
			//System.out.println("target" + target);
			for(int i=0; i<N; i++)				// 2일로 해결 가능한것 1일로 해결 가능한것 나누고
			{
				int diff =target - array[i];
				twoGrowth += diff/2;
				oneGrowth += diff%2;
			}
			int days = 0;
			
			
			if(twoGrowth > oneGrowth)	// 2일로 해결가능한게 더 많다면 2->1일로 나눠줌
			{
				int diff = (twoGrowth - oneGrowth)*2;
				days = oneGrowth *2 + (diff/3) * 2;
				
				if(diff%3 == 1) {		// 홀수 1일만큼 더 필요하다면 +1
					days+=1;
				}
				else if(diff%3==2)
				{
					days+=2;
				}
			}
			else if(twoGrowth < oneGrowth)  // 1일 만 남는다면 따로 계산
			{
				days += twoGrowth *2;
				
				int diff = oneGrowth - twoGrowth;

				days += diff * 2 -1;

			}
			else							// 1일 == 2일 상황
			{
				days += twoGrowth *2;
			}
			
			
			bw.write("#" + Integer.toString(t) + " "+Integer.toString(days) + "\n");
		}
		bw.flush();
		bw.close();
	}
	

	
	


}

