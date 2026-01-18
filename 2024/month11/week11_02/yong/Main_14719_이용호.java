package algostudy.baek.week11_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14719_이용호 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int H = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());
		
		int[] block = new int[W];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < W; i++) {
			block[i] = Integer.parseInt(st.nextToken());
		}
		int totalWater = 0;
		for(int i = 1; i < W - 1; i++) {
			int left = 0;
			int right = 0;
			
			// 현재 칸 보다 높은 칸 있어야 빗물이 모임 -> 현재 칸 기준 높은 칸 찾기
			for(int j = 0; j < i; j++) {
				left = Math.max(block[j], left);
			}
			
			for(int j = i + 1; j < W; j++) {
				right = Math.max(block[j], right);
			}
			// 현재 칸이 더 낮은지 확인
			if(block[i] < left && block[i] < right) {
				// 더 낮은 칸 기준으로 물이 채워지므로
				totalWater += Math.min(left, right) - block[i];
			}
		}
		System.out.println(totalWater);
		
	}

}
