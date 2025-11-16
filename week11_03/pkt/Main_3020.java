package week_11_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_3020 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());
		
		int[] down = new int[H+2];
		int[] up = new int[H+2]; // 누적합 시 DP에서 범위 초과 방지위해 H+2
		
		
		// 장애물 입력받기. 
		for (int i = 1; i <= N/2; i++) {
			int a = Integer.parseInt(br.readLine()); // 짝 홀 받으니 이런식으로 입력받으면 굳이 분기처리로 짝수인덱스에 입력 할 필요없음. 
			int b = H - Integer.parseInt(br.readLine()) + 1; // 종유석의 바닥 위치
			down[a]++;
			up[b]++;
		}
		
		
		for (int i = 1; i <= H; i++) {
			down[i] += down[i-1];
		}
		
		for (int i = H; i >= 1; i--) {
			up[i] += up[i+1];
		}
		
		int min = N;
		int cnt = 0;
		for (int i = 1; i <= H; i++) {
			int dif = (down[H] - down[i-1]) + (up[1] - up[i+1]);
			
			if(dif < min) {
				min = dif;
				cnt = 1;
			} else if(dif == min) cnt++;
		}
		
		System.out.println(min +" " + cnt);
	
	}
}

// 2차원 배열에다가 인덱스 홀수 짝수일때 분기하고,
// 카운트해서 같은거 cnt에 저장하고, 
// 시간초과 생각하면 단순구현 말고, DP로 해야 하지 않을가? -> max값 DP
// N*N하는 순간 시간 초과 고로 단순 구현 노노.ㅜ 