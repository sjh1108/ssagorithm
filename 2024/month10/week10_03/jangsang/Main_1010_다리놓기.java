package algo2025_10_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1010_다리놓기 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N,M,tc;
	public static void main(String[] args) throws NumberFormatException, IOException {
		tc = Integer.parseInt(input.readLine());
		
		for (int i = 0; i < tc; i++) {
			tokens = new StringTokenizer(input.readLine());
			N = Integer.parseInt(tokens.nextToken());
			M = Integer.parseInt(tokens.nextToken());
			System.out.println(cb(M, N));
			
		}
		
	}
	static int cb(int n, int r) {
		
        if (r > n - r) {
            r = n - r;
        }
        long result = 1;
      
        for (int i = 1; i <= r; i++) {
            result = result * (n - i + 1) / i;
        }

        return (int) result;
    }

}
