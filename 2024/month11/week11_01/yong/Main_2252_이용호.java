package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2252_이용호 {
/*
 * 백준 2252 : 줄 세우기
 * A B -> A가 B앞에 서야함
 * 앞에서 부터 출력
 */
	static List<Integer>[] graph;
	static int[] D;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		graph = new LinkedList[N + 1];
		D = new int[N + 1]; // 진입차수 배열 (각 학생이 앞에 서야 하는 사람 수)
		
		for(int i = 1 ; i <= N; i++) {
			graph[i] = new LinkedList<>(); // graph[a] = a가 앞에 서야 하는 학생들의 리스트
		}

		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// A가 B 앞에 서야한다
			int front = Integer.parseInt(st.nextToken()); // A
			int back = Integer.parseInt(st.nextToken()); // B
			
			graph[front].add(back); // a -> b
			D[back]++; // back의 앞사람 증가
			
		}
		
		// 앞사람 없는 학생 큐에 삽입
		Queue<Integer> q = new LinkedList<>();
		for(int i = 1; i <= N; i++) {
			if(D[i] != 0) continue;
			q.offer(i);
		}
		
		StringBuilder sb = new StringBuilder();
		while(!q.isEmpty()) {
			int cur = q.poll();
			sb.append(cur + " ");
			
			// 뒤 학생들 탐색
			for(int next : graph[cur]) {
				D[next]--; // 현재 학생 제거(차수 감소)
				if(D[next] != 0) continue;
				q.offer(next); // 진입 차수 0 -> 큐추가
			}
		}
		System.out.println(sb);
	}
	

}
