package algo2025_10_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_7568_덩치 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N;
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine());
		int[][] people = new int [N][2];
		
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
			people[i][0] = Integer.parseInt(tokens.nextToken()); //몸무게
			people[i][1] = Integer.parseInt(tokens.nextToken()); // 키
		}
		
		for (int i = 0; i < N; i++) {
			int rank = 1;
			for (int j = 0; j < people.length; j++) {
				if(i == j) continue;
				if(people[i][0] < people[j][0] && people[i][1] < people[j][1]) 
					rank++;
			}
			System.out.print(rank + " ");
		}
		
		
	} // main
} //calss
