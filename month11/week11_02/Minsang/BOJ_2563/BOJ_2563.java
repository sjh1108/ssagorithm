package week11_02.Minsang.BOJ_2563;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2563{
	static int N;
	static boolean[][] paper;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		paper = new boolean[100][100];
		
		StringTokenizer st;
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			// 중복된 부분도 true로 바꾸기
			for(int j = x; j < x+10; j++) {
				for(int k = y; k <y+10; k++) {
					paper[j][k] = true;
				}
			}
		}
		
		// 넓이 구하기
		int area = 0;
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				if(paper[i][j]) {
				area++;
				}
			}
		}
		System.out.println(area);

	}

}

