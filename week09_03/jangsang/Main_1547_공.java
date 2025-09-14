package algo2025_09_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1547_공 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static int M;
	public static void main(String[] args) throws NumberFormatException, IOException {
		M = Integer.parseInt(input.readLine());
		int ball = 1;
		
		for (int i = 0; i < M; i++) {
			StringTokenizer tokens = new StringTokenizer(input.readLine());
			int x = Integer.parseInt(tokens.nextToken());
            int y = Integer.parseInt(tokens.nextToken());
            
            if (ball == x) {
                ball = y;
            } else if (ball == y) {
                ball = x;
            }
		}
		System.out.println(ball);
	}
}
