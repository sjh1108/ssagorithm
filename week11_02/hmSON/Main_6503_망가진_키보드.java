import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main_6503_망가진_키보드 {
	
	/*
	 * BOJ_6503 : (문자열) 최대 N개의 서로 다른 문자로 이루어진 가장 긴 부분 문자열
	 * 자료구조 및 알고리즘 : 슬라이딩 윈도우, 해시맵(빈도 카운팅)
	 *
	 * [문제 요약]
	 * - 정수 N과 문자열 S가 주어진다. (여러 테스트 케이스, N=0이면 종료)
	 * - 서로 다른 문자의 종류가 최대 N개인 부분 문자열 중 가장 긴 길이를 구하라.
	 *
	 * [접근 아이디어]
	 * - 투 포인터(슬라이딩 윈도우)로 구간 [left..right]를 유지하며,
	 *   구간 내 문자 빈도를 해시맵으로 관리한다.
	 * - 새로운 문자를 오른쪽으로 확장할 때(map.size() > N)이면,
	 *   왼쪽을 줄여 문자 종류 수를 N 이하로 맞춘다.
	 * - 매 단계에서 윈도우 길이(right - left + 1)의 최댓값을 갱신한다.
	 *
	 * [시간 복잡도]
	 * - 각 문자는 윈도우에 들어오고 나가는 과정이 최대 한 번씩 발생 → O(|S|)
	 */
	
	// 공용 입출력 버퍼
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	// N: 허용 가능한 서로 다른 문자 수(종류 수), max: 정답(최장 길이), str: 입력 문자열
	static int n, max;
	static String str;
	
	// 현재 윈도우 내 각 문자 빈도
	static HashMap<Character, Integer> map = new HashMap<>();
	
	public static void main(String[] args) throws Exception {
		while(true) {
			n = Integer.parseInt(br.readLine()); // 허용 종류 수
			if(n == 0) break;                    // 0이면 입력 종료
			
			map.clear();
			max = n;                 // 최소 길이는 N (초기 윈도우 길이 가정) — 이후 갱신
			str = br.readLine();     // 대상 문자열
			
			search();                // 슬라이딩 윈도우 수행
			sb.append(max).append('\n');
		}
		
		System.out.println(sb);
	}
	
	/*
	 * search()
	 * - 슬라이딩 윈도우로 "서로 다른 문자가 최대 n개"인 최장 부분 문자열 길이를 계산하여 max를 갱신한다.
	 */
	static void search() {
		int left = 0, right = n; // 초기 윈도우 [0..n-1]로 시작하기 위해 right를 n에서 시작
		
		// 초기 윈도우 [0..n-1] 빈도 세팅
		for(int i=0; i<n && i<str.length(); i++) {
			char c = str.charAt(i);
			map.put(c, map.getOrDefault(c, 0) + 1);
		}
		
		// 문자열을 오른쪽으로 확장
		while(right < str.length()) {
			// 1) 오른쪽 문자 추가
			char c = str.charAt(right);
			map.put(c, map.getOrDefault(c, 0) + 1);
			
			// 2) 문자의 종류 수가 n을 초과하면, 왼쪽을 줄여서 n 이하로 맞춘다
			if(map.size() > n) {
				char target = str.charAt(left++);
				map.put(target, map.get(target) - 1);
				if(map.get(target) == 0) map.remove(target);
			}
			
			// 3) 현재 윈도우 길이로 정답 갱신
			int diff = right - left + 1;
			if(diff > max) max = diff;
			
			// 다음 위치로 이동
			right++;
		}
	}

}
