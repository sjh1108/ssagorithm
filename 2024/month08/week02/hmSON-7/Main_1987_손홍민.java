import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1987_손홍민 {
	
	static int r, c, maxCnt = 1;
	static char[][] map;
	static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
	
	public static void main(String[] args) throws Exception {
		init(); 
		char start = map[0][0];
		int bit = 1<<(start - 'A');
		dfs(0, 0, bit, 1);
		System.out.println(maxCnt);
	}
	
	public static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new char[r][c];
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				map[i][j] = line.charAt(j);
			}
		}
	}
	
	public static void dfs(int y, int x, int bit, int cnt) {
		if(cnt > maxCnt) maxCnt = cnt;
		for(int i=0; i<4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];
			if(!isIn(ny, nx)) continue;
			int newMask = 1 << (map[ny][nx] - 'A');
			if((newMask & bit) == 0) dfs(ny, nx, bit | newMask, cnt+1);
		}
 	}
	
	public static int countBit(int mask) {
		if(mask == 0) return 0;
		return mask%2 + countBit(mask/2);
	}
	
	public static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < r && x < c;
	}
	
}
