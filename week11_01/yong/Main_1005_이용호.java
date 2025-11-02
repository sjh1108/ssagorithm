package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1005_이용호{
/*
 * 백준 1005번: ACM Craft
 * 건물을 짓기위해 선행조건이 필요함 -> 위상 정렬
 */
	static List<Integer>[] graph;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 0; tc < T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // 건물 개수
			int K = Integer.parseInt(st.nextToken()); // 규칙의 개수(간선 개수)
			
			int[] buildTime = new int[N + 1]; // 건물 건설에 걸리는 시간
			int[] D = new int[N + 1]; // 이전에 건설되어야 할 건물 수(차수) 
			
			graph = new LinkedList[N + 1]; // graph[a] -> 건물 a가 지어져야 하는 건물들
			
			for(int i = 1; i <= N; i++) {
				graph[i] = new LinkedList<>();
			}
			
			// 건물 건설에 걸리는 시간 입력
			buildTime = new int[N+1];
			st = new StringTokenizer(br.readLine());
			for(int i = 1; i <= N; i++) {
				buildTime[i] = Integer.parseInt(st.nextToken());
			}
			
			// 건물 건설 제약
			for(int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int front = Integer.parseInt(st.nextToken()); // 먼저 지어져야할 건물
				int back = Integer.parseInt(st.nextToken()); 
				graph[front].add(back);
				D[back]++;
			}
			
			int W = Integer.parseInt(br.readLine()); // 건설해야 하는 건물
			int[] minTime = new int[N + 1];
			
			// 건설 가능한 건물 목록
			Queue<Integer> q = new LinkedList<>();
			// 바로 건설 가능한 건물 큐에 추가
			for (int i = 1; i <= N; i++) {
				if(D[i] != 0) continue;
				q.offer(i);
				minTime[i] = buildTime[i];
			}
			
			while(!q.isEmpty()) {
				int cur = q.poll();
				
				for(int next : graph[cur]) {
					minTime[next] = Math.max(minTime[next], minTime[cur] + buildTime[next]); // 시간 다 더해야 함
					D[next]--;
					if(D[next] != 0) continue;
					q.offer(next);
				}
			}
			
			System.out.println(minTime[W]);
		}

	}

}

