package week;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - N 명의 학생이 키순서대로 줄
 * - 두 학생의 키를 비교
 * 
 * 입력 
 * - N M (학생수, 키를비교한횟수)
 * - M개의 비교 A B (A가 B 앞에 선다)
 * 
 * 출력
 * - 첫째 줄에 학생들을 앞에서부터 줄세운 결과
 * 
 * 전략
 * - A -> B 방향의 그래프 생성
 * - 
 *
 */
public class Main_2252_줄세우기 {

	static int N, M; // 노드, 간선수)
	static List<Integer>[] adj;
	static int[] head;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 인접리스트 
		adj = new ArrayList[N+1];
		for(int i = 1; i <= N; i++) {
			adj[i] = new ArrayList<>();
		}// 초기화
		
		head = new int[N+1];//초기화
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			
			adj[from].add(to);
			
			head[to] += 1;
		}// 입력끝

		tsort();
		
		System.out.println(sb.toString());
	}
	
	private static void tsort() {
		
		Queue<Integer> q = new ArrayDeque<>();
		for(int i = 1; i <= N; i++) {
			if(head[i] == 0) {
				q.offer(i);
				sb.append(i).append(" ");
			}

		}


		while(!q.isEmpty()) {
			int now = q.poll();
			
			for(int next : adj[now]) {
				head[next] -= 1;
				
				if(head[next] == 0) {
					q.offer(next);
					sb.append(next).append(" ");
				}
			}
		}
		
		
	}
	
	
}
