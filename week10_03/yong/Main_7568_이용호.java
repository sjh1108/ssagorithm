package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main_7568_이용호 {
/*
 * 몸무게 x 키 y (x1 > x2) & (y1 > y2) -> 1의 덩치가 더 큼
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		int[][] dung = new int[N][2];
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int kg = Integer.parseInt(st.nextToken());
			int height = Integer.parseInt(st.nextToken());
			dung[i][0] = kg;
			dung[i][1] = height;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < N; i++) {
			int rank = 1;
			for(int j = 0; j < N; j++) { 
				if(i == j) continue; //자신은 비교대상 제외
				//자신보다 덩치크면 rank down
				if(dung[i][0] < dung[j][0] && dung[i][1] < dung[j][1]) rank++;
			}
			sb.append(rank + " ");
		}
		System.out.println(sb);
	}
	

}
