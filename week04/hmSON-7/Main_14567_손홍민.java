import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_14567_손홍민 {
	
	// n: 과목 수, con: 지켜야 할 조건 수, list: 선수과목 -> 이후 수강 가능 과목 인접리스트
	// prevCnt: 과목별 선수과목 수, term: 과목별 가장 빠른 수강 가능 학기(출력용)
	static int n, con;
	static List<List<Integer>> list = new ArrayList<>();
	static int[] prevCnt, term;
	
	public static void main(String[] args) throws Exception {
		init(); topologicalSort();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<n; i++) {
			sb.append(term[i]).append(" ");
		}
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		con = Integer.parseInt(st.nextToken());
		for(int i=0; i<n; i++) {
			list.add(new ArrayList<>());
		}
		prevCnt = new int[n];
		term = new int[n];
		
		for(int i=0; i<con; i++) {
			st = new StringTokenizer(br.readLine());
			int before = Integer.parseInt(st.nextToken()) - 1;
			int after = Integer.parseInt(st.nextToken()) - 1;
			
			// 인접리스트에 조건 등록
			list.get(before).add(after);
			
			// 선수과목 수 카운트
			prevCnt[after]++;
		}
	}
	
	static void topologicalSort() {
		Queue<int[]> q = new ArrayDeque<>();
		
		// 선수과목이 없는 과목은 즉시 수강 가능 -> 큐에 삽입
		// cur[0]: 과목 번호, cur[1]: 수강 가능한 학기
		for(int i=0; i<n; i++) {
			if(prevCnt[i] == 0) q.add(new int[] {i, 1});
		}
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int sub = cur[0], cnt = cur[1];
			// 현재 접근한 과목의 수강 가능한 학기를 등록
			term[sub] = cnt;
			for(int next : list.get(sub)) {
				// 선수 과목 수강 -> 카운트 감소
				// 만약 선수 과목을 모두 수강했다면? -> 큐에 등록
				// 수강 가능 학기는 마지막으로 접근한 과목의 수강 학기 +1
				prevCnt[next]--;
				if(prevCnt[next] == 0) {
					q.add(new int[] {next, cnt+1});
				}
			}
		}
	}
	
}
