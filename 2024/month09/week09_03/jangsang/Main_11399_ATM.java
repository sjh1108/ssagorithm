package algo2025_09_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_11399_ATM {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int[] times;
	static int N;
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine());
		times = new int[N];
		
		tokens = new StringTokenizer(input.readLine());
		for (int i = 0; i < N; i++) {
			times[i] = Integer.parseInt(tokens.nextToken());
		}
		
		Arrays.sort(times); // 가장 늦은 시간을 뒤로 보내야함 그래야 시간 누적이 안됨.
		int time = 0;
		int time2 = 0;
		
		for (int i = 0; i < N; i++) {
			time += times[i];
			time2 += time;
		}
		System.out.println(time2);
	}
}
