package week; // 실버 4

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 이미지 크기 RxC (3 ≤ R ≤ 40, 3 ≤ C ≤ 40)
 * - 각 픽셀은 어두운 정도 V를 나타냄 (0 ≤ V ≤ 255)
 * 
 * - 숫자 9개가 오름차순이나 내림차순으로 정렬되어 있을 때, 중앙값은 다섯 번째 숫자
 * 
 * - 중앙 필터는 이미지에 있는 노이즈를 제거하는 필터
 * - 필터의 크기는 3x3
 * - 이미지의 중앙값을 찾으면서 잡음을 제거
 * 
 * - 그럼 결국 R과 C가 2씩 줄어들게되네
 * 
 * 출력 
 * - 그렇게 필터링된 이미지 J를 구하고
 * - 값이 T보다 크거나 같은 픽셀의 수를 구하라
 * 
 * 입력
 * - R C
 * - RxC 배열
 * - T
 *
 * 전략
 * - 4중 for문밖에 안 떠오름
 * - 일단 깡으로 가보자 
 * 
 * 몰랐던 것
 * - ArrayList 정렬법에 대해 잊어버렸다
 * - Collections.sort(list);
 */
public class Main_1895_필터 {

	static int R, C; // 이미지 크기 RxC
	static Integer[][] map; // 이미지맵
	static ArrayList<Integer> J; // 필터링된 맵
	static int T; // 결과 필터 값
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		map = new Integer[R][C];
		J = new ArrayList<>((R-2)*(C-2));
		
		for(int r = 0; r < R; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < C; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		T = Integer.parseInt(br.readLine());// 진자 입력끝
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for(int r = 1; r < R-1; r++) {
			for(int c = 1; c < C-1; c++) {
				list.clear();
				for(int i = -1; i < 2; i++) {
					for(int j = -1; j <2; j++) {
						list.add(map[r+i][c+j]);						
					}
				}// 필터 하나
				Collections.sort(list); // 정렬
				int middle = list.get(4); // 중앙값 추출 (주의: 다섯번재 숫자 인덱스는 4)
				J.add(middle);
			}
		}
		
		
		int cnt = 0;
		for(int i = 0; i < J.size(); i++) {
//			System.out.println(J.get(i));
			if(J.get(i) >= T) {
				cnt++;
			}
		}
		
		System.out.println(cnt);
	}
}







