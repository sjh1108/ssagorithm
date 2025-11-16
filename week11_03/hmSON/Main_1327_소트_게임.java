import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1327_소트_게임 {
	
	/*
	 * BOJ_1327 : 소트 게임 (Gold_4)
	 * 자료구조 및 알고리즘 : BFS(상태 그래프 탐색), 방문 집합(HashSet)
	 *
	 * [문제 요약]
	 * - 길이 N의 수열에서, 연속한 길이 K 구간을 선택해 '뒤집기(reverse)' 연산을 할 수 있다.
	 * - 초기 수열을 오름차순(1..N)으로 정렬하기 위한 최소 연산 횟수를 구하라.
	 * - 정렬이 불가능하면 -1을 출력한다.
	 *
	 * [접근 아이디어]
	 * - 한 번의 연산은 "상태(수열 문자열)"를 "다른 상태"로 변환하는 간선으로 볼 수 있다.
	 * - 모든 가능한 길이 K의 구간을 뒤집어 생성되는 이웃 상태들을 한 레벨씩 확장하는 BFS로
	 *   목표 상태("123...N")에 처음 도달하는 깊이가 최소 연산 횟수와 같다.
	 * - 이미 방문한 상태는 다시 방문하지 않도록 HashSet으로 중복 제거.
	 *
	 * [상태 표현]
	 * - 문자열로 상태를 표현("24153" 등). 비교/저장/해시가 간편하다.
	 *
	 * [시간 복잡도]
	 * - 최악 시 상태 공간은 N! 이지만, K 고정의 변환과 방문 체크로 실제 탐색은 훨씬 줄어든다.
	 * - 각 상태에서 생성되는 이웃 수는 (N-K+1).
	 */
	
	static int n, k;                 // n: 수열 길이, k: 뒤집기 구간 길이
	static char[] arr;               // 입력 수열(문자형으로 보관: '1'..'9' 등)
	static HashSet<String> arrSet = new HashSet<>(); // 방문 상태 집합
	
	// BFS 큐에서 사용할 상태 객체
	static class Arr {
		String str; // 현재 상태(수열) 문자열
		int cnt;    // 이 상태까지 도달하는 데 소요된 연산 횟수(깊이)
		
		public Arr(String str, int cnt) {
			this.str = str;
			this.cnt = cnt;
		}
	}
	
	// 입력 및 초기화
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		arr = new char[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			// 입력이 공백 구분된 숫자이므로 문자열로 받고 첫 글자 사용
			// (문제 입력은 각 원소가 1~N의 정수이며, 자리수 1자리 가정)
			arr[i] = st.nextToken().charAt(0);
		}
	}
	
	/*
	 * bfs()
	 * - 시작 상태에서 목표 상태("123...N")로 가는 최소 뒤집기 횟수를 구한다.
	 * - 불가능하면 -1을 반환.
	 */
	static int bfs() {
		// 목표 상태 생성: "123...N"
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) sb.append(i);
		String target = sb.toString();
		
		// 시작 상태가 이미 정렬되어 있으면 0
		String start = String.valueOf(arr);
		if (start.equals(target)) return 0;
		
		// BFS 준비
		Queue<Arr> q = new ArrayDeque<>();
		q.add(new Arr(start, 0));
		arrSet.add(start); // 시작 상태 방문 표시
		
		while (!q.isEmpty()) {
			Arr a = q.poll();
			
			// 길이 K의 모든 연속 구간을 뒤집어 다음 상태를 생성
			for (int i = 0; i <= n - k; i++) {
				char[] str = a.str.toCharArray();
				
				// [i .. i+k-1] 구간을 제자리에서 뒤집기
				for (int j = 0; j < k / 2; j++) {
					char temp = str[i + j];
					str[i + j] = str[i + (k - j - 1)];
					str[i + (k - j - 1)] = temp;
				}
				
				String next = String.valueOf(str);
				// 이미 본 상태는 스킵
				if (arrSet.contains(next)) continue;
				
				// 목표 상태 도달 시, 현재 깊이+1이 최소 연산 횟수
				if (next.equals(target)) return a.cnt + 1;
				
				// 다음 레벨로 확장
				q.add(new Arr(next, a.cnt + 1));
				arrSet.add(next);
			}
		}
		
		// 모든 상태를 탐색했지만 목표에 도달하지 못하면 불가능
		return -1;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println(bfs());
	}

}
