package bk.silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * N <= 50
 * x,y <= 200
 *
 */
public class Main_7568_덩치 {

	static int N;
	static int[][] list;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		list = new int[N][2]; // 몸무게, 키
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			list[i][0] = Integer.parseInt(st.nextToken());
			list[i][1] = Integer.parseInt(st.nextToken());
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < N; i ++) {
			int count = 0;
			for(int j = 0; j < N; j++) {
				if(list[i][0] < list[j][0] && list[i][1] < list[j][1]) {
					count++;
				}
			}
			sb.append(count+1).append(" ");
		}
		sb.append("\n");
		
		System.out.print(sb.toString());
	}
}
