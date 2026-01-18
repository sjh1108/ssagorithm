package month08.week04.kjs;
//다시풀어봐라
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class BOJ_2630_색종이만들기 {
	static int[][] map;
	static int white;
	static int blue;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		divide(0,0,N);
		System.out.println(white);
		System.out.println(blue);

	}

	static void divide(int r, int c, int n) {
		if (isSplit(r, c, n)) {
			if (map[r][c] == 0) {
				white++;
			} else {
				blue++;
			}
			return;
		}
		int half = n/2;
        divide(r, c, half);                 // 1사분면 (좌상)
        divide(r, c + half, half);          // 2사분면 (우상)
        divide(r + half, c, half);          // 3사분면 (좌하)
        divide(r + half, c + half, half);   // 4사분면 (우하)
		
	}

	static boolean isSplit(int r, int c, int n) {
		int firstN = map[r][c];
		for (int i = r; i < r+n; i++) {
			for (int j = c; j < c+n; j++) {
				if (map[i][j] != firstN)
					return false;
			}
		}
		return true;
	}

}
