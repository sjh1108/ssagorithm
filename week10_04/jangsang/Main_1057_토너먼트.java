package algo2025_10_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1057_토너먼트 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N, kim, lim;
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		kim = Integer.parseInt(tokens.nextToken());
		lim = Integer.parseInt(tokens.nextToken());
		
		int round = 0;
		while(kim != lim) {
			kim = (kim + 1) / 2;
			lim = (lim + 1) / 2;
			round++;
		}
		System.out.println(round);
		
	}
}
