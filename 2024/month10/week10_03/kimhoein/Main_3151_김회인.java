package study;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


public class Main_3151_김회인 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		
		int arr[] = new int[n];
		
		for(int i=0; i<n; i++)
		{
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr);
		long count=0;
		
		for(int i=0; i<n; i++)
		{
			//System.out.print(arr[i] + " ");			
		}
		
		//System.out.println();
		for(int i=0; i<n; i++)
		{
			int start = i+1;
			int end = n-1;
			//if(start == i) start++;
			
			while(start < end)
			{
				int sum = arr[i] + arr[start] + arr[end];
				
				if(sum == 0)
				{
					if(arr[start] == arr[end])
					{
						int len = end - start +1;
						count += len *(len -1)/2;
						break;
					}
					
					int startVal = arr[start];
					int endVal = arr[end];
					int startCount = 0, endCount=0;
					
					while(start < n && arr[start] == startVal) {
						start++;
						startCount++;
					}
					
					while(end >= 0 && arr[end] == endVal) {
						end--;
						endCount++;
					}
					
					count += startCount * endCount;
				}
				else if(sum <0)
				{
					start++;
				}
				else if(sum > 0)
				{
					end--;
				}
				
			}
		}
		 
		bw.write(Long.toString(count));
		
        bw.flush();
		bw.close();
		br.close();
	}
	

}
