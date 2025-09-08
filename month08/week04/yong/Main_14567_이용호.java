package month08.week04.yong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_14567_이용호 {
/*
 * 모든 과목에 대해 각 과목을 이수하려면 몇 학기가 걸리는지 계산하는 프로그램 작성
 *
 */
	static List<LinkedList<Integer>> graph;
	static int[] degree;
	static Queue<Integer> q;
	static int[] seq;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 과목의 수
		int M = Integer.parseInt(st.nextToken()); // 선수과목 조건
		
		degree = new int[N+1];
		seq = new int[N+1];
		
		graph = new LinkedList<>(); // {}
		for(int i = 0; i <= N ;i++) { //1base
			graph.add(new LinkedList<>()); //{{}, {}, N개} 
		}
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// A듣고 B들어야 한다. A -> B
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			degree[B] += 1;
			graph.get(A).add(B);
		}
		
		q = new LinkedList<>();
		for(int i = 1; i <= N; i++) {
			if(degree[i] == 0) q.add(i);
			seq[i] += 1;
		}
		bfs();
		for(int i = 1; i <= N; i++) {
			System.out.print(seq[i] +" ");
		}
	}
	
	//1. 큐 원소 빼고 연결된 원소들 차수 빼주기, 2. 그중 차수 0인 원소 큐에 넣어주기
	private static void bfs() {
		while(!q.isEmpty()) {
			int now = q.poll();
			for(int connected : graph.get(now)) {
				degree[connected]--;
				//connected 과목은 now 과목을 선수로 하므로, 학기는 (now 학기 + 1) 이상이어야 함
				seq[connected] = Math.max(seq[connected], seq[now] + 1);
				if(degree[connected] == 0) {
					q.add(connected);
				}
			}
			
		}
	}
}
