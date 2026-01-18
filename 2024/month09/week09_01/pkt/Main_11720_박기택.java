package exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_11720_박기택 {
		public static void main(String[] args) throws NumberFormatException, IOException {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int N = Integer.parseInt(br.readLine());
			String num = br.readLine();
			
			int[] arr = new int[N];
			
			for (int i = 0; i < N; i++) {
				arr[i] = num.charAt(i) - '0';
			}
			//toCharArray와 비교해서 보기. 
			
			
			int sum = 0;
			for (int i = 0; i < N; i++) {
				sum += arr[i];
			}
			  
			
			System.out.println(sum);
			
		}

}
