import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_1863_스카이라인_쉬운거 {
	
	// BOJ_1863 : 스카이라인 쉬운거(Gold_4)
	// 자료구조 및 알고리즘 : 스택
	// 모든 건물은 높이가 자연수인 직사각형이다. 건물들의 윤곽(스카이라인)만 보고 건물이 최소 몇 채인지 구해야 한다.
	// 스택을 이용해 높이 정보가 바뀔 때마다 건물의 높이를 스택에 저장하거나, 건물의 개수를 카운트해야 한다.
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		// 스택 구현을 위한 덱 라이브러리 호출
		// 왜 스택이 아니라 덱을 썼는가?
		// -> Vector 기반 컬렉션인 Stack은 동기화 지원, Array 기반 컬렉션인 Deque은 동기화 지원 X
		// -> 동기화 지원  : 멀티 스레드 환경에서 일관성 보장. 그러나 싱글 스레드 환경에서는 동기화 오버헤드만 가진 채로 장점은 사라짐
		// -> 따라서 알고리즘 문제 풀이와 같은 싱글 스레드 환경에서는 동기화 오버헤드 없이 스택 구현이 가능한 Deque 사용
		Deque<Integer> stack = new ArrayDeque<>();
		
		// 건물의 개수 카운터
		int cnt = 0;
		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			// 건물의 높이가 변경되는 위치, 이 문제 풀이에서는 사용할 필요가 없음
			int idx = Integer.parseInt(st.nextToken());
			// 건물의 높이
			int h = Integer.parseInt(st.nextToken());
			
			// 현재 스택에 저장된 건물보다 낮은 높이의 건물이 등장했다면
			// h보다 높은 건물들은 다 현재 위치에서 끝났다는 것을 의미함 -> h보다 높은 건물들을 전부 제거하고 건물 개수 카운트
			while(!stack.isEmpty() && stack.peekLast() > h) {
				stack.removeLast();
				cnt++;
			}
			
			// h=0은 땅임. 건물로 취급하지 않음
			// 스택이 비어있거나 h가 peek보다 크면 즉시 건물의 높이를 스택에 추가
			// 만약 스택의 peek와 h의 높이가 같다면? h보다 높은 건물들이 제거된 이후 같은 높이에서 만남 -> 같은 건물이므로 스택에 h를 추가하지 않음
			if((stack.isEmpty() || stack.peekLast() < h) && h > 0) stack.addLast(h);
		}
		
		// 순회 종료 후 스택에 남아있는 모든 건물 정보를 처리하고 카운트
		while(!stack.isEmpty()) {
			stack.removeLast();
			cnt++;
		}
		
		System.out.println(cnt);
	}

}
