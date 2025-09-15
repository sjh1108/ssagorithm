package ssagorithm.bk.s;

import java.io.*;
import java.util.*;

/**
 * 문제 이해
 * - 주어진 2차원 배열의 모든 수를 같게 만들어야함
 * - 한칸을 -1 하는데에 2초의 시간
 * - 한칸을 +1 하는데에 1초의 시간이 걸림
 * 
 * 입력
 * - N : 행
 * - M : 열
 * - B : 블럭사용
 * - map
 * 
 * 출력
 * - 최소시간
 * - 같다면 높이가 높은 것
 * 
 * 전략
 * - 최적의 높이를 미리 찾아낸다는 방식은 그리디여서 실패
 * - 가능한 높이를 모두 탐색하는 방식으로 변경 
 * 
 * 후기
 * - 시도 1 : 그리디 -> 당연히 최적해가 안 나옴
 * - 시도 2 : 모든 (r, c)를 돌며 원하는 높이에 도달할 수 있는지를 검사 후
 * 			각 높이 별 최소 시간 도출 -> 최소 시간을 구해봄
 * 			-> 결과적으로 실패. 블럭이 회수 될 수 있으므로 특정 좌표에서
 * 				블럭이 부족해도 그게 불가능하다고 리턴해서는 안 됐음
 * - 시도 3 : 먼저 높은 것들을 다 깎고 남은 블럭으로 채워볼까 생각했는데
 * 				시간상 너무 손해인 것 같아서 서치함.
 * 			->  그리디 방법에서 사용했던 count[] 배열을 통해 블럭수를 저장해놓으면
 * 				더 간단해질 것 같음. 
 */
public class Main_18111_마인크래프트 {

	static int N, M, B, map[][];
	static int min, max; 
	static int count[]; // 블럭 수 카운트 
	static int time, height;   
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine()," ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		min = Integer.MAX_VALUE;
		count = new int[257]; // 숫자랑 초기화 위치 헷갈리니까 모든 인덱스 만들게 
		max = 0;
		time = Integer.MAX_VALUE;
		// 초기화
		
		
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine()," ");
			for(int c = 0; c < M; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				max = Math.max(max, map[r][c]);
				min = Math.min(min, map[r][c]);
				count[map[r][c]]++;
			}
		}// 입력끝
		
		//가장 낮은 블럭부터 높은 블럭까지 모든 경우의 수의 최단시간을 찾기 
		for(int l = min; l <= max; l++) {
			int result = getTime(l);
			if(result <= time) {
				time = result;
				height = l;
			}		
		}
		
		
		sb.append(time).append(" ").append(height); 
		System.out.println(sb.toString());
	}

	private static int getTime(int l) {
		int time = 0;
	 	int block = B; // 오답노트 : 전역 B를 사용하가 문제생김. 전역변수 사용에 주의 
		for(int b = max; b >= min; b--) { // max부터 시작하니까 블럭이 있을 수도 있는 상황 방지  
			int cnt = count[b];
			// 원하는 높이보다 블럭이 더 높다면  -> 깎아야지 
			if(b > l) {
				time += (b - l) * cnt * 2;
				block += (b - l) * cnt;
			} else if(b < l) {
				time += (l - b) * cnt;
				block -= (l - b) * cnt;
			} // 뭔가.. 코드중복이 거슬리기는 하는데 벌써 제출이 12회가 넘어감 일단 넘기자 
		}
		
		if(block < 0) { // 블럭이 부족했다면 
			return Integer.MAX_VALUE; //불가능 리턴 
		}
		return time;
	}

}
