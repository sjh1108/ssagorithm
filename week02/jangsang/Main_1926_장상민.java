package Boj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1926_장상민 {
	    static Queue<int[]> q = new ArrayDeque<>();
	    static int[][] map;
	    static int[] dr = {-1,1,0,0};
	    static int[] dc = {0,0,-1,1};
	    static int N;
	    static int M;
	    static int maxSize = 0;
	
	//bfs 기본구조..!
	public static void bfs(int y, int x) {
	    q.add(new int[] {y, x});
	    map[y][x] = 0;
	    int cnt = 0;
	    while(!q.isEmpty()) {
	        int[] now = q.poll();
	        cnt++;
	        //탐색
	        for (int i = 0; i < 4; i++) {
	            int newy = now[0] + dr[i];
	            int newx = now[1] + dc[i];
	            if(!isIn(newy, newx) || map[newy][newx] == 0) {
	                continue;
	            }
	            // 여기까지 통과하면 1임
	            q.add(new int[] {newy, newx});
	            // 탐색한 부분 0으로 초기화.
	            map[newy][newx] = 0;
	        }
	    }
	    if (cnt > maxSize) {
	        maxSize = cnt;
	    }
	    
	}
	
	// 배열범위 검사
	public static boolean isIn(int y, int x) {
	    return y >= 0 && x >= 0 && y < N && x < M;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    
	    StringTokenizer st = new StringTokenizer(br.readLine());
	    
	    N = Integer.parseInt(st.nextToken());
	    M = Integer.parseInt(st.nextToken());
	    
	    map = new int[N][M];
	    
	    //배열입력받기
	    for (int i = 0; i < N; i++) {
	        st = new StringTokenizer(br.readLine());
	        for (int j = 0; j < M; j++) {
	            map[i][j] = Integer.parseInt(st.nextToken());
	        }
	    }
	    
	    int artCnt = 0;
	    // 돌리고 돌리고~
	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < M; j++) {
	            if(map[i][j] == 1 ) {
	                bfs(i,j);
	                artCnt++;
	            }
	        }
	    }
	    
	    System.out.println(artCnt +"\n" + maxSize);
	    
	}
}