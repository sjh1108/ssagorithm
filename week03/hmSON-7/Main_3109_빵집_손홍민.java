import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_3109_빵집_손홍민 {
	
	// 각각 맵 크기, 파이프 개수 카운트
	static int r, c, pathCnt = 0;
	static boolean[][] map;
	// 우측 상단, 중앙, 하단으로만 이동 가능
	// path : 방문 체크용 비트마스킹 배열
	static int[] dy = {-1, 0, 1}, path;
	
	public static void main(String[] args) throws Exception {
		init();
		
		for(int i=0; i<r; i++) {
			path[0] = i;
			setPipe(i, 0);
		}
		System.out.println(pathCnt);
	}
	
	public static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new boolean[r][c];
		path = new int[c];
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				map[i][j] = line.charAt(j) == 'x';
			}
		}
	}
	
	public static void setPipe(int y, int x) {
		// 마지막 라인까지 도착한 경우
		if(x == c-1) {
			// 지금까지 지나온 위치에 파이프 설치 및 파이프 카운트
			for(int i=0; i<c; i++) {
				int cy = path[i];
				map[cy][i] = true;
			}
			pathCnt++;
			return;
		}

		// 다음 경로 확인
		for(int i=0; i<3; i++) {
			int ny = y + dy[i];
			if(ny < 0 || ny >= r || map[ny][x+1]) continue;
			path[x+1] = ny;
			setPipe(ny, x+1);
			// 파이프 완성 후 빠른 복귀를 위해 파이프 위치를 역추적하면서 리턴
			if(map[y][x]) return;
		}
		map[y][x] = true;
	}
	
}
