package week11_04.Minsang.BOJ_16928;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_16928{
	static int N, M;
	static int[] move; // 사다리나 뱀  이동 정보
	static boolean[] visited; // 방문 체크
	static int minCost;
	// 그냥 시뮬레이션 구현 같지 않음 ???????????????????????????????
	// 너비 탐색 이래 .. 그럼 bfs 쓰라는거잖아. 근데 구현으로도 통할것같은데
	
	// 구해야하는 것 : 주사위를 굴러야 하는 횟수의 최솟값
	// 사다리를 이용해 이동한 칸 = ++, 뱀을 이용해 이동한 칸 = --
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		move = new int[101];
		visited = new boolean[101];
		
		// 입력을 받긴 할 건데, x,y 칸 
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			move[x] = y;
		}
		
		for(int i= 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			move[u] = v;
		}
		
		System.out.println(bfs(move, visited));
	}
	
	// 자 차근차근 주석부터 달아보자 (일단 bfs 돌거니까 필요한 매개변수 생각해보자. 이동하는 곳, 방문한 곳 체크해야하니까 이 두개가 필요해)
	static int bfs(int[] move, boolean[] visited) {
		Queue<Integer> q = new LinkedList<>(); // 게임 -> 보드판에서 진행하는 형식 -> linkedList로 해보잣
		q.add(1); // 보드판 시작점이 1
		visited[1] = true; // 방문했으니까 1번 체크
		
		int cnt = 0;
		
		while(!q.isEmpty()) { // q가 빌때까지 빼야겠지
			int size = q.size();
			
			for(int i = 0; i < size; i++) { // size만큼 돌아봐요
				int cur = q.poll(); // cur에 q 빼기
				
				if(cur == 100)	return cnt; // 현재 위치가 100이면  더이상 탐색할 필요 x, cnt 리턴
				
				for(int dice = 1; dice <=6; dice++) { // 주사위 만큼
					int next = cur + dice; // 다음칸은 현재 칸 + 주사위 수
					
					if(next > 100) continue; // 다음칸이 100 번째 칸이 되면 xx
					if(visited[next]) continue; //다음 칸을 방문했던 곳이면 xx
					
					visited[next] = true; // 다음칸 방문 처리

					if(move[next] != 0) { // 뱀도 없고, 사다리도 없으면 다음칸으로 이동
						next = move[next];
					}
					q.add(next);
				}

			}
			cnt++;

		}
		
		return cnt;
	}
}

