import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_9466_텀_프로젝트 {
	
	/*
	 * BOJ_9466 : 텀 프로젝트 (Gold_3)
	 * 자료구조 및 알고리즘 : DFS
	 *
	 * [문제 요약]
	 * - 학생들은 같이 하고 싶은 학생을 딱 한 명 지목한다. (자기 자신 포함)
	 * - 서로가 서로를 지목하는 사이클(Cycle)이 형성되면 그들은 한 팀이 된다.
	 * - 어느 팀에도 속하지 못한 학생들의 수를 구하라.
	 *
	 * [핵심 아이디어]
	 * - 방향 그래프에서 사이클을 찾는 문제다.
	 * - 일반적인 boolean 방문 배열로는 '현재 탐색 경로에서 만난 노드(사이클 O)'와
	 * '이미 예전에 탐색이 끝난 노드(사이클 X)'를 구분할 수 없다.
	 * - 따라서 3가지 상태로 노드를 관리한다.
	 * 1. r (Ready): 아직 방문하지 않음.
	 * 2. v (Visiting): 현재 DFS 탐색 스택에 들어있는 상태 (방문 중).
	 * 3. f (Finished): 탐색이 완전히 종료되어 사이클 여부 판단이 끝난 상태.
	 *
	 * [구현 메모]
	 * - DFS를 진행하다가 다음 노드가 'v' 상태라면, 현재 경로 상에서 다시 만난 것이므로 '사이클'이다.
	 * - 사이클이 발견되면, 해당 사이클에 포함된 학생 수만큼 전체 학생 수(cnt)에서 뺀다.
	 *
	 * [시간 복잡도]
	 * - 각 정점은 r -> v -> f 순서로 단 한 번씩만 상태가 변한다.
	 * - 따라서 O(N)으로 해결 가능하다.
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int n, cnt; // 전체 학생 수, 팀에 속하지 못한 학생 수(정답)
	static int[] target; // 지목한 학생 번호 저장
	static char[] state; // 상태 배열 (r: ready, v: visiting, f: finished)
	
	static void init() throws Exception {
		n = Integer.parseInt(br.readLine());
		target = new int[n];
		state = new char[n];
		Arrays.fill(state, 'r'); // 모든 학생을 초기 상태(ready)로 설정
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			target[i] = Integer.parseInt(st.nextToken()) - 1; // 0-based index
		}
	}
	
	static void dfs(int cur) {
		state[cur] = 'v'; // 현재 노드를 '방문 중'으로 표시
		int next = target[cur]; // 다음 지목 대상
		
		if(state[next] == 'r') {
			// 1. 아직 방문하지 않은 노드라면 계속 탐색
			dfs(next);
		} else {
			// 2. 이미 방문한 노드를 만남
			// 만약 그 노드가 'v'(방문 중) 상태라면 사이클이 발생한 것임
			// ('f' 상태라면 이미 처리가 끝난 노드이므로 사이클 아님 -> 무시)
			if(state[next] == 'v') {
				// 사이클에 포함된 학생들을 카운트에서 제외 (팀 결성 성공)
				
				// 일단 사이클을 발견한 지점(next)까지 도달한 'cur'도 팀원이므로 감소
				cnt--;
				
				// next부터 다시 next를 만날 때까지 추적하여 사이클 구성원들을 감소
				for(int i = next; i != cur; i = target[i]) {
					cnt--;
				}
			}
		}
		
		state[cur] = 'f'; // 현재 노드에 대한 탐색 종료 (Finished)
	}
	
	public static void main(String[] args) throws Exception {
		int t = Integer.parseInt(br.readLine());
		while(t-- > 0) {
			init();
			cnt = n; // 전체 학생 수로 초기화 (나중에 팀원을 뺌)
			
			for(int i=0; i<n; i++) {
				// 아직 방문하지 않은 학생에 대해서만 DFS 수행
				if(state[i] != 'r') continue;
				dfs(i);
			}
			
			sb.append(cnt + "\n");
		}
		
		System.out.println(sb);
	}

}