package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10250 {
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int H = Integer.parseInt(st.nextToken()); // 층
			int W = Integer.parseInt(st.nextToken()); // 호실 좌부터 1호실 우로갈 떄 +1
			int N = Integer.parseInt(st.nextToken()); // 몇 번째 손님
			
			// 전략: 손님의 순서에 따라 알맞은 방 소개해주면 됨. 
			int Y = N % H; // 층
			int X = (N / H) + 1; //호실
			// 꼭대기층 처리
			if(Y == 0) {
				Y = H;
				X--;
			}
			
			String sX = null; // if문 안 지역변수 회피.
			if (X < 10) {
				sX = "0" + String.valueOf(X);
			} else {
				sX = String.valueOf(X);
			}
			
			String sY = String.valueOf(Y);
			
			sb.append(sY+sX).append("\n");
		}
		System.out.println(sb);
	}
}

// 꼭대기 층 엣지케이스 처리... <- 틀린거니까, 엣지 위주로 다시 확인