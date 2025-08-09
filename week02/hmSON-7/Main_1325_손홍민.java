package silver_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1325_손홍민 {

	static int n, m, maxHacked;
	static List<List<Integer>> comList = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 각각 컴퓨터의 수와 신뢰 관계의 수
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		// 각 컴퓨터에 대한 신뢰 리스트 생성
		for(int i=0; i<n; i++) {
			comList.add(new ArrayList<>());
		}
		
		// 그래프 생성
		// 해커는 신뢰 관계를 역으로 이용하여 신뢰받는 컴퓨터부터 해킹해야 효율적이므로 역순으로 리스트에 관계 추가
		// 신뢰받는 대상 컴퓨터 -> 타겟을 신뢰하는 컴퓨터
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int trust = Integer.parseInt(st.nextToken()) - 1;
			int target = Integer.parseInt(st.nextToken()) - 1;
			comList.get(target).add(trust);
		}
		maxHacked = 1;
	}
	
	public static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 각각 컴퓨터의 수와 신뢰 관계의 수
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		// 각 컴퓨터에 대한 신뢰 리스트 생성
		for(int i=0; i<n; i++) {
			comList.add(new ArrayList<>());
		}
		
		// 그래프 생성
		// 해커는 신뢰 관계를 역으로 이용하여 신뢰받는 컴퓨터부터 해킹해야 효율적이므로 역순으로 리스트에 관계 추가
		// 신뢰받는 대상 컴퓨터 -> 타겟을 신뢰하는 컴퓨터
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int trust = Integer.parseInt(st.nextToken()) - 1;
			int target = Integer.parseInt(st.nextToken()) - 1;
			comList.get(target).add(trust);
		}
		maxHacked = 1;
	}
	
	public static void solve() {
		List<Integer> result = new ArrayList<>();
		for(int i=0; i<n; i++) {
			// 각 컴퓨터부터 시작하는 BFS 알고리즘
			Queue<Integer> q = new ArrayDeque<>();
			q.offer(i);
			// 중복 방지
			boolean[] visited = new boolean[n];
			visited[i] = true;
			// 해킹할 수 있는 컴퓨터 수
			int hackedCnt = 1;
			
			while(!q.isEmpty()) {
				List<Integer> nextList = comList.get(q.poll());
				for(int j=0; j<nextList.size(); j++) {
					int value = nextList.get(j);
					if(visited[value]) continue;
					visited[value] = true;
					hackedCnt++;
					q.offer(value);
				}
			}
			
			// 탐색 결과가 현재 기록된 최대 컴퓨터 수보다 크다면 값 갱신 및 출력할 컴퓨터 리스트 초기화
			// 탐색 결과가 현재 기록된 최대 컴퓨터 수와 동일하다면 출력할 컴퓨터 리스트에 해당 컴퓨터 인덱스 추가
			if(hackedCnt > maxHacked) {
				result.clear();
				result.add(i+1);
				maxHacked = hackedCnt;
			} else if(hackedCnt == maxHacked) result.add(i+1);
		}
		
		// 리스트 출력
		StringBuilder sb = new StringBuilder();
		for(int x : result) {
			sb.append(x).append(" ");
		}
		System.out.println(sb);
	}
	
}
