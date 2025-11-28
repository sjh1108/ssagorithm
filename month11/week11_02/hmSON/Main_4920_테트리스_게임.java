import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4920_테트리스_게임 {
	
	/*
	 * BOJ_4920 : 테트리스 게임(Gold_5)
	 * 자료구조 및 알고리즘 : 구현, 브루트포스
	 * 
	 * 테트리스의 다섯 조각(직선, 반전 S자, 반전 L자, T자, 정사각형 ㅁ자) 중 하나를 표에 놓았을 때 블록 아래에 있는 숫자의 합의 최대값을 구해야 한다.
	 * 모든 테트리스 블록은 90도씩 회전시킬 수 있지만 뒤집을 수는 없으며, 블록이 모두 표 안에 들어있는 형태여야 한다.
	 * 표의 크기는 최소 4 최대 100까지 주어지고, 표에 쓰여 있는 각 숫자는 절대값이 1_000_000 이하인 정수이다.
	 * 입력의 마지막 줄은 0 입력으로 구분한다.
	 * 
	 * 입력값에 무의미한 띄어쓰기가 많으니 적절히 공백만 쳐내고 필요한 값만 변수와 배열에 저장할 수 있어야 한다.
	 */
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	// 표 크기, 각 테스트 케이스의 최대값
	static int n, max;
	// 표 정보
	static int[][] table;
	// 테트리스 조각 목록
	static int[][][] tetris = {
            // 직선
            {{0,0}, {0,1}, {0,2}, {0,3}},  // ㅡ
            {{0,0}, {1,0}, {2,0}, {3,0}},  // ㅣ

            // 반전 S자 모양
            {{0,1}, {1,1}, {1,0}, {2,0}},
            {{0,0}, {0,1}, {1,1}, {1,2}},
            
            // 반전 L자 모양
            {{0,1}, {1,1}, {2,0}, {2,1}},
            {{0,0}, {0,1}, {0,2}, {1,2}},
            {{0,0}, {0,1}, {1,0}, {2,0}},
            {{0,0}, {1,0}, {1,1}, {1,2}},

            // T자 모양
            {{0,1}, {1,0}, {1,1}, {1,2}},  // ㅗ
            {{0,1}, {1,0}, {1,1}, {2,1}},  // ㅓ
            {{0,0}, {0,1}, {0,2}, {1,1}},  // ㅜ
            {{0,0}, {1,0}, {2,0}, {1,1}},  // ㅏ
            
            // 정사각형 모양
            {{0,0}, {0,1}, {1,0}, {1,1}},  // ㅁ
    };
	
	static void init() throws Exception {
		// 입력값에 쓸데없는 띄어쓰기가 많음. 일단 사이드부터 제거
		n = Integer.parseInt(br.readLine().trim());
		// 입력값이 0이면 입력 종료를 의미
		if(n == 0) return;
		
		table = new int[n][n];
		for(int i=0; i<n; i++) {
			// 마찬가지로 사이드의 띄어쓰기 제거
			StringTokenizer st = new StringTokenizer(br.readLine().trim());
			// 숫자 또한 가장 큰 자릿수에 맞춰 띄어쓰기가 들어가 있으므로 Token중 아무것도 없는 값이 존재할 수 있음.
			// 따라서 숫자가 존재하는 토큰만 처리하고 나머지는 무시
			int idx = 0;
			while(st.hasMoreTokens()) {
				String token = st.nextToken();
				if(token.length() == 0) continue;
				
				table[i][idx++] = Integer.parseInt(token);
			}
		}
	}
	
	// 현재 위치(r, c)를 기준으로 조각 설치
	// 만약 표를 넘어가는 블록이 하나라도 있으면 취급하지 않음
	static void getPoint(int r, int c) {
		for(int[][] block : tetris) {
			int sum = 0;
			// 배열 범위 초과 플래그. 하나라도 표를 넘어가는 블록이 존재하면 즉시 false로 변경
			boolean flag = true;
			for(int[] unit : block) {
				int y = unit[0] + r, x = unit[1] + c;
				if(y < 0 || x < 0 || y >= n || x >= n) {
					flag = false;
					break;
				}
				sum += table[y][x];
			}
			
			if(flag && sum > max) max = sum;
		}
	}
	
	public static void main(String[] args) throws Exception {
		int tc = 1;
		while(true) {
			init();
			// 0이 입력값일 때 모든 입력 종료
			if(n == 0) break;
			
			// 표에는 음수가 포함될 수 있음(출력값은 최소 -4_000_000임)
			// 그러므로 항상 초기값은 그 아래여야 함
			max = Integer.MIN_VALUE;
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					getPoint(i, j);
				}
			}
			
			sb.append(tc + ". " + max + "\n");
			tc++;
		}
		
		System.out.println(sb);
	}

}
