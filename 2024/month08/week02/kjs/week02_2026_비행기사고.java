package month08.week02.kjs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class week02_2026_비행기사고 {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());

		int[] DP = new int[N];

		for (int i = 0; i < DP.length; i++) {
			DP[i] = Integer.parseInt(st.nextToken());

		}
//		for(int i=0; i<DP.length; i++) {
//			System.out.print(DP[i] + " ");
//		}
		
		int max = DP[0];
		for(int i=0; i<DP.length; i++) {
			if(DP[i]>max) {
				max =DP[i];
			}
		}
		int last = DP[N-1];
		int min=0;
		for(int i=N-1; i>=0; i--) {
			if(last>DP[i]) {
				min = DP[i];
				break;
			}else {
				min=-1;
			}
		}
		int result=0;
		if(min==-1) {
			result = min;
		}else {
			result=max-min;
		}
		System.out.println(result);
	
	}
}
