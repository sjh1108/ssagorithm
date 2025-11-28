package week11_04.Minsang.BOJ_2667;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2667 {
	static int N;
	static int[][] map;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static boolean[][] visited;
	// 1 : 집이 있는 곳, 0 : 집이 없는 곳
	// 출력 : 단지 수 출력, 각 단지에 속하는 집의 수를 오름차순으로 정렬하여 출력.
	
	// 내 생각엔 BFS야 !!!!!!!
	// 가 아니었어. DFS였어. -> 왜 ? 모든 아파트를 돌면서 단지로 묶어줘야하니까
	// 각 단지에 속하는 집의 수를 담아서 오름차순으로 정렬 -> 출력 ArrayList에 담아주기
	
	// 클래스 노드 따로 뺀다
	static class Node {
		int x;
		int y;
		
		Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		visited = new boolean[N][N];
		
		// 맵 입력 받아 -> 문자열로 받았으니까 숫자로 변환하기 위해서는 charAt 해줘야함 !
		for(int i = 0; i < N; i++) {
			String line = br.readLine();
			for(int j = 0; j < N; j++) {
				map[i][j] = line.charAt(j) - '0';
			}
		}
		
		// 인접하는 단지 -> 개수 체크
		// 상하좌우 방향으로 1로 인접해 있는 크기 구하기
		// 각 단지내 집의 수를 오름차순으로 정렬하여 한 줄에 하나씩 출력
		
		// 단지 크기를 저장 할 리스트 만들어줘야함
		ArrayList<Integer> danjiSize = new ArrayList<>();
		
		// 맵을 돌면서 집을 찾고, 단지를 탐색해야겠찌 ?
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] == 1 && !visited[i][j]) { // 완전 탐색하는데, 탐색 범위 지정 해주기 -> 방문 안한곳, 집이 있는 곳
					int size = bfs(i, j);// 단지 크기 구하기 (여기서 bfs 돌아야함)
					danjiSize.add(size);		// 단지 크기 리스트에 추가
				}
			}
		}
		
		// 단지 크기 오름차순 정렬
		Collections.sort(danjiSize);
		
		// 출력해주삼
		System.out.println(danjiSize.size());
		for(int size : danjiSize) {
			System.out.println(size);
		}
	}

	// bfs 메서드
	private static int bfs(int x, int y) {
		Queue<Node> q = new ArrayDeque<>();
		q.add(new Node(x, y));
		visited[x][y] = true; // 시작점 방문처리
		
		int cnt = 1; // 1부터 시작
		
		// Node 클래스에 q가 비어있을때까지 빼주기
		while(!q.isEmpty()) {
			Node node = q.poll();
			
			// 현재 좌표
			int cx = node.x;
			int cy = node.y;
			
			// for문 돌아서 -> 다음 칸 출력
			// 범위 지정 ( cnt++, dfs도 돌아주기 )
			for(int d = 0; d < 4; d++) {
				
				int nx = cx + dx[d];
				int ny = cy + dy[d];
				
				// 범위 지정 & 방문하지 않은 곳 & 집이 있는 곳
				if(nx >= 0 && ny >= 0 && nx < N && ny < N && !visited[nx][ny] && map[nx][ny] == 1) {
					// 방문하면 방문 처리
					visited[nx][ny] = true;
					// 다음칸 리스트에 넣어주고, cnt ++ -> return 
					q.add(new Node(nx, ny));
					cnt++;
			}
		}
		
	}
		return cnt;
	}
	// 내가 뭘 놓친거였지 ..................
}