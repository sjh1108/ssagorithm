package d0911;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class BOJ_18111_마인크래프트 {

	static int n, m, b, max, min, temp;
	static int[][] map;
	static Deque<int[]> dq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());

		map = new int[n][m];
		max = Integer.MIN_VALUE;
		min = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				min = Math.min(min, map[i][j]);
				max = Math.max(max, map[i][j]);
			}
		}
		System.out.println(min);
		System.out.println(max);
//		dq = new ArrayDeque<>();
		
		for (int low = min; low <= max; low++) {
			// i 는 최저 골
			// 채워야 하는 전체 값 계산 필요 fill
			// 깎아야 하는 값 crash
			// 인벤토리 값 b
			// fill > crash + b 이면 넘기기
			int fill = 0;
			int crash = 0;
			
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (map[i][j] > low) {
						crash += (map[i][j] - low);
					} else if (map[i][j] < low) {
						fill += (low - map[i][j]);
					}
				}
			}
			
			System.out.println("fill :"+fill);
			System.out.println("Crash+B : "+ crash+b);
			if (fill > crash + b) continue;
			
			
			System.out.println("# " + low + "의 crash : " + crash + " fill : " + fill + " low :" + low);
//			int time = 0;
//			
//			for (int i = 0; i < n; i++) {
//				for (int j = 0; j < m; j++) {
//					if (map[i][j] > low) {
//						while (map[i][j] > low) {
//							map[i][j]--;
//							crash--;
//							time += 2;
//						}
//					} else if (map[i][j] < low) {
//						while (map[i][j] < low) {
//							map[i][j]++;
//							fill--;
//							time += 1;
//						}
//					}
//				}
//			}
//			
//			System.out.println("low가 " + low + " 일 때 time " +time);
//			 low for문 끝
			
//2 2 1
//1 2
//3 4
		}

	}

}
