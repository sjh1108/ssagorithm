import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_15961_회전_초밥 {
	
	/*
	 * BOJ_15961 : 회전 초밥 (Gold_4)
	 * 자료구조 및 알고리즘 : 슬라이딩 윈도우, 빈도 카운팅
	 *
	 * [문제 요약]
	 * - 길이 N의 원형 컨베이어 벨트 위에 초밥 번호가 주어진다(1..D).
	 * - 연속한 K개의 접시를 집어 먹을 수 있고, 쿠폰 C에 해당하는 초밥 1개를 추가로 무료 제공받는다.
	 * - 단, 같은 종류 초밥은 여러 개 먹어도 "종류 수"는 1로 계산된다.
	 * - 연속 구간 K개에서 먹을 수 있는 "서로 다른 초밥 종류 수 + (쿠폰 초밥이 구간에 없으면 +1)"의 최댓값을 구하라.
	 *
	 * [접근 아이디어]
	 * - 슬라이딩 윈도우 길이 K를 원형으로 이동하면서, 구간 내 "각 초밥 종류 등장 횟수"를 관리한다.
	 * - 현재 구간의 서로 다른 종류 수(cnt)를 유지:
	 *   * 어떤 종류의 빈도가 0→1이 되면 cnt++
	 *   * 1→0이 되면 cnt--
	 * - 매 스텝에서 최대값 = max(max, cnt + (쿠폰 종류가 구간에 없다면 1, 있으면 0))
	 * - 원형이므로 오른쪽 인덱스는 (right % n)로 순환 처리.
	 *
	 * [시간 복잡도]
	 * - 각 접시는 윈도우 진입/이탈 시 한 번씩만 처리 → O(N)
	 */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// N: 접시 수, D: 초밥 가짓수, K: 연속해서 먹는 접시 수, C: 쿠폰 초밥 번호
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());      // 벨트 길이(접시 개수)
		int maxIdx = Integer.parseInt(st.nextToken()); // 초밥 가짓수 D
		int k = Integer.parseInt(st.nextToken());      // 윈도우 크기 K
		int coupon = Integer.parseInt(st.nextToken()) - 1; // 쿠폰 초밥 (0-based)
		
		// belt[i] : i번째 위치의 초밥 종류(0-based로 변환)
		int[] belt = new int[n];
		// sushi[t] : 현재 윈도우 내에서 "종류 t" 초밥의 개수(빈도)
		int[] sushi = new int[maxIdx];
		for(int i=0; i<n; i++) {
			belt[i] = Integer.parseInt(br.readLine()) - 1; // 입력을 0-based로 변경
		}
		
		// 초기 윈도우 [0..k-1] 세팅
		int cnt = 0; // 현재 윈도우 내 서로 다른 초밥 종류 수
		int max = 0; // 정답(최댓값)
		for(int i=0; i<k; i++) {
			int id = belt[i];
			if(sushi[id] == 0) cnt++; // 새로운 종류가 처음 등장
			sushi[id]++;
		}
		max = cnt;
		// 쿠폰 초밥이 현재 윈도우에 없다면 +1 시도
		if(sushi[coupon] == 0) max++;
		
		// 슬라이딩 윈도우: 왼쪽 제거, 오른쪽 추가 (원형이므로 % n 사용)
		int left = 0, right = k-1; // 현재 윈도우는 [left..right]
		// 최댓값은 이론상 K(모든 접시가 다른 종류) + 쿠폰 1 → K+1을 넘을 수 없음
		// left < n-1 : 원형으로 한 바퀴를 모두 검사하면 충분 (중복 검사 방지용 조건)
		while(max < k+1 && left < n-1) {
			// 윈도우에서 왼쪽 접시 제거
			int leftIdx = belt[left++];
			sushi[leftIdx]--;
			if(sushi[leftIdx] == 0) cnt--; // 어떤 종류가 완전히 사라지면 서로 다른 종류 수 감소
			
			// 윈도우 오른쪽에 새 접시 추가 (원형)
			int rightIdx = belt[(++right) % n];
			sushi[rightIdx]++;
			if(sushi[rightIdx] == 1) cnt++; // 새로운 종류가 추가되면 증가
			
			// 쿠폰 초밥 포함 여부를 반영해 최댓값 갱신
			// (쿠폰 종류가 현재 윈도우에 없다면 +1)
			max = Math.max(max, cnt + (sushi[coupon] == 0 ? 1 : 0));
		}
		
		// 결과 출력
		System.out.println(max);
	}
	
}
