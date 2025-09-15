package algo2025_09_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main__11047_동전0 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N, K;
	static int[] coins;
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		
		coins = new int[N];
		for (int i = 0; i < N; i++) {
			coins[i] = Integer.parseInt(input.readLine());
		}
		
		int count = 0;
		for (int i = N - 1; i >= 0; i--) { // 큰 화폐단위부터 사용 혹시나 정렬 안되어있으면 arrays.sort 정렬하시
		    if (coins[i] <= K) {
		        count += K / coins[i];
		        K = K % coins[i];
		    }
		}
		
		System.out.println(count);

	}
}
