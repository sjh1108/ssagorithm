import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_2696_손홍민 {
	
	// 알고리즘 분류 : 우선순위 큐
	// 구상 : 우선순위 큐 2개를 이용해 입력값을 받을 때마다 실시간으로 중앙값을 갱신한다
	// 문제 풀이 :
	// 1. 2개의 우선순위 큐를 선언한다.
	// 1-1. 하나는 중앙값보다 작은 값을 담으며, 그 중 가장 큰 값이 peek값이 되는 prev 큐(내림차순)
	// 1-2. 또 하나는 중앙값보다 큰 값을 담으며, 그 중 가장 작은 값이 peek값이 되는 next 큐(오름차순)
	// 2. 입력받는 수열의 첫번째 값을 초기 중앙값으로 설정한다.
	// 3. 이후 수를 2개 입력받을 때마다 두 우선순위 큐의 크기를 비교하여 중앙값을 재조정한다.
	// 3-1. prev의 크기가 크다면 중앙값을 next로 밀어내고 prev에서 제일 큰 값을 중앙값으로 설정
	// 3-2. next의 크기가 크다면 중앙값을 prev로 밀어내고 next에서 제일 작은 값을 중앙값으로 설정
	// 3-3. 두 우선순위 큐의 크기가 동일하다면 현상 유지
	// 4. 홀수번째 입력마다(초기값 포함) 그 순간의 중앙값을 출력한다.
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int n;
	// 중앙값보다 큰 수를 담는 우선순위 큐. 오름차순으로 설정하여 중앙값 재조정시 기존의 중앙값과 가장 가까운 수가 새 중앙값이 되도록 함.
	static PriorityQueue<Integer> next = new PriorityQueue<>();
	// 중앙값보다 작은 수를 담는 우선순위 큐. 내림차순으로 설정하여 중앙값 재조정시 기존의 중앙값과 가장 가까운 수가 새 중앙값이 되도록 함.
	static PriorityQueue<Integer> prev = new PriorityQueue<>(Collections.reverseOrder());
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc--> 0) {
			n = Integer.parseInt(br.readLine());
			
			sb.append((n+1)/2 + "\n");
			st = new StringTokenizer(br.readLine());
			int mid = Integer.parseInt(st.nextToken());
			sb.append(mid + " ");
			int cnt = 1;
			// 중앙값 재조정 및 출력은 홀수번째 입력시에만 처리
			boolean oddCheck = true;
			
			// 입력시 숫자 10개 단위로 들어옴
			for(int d=0; d<=n/10; d++) {
				while(st.hasMoreTokens()) {
					int x = Integer.parseInt(st.nextToken());
					// 중앙값보다 크거나 같은 수는 next로
					if(x >= mid) next.add(x);
					// 중앙값보다 작은 수는 prev로
					else prev.add(x);
					cnt++;
					oddCheck = !oddCheck;
					if(!oddCheck) continue;
					
					// 중앙값 재조정. 기존 중앙값을 밀어내고 크기가 큰 우선순위 큐의 peek를 새 중앙값으로 만들어 균형 유지
					if(prev.size() > next.size()) {
						next.add(mid);
						mid = prev.poll();
					} else if(next.size() > prev.size()){
						prev.add(mid);
						mid = next.poll();
					}
					
					sb.append(mid + " ");
				}
				
				if(cnt%10 == 0) {
					st = new StringTokenizer(br.readLine());
					if(cnt%20 == 0) {
						cnt = 0;
						sb.append("\n");
					}
				}
			}
			
			sb.append("\n");
			next.clear();
			prev.clear();
		}
		
		System.out.println(sb);
	}
	
}
