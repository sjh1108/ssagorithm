import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_2696_중앙값_구하기 {
	
	/*
	 * BOJ_2696 : 중앙값 구하기 (Gold_2)
	 * 자료구조 및 알고리즘 : 우선순위 큐(Priority Queue), 이중 힙(Two Heaps)
	 *
	 * [문제 요약]
	 * - 수열을 읽어들이며 홀수 번째 수를 읽을 때마다 지금까지 입력받은 수들의 중앙값을 출력하라.
	 * - 한 줄에 10개씩 입력되고, 출력 또한 한 줄에 10개씩 해야 한다.
	 *
	 * [핵심 아이디어]
	 * - 매번 정렬(Sorting)하면 시간 복잡도가 너무 커지므로 두 개의 힙을 사용한다.
	 * 1. 중앙값(mid)을 기준으로 작은 값들은 Max-Heap(prev)에, 큰 값들은 Min-Heap(next)에 저장한다.
	 * 2. 새로운 값이 들어오면 mid와 비교하여 적절한 힙에 넣는다.
	 * 3. 홀수 번째 입력마다 두 힙의 균형(개수)을 맞춘다.
	 * - 어느 한쪽 힙의 크기가 더 크다면, 현재 mid를 작은 쪽으로 넘기고
	 * - 큰 쪽 힙의 root(peek)를 새로운 mid로 가져온다.
	 * - 이 방식을 통해 항상 중앙값을 O(log N)에 갱신 및 유지할 수 있다.
	 *
	 * [구현 메모]
	 * - 입력 데이터가 한 줄에 10개씩 들어오므로, StringTokenizer 갱신 로직에 주의해야 한다.
	 * - 출력 또한 10개 단위로 줄바꿈을 해줘야 한다.
	 *
	 * [시간 복잡도]
	 * - 전체 수 N개에 대해 힙 삽입/삭제 연산 수행 -> O(N log N)
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int n;
	// 중앙값보다 큰 수들을 관리 (최솟값이 root여야 중앙값과 가까움 -> 오름차순)
	static PriorityQueue<Integer> next = new PriorityQueue<>();
	// 중앙값보다 작은 수들을 관리 (최댓값이 root여야 중앙값과 가까움 -> 내림차순)
	static PriorityQueue<Integer> prev = new PriorityQueue<>(Collections.reverseOrder());
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		
		while(tc--> 0) {
			n = Integer.parseInt(br.readLine());
			
			// 출력할 중앙값의 총 개수: (N+1)/2
			sb.append((n+1)/2 + "\n");
			
			st = new StringTokenizer(br.readLine());
			int mid = Integer.parseInt(st.nextToken()); // 첫 번째 수는 무조건 중앙값
			sb.append(mid + " ");
			
			int cnt = 1; // 입력받은 숫자의 개수 카운트
			boolean oddCheck = true; // 홀수 번째 입력인지 확인하는 플래그 (첫 입력 처리했으므로 true 시작)
			
			// 입력 데이터 처리 (10개 단위로 줄이 나뉘어 들어옴)
			// 총 N개의 데이터를 처리하기 위해 필요한 줄 수만큼 반복
			for(int d=0; d<=n/10; d++) {
				while(st.hasMoreTokens()) {
					int x = Integer.parseInt(st.nextToken());
					
					// 1. 적절한 힙에 삽입
					if(x >= mid) next.add(x);
					else prev.add(x);
					
					cnt++;
					oddCheck = !oddCheck; // 홀/짝 턴 변경
					
					// 짝수 번째 입력이면 힙에 넣기만 하고 중앙값 재조정/출력은 생략
					if(!oddCheck) continue;
					
					// 2. 중앙값(mid) 재조정 (Balancing)
					// 두 힙의 사이즈가 다르면 균형이 깨진 것이므로 mid를 교체
					if(prev.size() > next.size()) {
						next.add(mid);      // 현재 mid를 큰 쪽으로 보냄
						mid = prev.poll();  // 작은 쪽의 최대값을 새 mid로
					} else if(next.size() > prev.size()){
						prev.add(mid);      // 현재 mid를 작은 쪽으로 보냄
						mid = next.poll();  // 큰 쪽의 최소값을 새 mid로
					}
					
					sb.append(mid + " ");
				}
				
				// 한 줄(10개)을 다 읽었거나, 입력이 남았는데 토큰이 떨어졌으면 다음 줄 읽기
				if(cnt%10 == 0 && cnt < n) {
					st = new StringTokenizer(br.readLine());
				}
				
				// 출력 형식 맞추기: 10개의 중앙값을 출력할 때마다(입력 기준 20개마다) 줄바꿈
				if(cnt % 20 == 0) {
					sb.append("\n");
				}
			}
			
			// 테스트 케이스 종료 후 줄바꿈 및 자료구조 초기화
			sb.append("\n");
			next.clear();
			prev.clear();
		}
		
		System.out.println(sb);
	}
	
}