package d0909;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1339_단어수학_해답 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int [] arr = new int [26];

		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<line.length(); j++) {
				int c = line.charAt(j);
				arr[c-'A'] += (int)Math.pow(10, line.length()-1-j);
			}
		}
		Arrays.sort(arr); // 오름차순 정렬
		
		int num = 9;
		int idx = 25; // 오름차순이라 가장 높은 값부터 가져오기
		int result = 0;
		while(arr[idx] !=0) {
			result += arr[idx]*num;
			idx--;
			num--;
		}
		System.out.println(result);
	
	}

}
