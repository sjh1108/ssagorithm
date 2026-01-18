import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_3665 {
	
	// BOJ_3665 : 최종 순위(Gold_1)
	// 자료구조 및 알고리즘 : 위상 정렬, 해시맵
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder(), newRank;
	static StringTokenizer st;
	
	// 각 테스트 케이스의 정점과 간선 수
	static int v, e;
	// 지난 해 순위별 참가팀 번호(key : 지난 해의 순위, value : 해당 순위의 팀 번호)
	static HashMap<Integer, Integer> lastYear;
	// 각 팀의 앞에 있는 다른 팀들의 수
	static int[] prev;
	// list : 올해 대회에서 각 팀별로 자신보다 뒤에 있는 팀들의 지난 해 순위를 리스트로 저장(단방향)
	// reversed : 지난 해와 비교했을 때 순위가 뒤집힌 팀의 번호 쌍(양방향)
	static List<Integer>[] list, reversed;
	
	static void init() throws Exception {
		v = Integer.parseInt(br.readLine());
		lastYear = new HashMap<>();
		prev = new int[v];
		list = new ArrayList[v];
		reversed = new ArrayList[v];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<v; i++) {
			// 지난 해의 순위 및 팀 번호 입력
			lastYear.put(i, Integer.parseInt(st.nextToken()) - 1);
			list[i] = new ArrayList<>();
			reversed[i] = new ArrayList<>();
		}
		
		e = Integer.parseInt(br.readLine());
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			// 순위가 변경되어야 하는 두 팀의 번호. 입력 순서는 사전순이므로 두 팀의 기존 순위와 관련이 없다.
			int v1 = Integer.parseInt(st.nextToken())-1;
			int v2 = Integer.parseInt(st.nextToken())-1;
			
			// 따라서 reversed 인접 리스트에는 양방향으로 저장한다.
			reversed[v1].add(v2);
			reversed[v2].add(v1);
		}
		
		for(int i=0; i<v; i++) {
			for(int j=i+1; j<v; j++) {
				// 두 개의 팀을 각각 비교하여 앞인지, 뒤인지 판단 후 리스트에 관계를 저장
				int idx1 = lastYear.get(i);
				int idx2 = lastYear.get(j);
				
				// 이번 해에 순위가 역전되었는가?
				if(reversed[idx1].contains(idx2)) {
					list[j].add(i);
					prev[i]++;
				} else {
					list[i].add(j);
					prev[j]++;
				}
			}
		}
	}
	
	static boolean topologySort() {
		// 모든 팀을 확인했는지 판단하기 위한 방문 카운트
		int visitCnt = 0;
		// 만약 정상적인 순위 관계인 경우 이 스트링빌더에 기록된 올해의 순위를 출력할 것
		newRank = new StringBuilder();
		Queue<Integer> q = new ArrayDeque<>();
		for(int i=0; i<v; i++) {
			if(prev[i] > 0) continue;
			q.add(i);
			visitCnt++;
		}
		
		while(!q.isEmpty()) {
			int curRank = q.poll();
			
			// curRank는 지난 해의 순위임. 그 순위에 해당하는 팀의 번호를 기록
			newRank.append((lastYear.get(curRank)+1) + " ");
			
			// 뒤에 있는 모든 팀의 prev[next]를 디카운트
			for(int next : list[curRank]) {
				prev[next]--;
				if(prev[next] > 0) continue;
				
				q.add(next);
				visitCnt++;
			}
		}
		
		// 확인한 팀 숫자가 v와 동일하면 정상적인 순위 변동
		// 그렇지 않다면 모순된 형태임
		return visitCnt == v;
	}
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc-- > 0) {
			init();
			
			// 정상적인 순위 관계인 경우 : 기록된 올해의 순위 출력
			// 모순된 순위 관계인 경우 : IMPOSSIBLE 출력 
			sb.append(topologySort() ? newRank : "IMPOSSIBLE").append("\n");
		}
		
		System.out.println(sb);
	}

}
