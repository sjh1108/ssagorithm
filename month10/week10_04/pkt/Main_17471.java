package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


// 게리멘더링_17471
public class Main_17471 {
	static int N;
	static int min = Integer.MAX_VALUE;
	static int[] population;
	static List<Integer>[] graph;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); // 6 
		population = new int[N + 1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 인구 입력 받기. 
		for (int i = 1; i < N+1; i++) { // 5 2 3 4 1 2 <- 각 인덱스별 인구수.
			population[i] = Integer.parseInt(st.nextToken());
		}
		
		// 인접리스트 생성 2 2 4 이런 식으로 입력받음. 1번은 2,4와 연결.
		graph = new ArrayList[N+1];
		for (int i = 1; i <= N; i++) {
			graph[i] =  new ArrayList<>();
			st = new StringTokenizer(br.readLine());
			int cnt = Integer.parseInt(st.nextToken());
			for (int j = 0; j < cnt; j++) {
				graph[i].add(Integer.parseInt(st.nextToken()));
			}
		}
		
		// 조합(부분집합)탐색 알고리즘 - 비트마스킹으로 복잡도가 작아짐. 비트마스킹으로 완벽히 A|B 분리가 경우의 수 다 나온다. 
		for (int i = 1; i < (1 << N) - 1; i++) { // 1을 N칸 왼쪽으로 민다. -> 부분집합의 갯수: < 2^N - 1(0, 한쪽이 다가져간 거 제외)
			List<Integer> A = new ArrayList<>();
			List<Integer> B = new ArrayList<>();
			
			
			for (int j = 0; j < N; j++) {
				if((i & (1 << j)) != 0) A.add(j+1); // j는 인덱스이기에,, (j+1) 공통에서 -> 분리 // 실수: %가 아니라 & 
				else B.add(j+1);
			}
			
			// 두 구역이 각각 모두 연결되어 있나 확인 후 인구 차 계산
			if(isConnected(A) && isConnected(B)) {
				int diff = Math.abs(sum(A)-sum(B));
				min = Math.min(min, diff);
			}
		}
		
		
		
		
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
		
		
		
		
	}
	
	 static boolean isConnected(List<Integer> area) {
		 
		 Queue<Integer> q = new ArrayDeque<>();
		 boolean[] visited = new boolean[N+1];
		 q.offer(area.get(0));
		 visited[area.get(0)] = true;
		 int count = 1; // 방문한 구역수 
		 
		 while(!q.isEmpty()) {
			 int cur = q.poll();
			 for (int next : graph[cur]) {
				if(!visited[next] && area.contains(next)) {
					visited[next] = true;
					q.offer(next);
					count++;
				}
			}
		 }
		 return count == area.size(); // 방문한 구역수랑 구역수 같으면 됨. 
	 }
	
	 static int sum(List<Integer> area) {
		 int total = 0;
		 for (int i : area) {
			total += population[i];
		}
		return total;
	 }
	
	
}
