package d0909;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_1132_합 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[26];
		
		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<line.length(); j++) {
				char C = line.charAt(j);
				arr[C-'A'] = (int)Math.pow(10, line.length()-1-j);
			}
		}
		Arrays.sort(arr);
		
		int num = 9;
		int idx = 25;
		int result = 0;
		for(int i=0; i<idx; i++) {

		}
		
		
		
	}

}
