package ssagorithm.week03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj2669_직사각형합집합면적 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 입력 1~100 최대 배열 만들기
		int[][] map = new int[100][100];
		// 넓이 합산용 변수
		int sum = 0;
		
		// 사각형 좌표 4개 받기 (x1,y1) , (x2,y2)
		for (int i=0; i<4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			// 한개 좌표 받았을 때 배열 채우는 부분
			for(int r=0; r<x2-x1; r++) {
				// r * c  값 1로 채우기
				for(int c=0; c<y2-y1; c++) {
					if(map[x1+r][y1+c]==0) {
						map[x1+r][y1+c] = 1;
						sum++;
					}
				}
			}
		}
		System.out.println(sum);
		
	}
	

}
