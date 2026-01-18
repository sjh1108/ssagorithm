package month08.week02.kjs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class week02_boj11720_숫자의합 {

	   public static void main(String[] args) throws IOException {

	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        int N = Integer.parseInt(br.readLine());
	        String numbers = br.readLine();

	        int sum = 0;

	        for (int i = 0; i < N; i++) {
	            sum += numbers.charAt(i) - '0';
	        }

	        System.out.println(sum);
	    }

}
