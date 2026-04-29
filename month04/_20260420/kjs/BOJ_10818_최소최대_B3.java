package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_10818_최소최대_B3 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] num = new int[N];
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<num.length; i++) {
			num[i] = Integer.parseInt(st.nextToken());
			if(max<=num[i]) { max = num[i];}
			if(min>num[i]) { min = num[i];}
		}
		System.out.println(min + " " + max);

	}

}
