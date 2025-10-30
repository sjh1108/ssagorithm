package algostudy.baek.week10_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_2304_이용호 {
/*
 * 백준 2304: 창고 다각형
 * N 개의 기둥 (폭 1M) 활용해서 창고 제작 하려함
 * 창고의 지붕 조건
 * 수평 부분과 수직부분으로 구성, 모두 연결
 * 수평,수직 부분은 반드시 기둥의 윗면과 닿아야 함
 * 지붕의 가장자리는 땅에 닿아야함
 * 비가 올때 물이 고이지 않도록 오목 금지
 * 
 * 높이 기준 내림차순 으로 정렬
 * 가장 높은 기둥부터 처리(지붕 덮기)
 * 현재 기둥 기준 가장 가까운 이미 덮인 지붕 찾고 그 사이 덮기
 * 덮은 구간 면적 계산
 */
	static boolean[] isRoof;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		isRoof = new boolean[1001]; // 1 based
		int sum = 0;
		
		ArrayList<int[]> s = new ArrayList<>();

		// 기둥 정보 입력
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int L = Integer.parseInt(st.nextToken());
			int H = Integer.parseInt(st.nextToken());
			s.add(new int[] {L,H});
		}
		
		s.sort((a,b) -> b[1] - a[1]);
		
		// 지붕 덮기
		for(int[] roofCan : s) {
			int nowL = roofCan[0]; // 현재 기둥 위치x
			int nowH = roofCan[1]; // 현재 기둥 높이y
			
			// 이미 지붕 덮여있으면 패스
			if(isRoof[nowL]) continue;

			// 연결할수 있는 지붕 탐색(가장 가까운 덮인 지붕 탐색)
			int connectN = searchRoof(nowL);
			
			// 첫 기둥은 자기 자신밖에 없음 -> 단일 기둥 면적 추가
			if (connectN == nowL) {
				isRoof[nowL] = true;
				sum += nowH;
			}
			
			else {
				
				int left; int right;
				// 연결 가능 지붕이 존재하면 두 기둥 사이 덮기
				if(nowL < connectN) {
					left = nowL;
					right = connectN;
				}
				else {
					left = connectN;
					right = nowL;
				}
				for(int i = left; i <= right; i++) {
					isRoof[i] = true;
				}
				
				// 면적 계산
				sum += (right - left) * nowH;
			}
		}
		System.out.println(sum);

	}
	
	// 현재 기둥에서 가장 가까운 지붕 기둥 찾기
	private static int searchRoof(int nowL) {
		int gap = 1001;
		int result = nowL;
		for(int i = 1 ; i <= 1000; i++) {
			if(!isRoof[i]) continue;
			// 현재 기둥과 가장 가까운 지붕갱신
			if(Math.abs(nowL - i) < gap) {
				gap = Math.abs(nowL - i);
				result = i;
			}
		}

		return result;
	}

}

