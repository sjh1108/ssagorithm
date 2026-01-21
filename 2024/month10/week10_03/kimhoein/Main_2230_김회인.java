package study;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


public class Main_2230_김회인 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int array[] = new int[n];
		
		for(int i=0; i<n; i++)
		{
			array[i] = Integer.parseInt(br.readLine());
		}
		
		Arrays.sort(array);
		int start =0;
		int end =1;
		int min = Integer.MAX_VALUE;
		
		while(true)
		{
			int sum = array[end] - array[start];
			if(sum >= m)
			{
				start++;
				min = Math.min(min, sum);
				if(start >= n) break;
			}
			else if(sum < m)
			{
				end++;
				if(end >= n) break;
			}
		}
		
		bw.write(Integer.toString(min));
		
        bw.flush();
		bw.close();
		br.close();
	}
	

}
