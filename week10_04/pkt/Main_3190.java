package practice;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// 뱀_3190
public class Main_3190 {
	
	static int N, K, L;
	static boolean[][] apple;
	
	static int[] dr = {0, 1, 0, -1};  // 동남서북
	static int[] dc = {1, 0, -1, 0}; 
	
// 행, 열이 x,y 대칭이 반대라서 조심하기 -> 동남서북 ,, 기존에서 위아래만 바꾸기 행, 열 방행 좀따 제대로 확인하기.
//	static int[] dr = {1, 0, -1, 0};  
//	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); // 보드의 크기 행 = y
		K = Integer.parseInt(br.readLine()); // 사과의 개수 열 = x
		
		StringTokenizer st;
		
		apple = new boolean[N+1][N+1]; // 0행, 0열은 안쓰는 방향으로 갈 예정.
		
		// 사과의 위치 - 숫자입력이 들어가는거에서 -1씩 해줘야 함.
		for (int t = 0; t < K; t++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			apple[r][c] = true; 
		}
		
		L = Integer.parseInt(br.readLine()); 
		
		Map<Integer, String> map = new HashMap<>();
		
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			String c = st.nextToken();
			map.put(x,c);
		}
		
		// L은 왼쪽, D는 오른쪽 90도 회전
		
		//System.out.println(BFS(0,0)); -> 애초에 행렬인데, 왜 0,0을 넣지?
		System.out.println(BFS(map)); // 왜 map을 끌고 가지? map을 BFS(map)으로 넘기는 이유는, 단순히 전역변수로 선언하지 않았기 때문
		// 논리적으로 필요한 데이터라면 무엇이든 가져갈 수 있음
		// 단 의존성에 따라 달라짐
		// 보드 전체에 공통적인 것은 전역으로, map처럼 입력으로 주어지는 회전 스케줄은 인자로 넘기는게 깔끔.
		
	}

	private static int BFS(Map<Integer, String> map) {
		Deque<int[]> snake = new ArrayDeque<>();
		boolean[][] visited = new boolean[N+1][N+1];
		
		snake.add(new int[] {1,1}); // 내장함수 잘 알아야 할듯. 생각보다 별로 안될테니 주말간 마스터. 
		visited[1][1] = true;
		
		int dir = 0; // 처음엔 오른쪽. 
		int time = 0;
		
		while(true) {
			
			
			time++; // 한 턴을 여기서 처리하네. 
			int[] head = snake.peekFirst();
			// ex. snake = [ [3,5], [3,4], [3,3] ] -> 머리 = (3,5) 몸통 = (3,4), (3,3)
			// 좌표가 있어서 머리 움직일 수 있게 됨. 
			
			int nr = head[0] + dr[dir];
			int nc = head[1] + dc[dir];
			
			// 머리가 벽이나 자기몸 충돌
			if(nr <=0 || nc <= 0 || nr > N || nc > N || visited[nr][nc]) break;
			
			// 머리 이동
			snake.addFirst(new int[] {nr,nc});
			visited[nr][nc] = true;
			
			// 사과 확인
			if(apple[nr][nc]) {
				apple[nr][nc] = false;
			} else {
				int[] tail = snake.pollLast();
				visited[tail[0]][tail[1]] = false; // 꼬리 지나가서 머리랑 부딪힐 일 없어.
			}
			
			// 회전 처리. 
			if(map.containsKey(time)) {
				String turn = map.get(time);
				if(turn.equals("L")) {
					dir = (dir + 3) % 4;
				}
				else {
					dir = (dir + 1) % 4;
				}
			}
			
		}
		
		return time;
	}
}

// 와,, 이거 꼬리는 머리를 따라가서 범위나 사과 먹는거 영향 안미치니까, 머리만 신경쓰면 됨 ㄷㄷㄷ
// 머리, 사과, 회전만 신경쓰면 됨... ㄷㄷ
// 그치만, 꼬리는 머리랑 부딪힐 수 있고, 1개씩 사라지니까, 그것만 처리해주면 됨 ㄷㄷ
// 회고: 난 아직 한없이 부족하고, 그저 이제 받아들일 수 있는 수준임. 진짜 많이 소화하자. 수학의 정석 1.5회독 한 정도 수준
// String으로 하고, Char로 하지 않은 이유: char c = st.nextToken().charAt(0); 추가 변환 비용 + 코드 길어짐.