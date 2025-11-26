package samsung01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1546_평균 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		double max = Integer.MIN_VALUE;
		for(int i=0; i<arr.length; i++) {
			if(arr[i]>max) {
				max = arr[i];
			}
		}
		double mean = 0;
		for(int i=0; i<arr.length; i++) {
			mean += arr[i]/max*100;
//			System.out.println(mean);
		}
		mean = mean/arr.length;
		System.out.println(mean);
		
		

	}

}
