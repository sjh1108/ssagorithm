package algostudy.baek;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_18111_이용호 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		
		int[][] map = new int[N][M];
		int maxH = 0; 
		int minH = 256; // 땅높이 256보다 작으니까 
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				maxH = Math.max(maxH, map[i][j]);
				minH = Math.min(minH, map[i][j]);
			}
		}
		
		int minTime = Integer.MAX_VALUE;
		int resH = 0;
		//블럭 높이 완탐으로 H높이로 평탄화 하면서 시간 비교
		for(int H = minH; H <= maxH; H++) {
			int block = B;
			int time = 0;
			
			//H높이로 평탄화 하는 시간 계산
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < M; c++) {
					//블럭을 제거해야 하면 시간 증가하고 블럭 추가
					if(map[r][c] > H) {
						//시간 증가, 블럭 추가
						time += (map[r][c] - H) * 2;
						block += map[r][c] - H;
					}
					//블럭을 채워야 하면
					else if(map[r][c] < H) {
						//시간 증가, 블럭 감소
						time += H - map[r][c];
						block -= H - map[r][c];
					}
				}
			}
			//블럭 부족하면 패스
			if(block < 0) continue;
			if(minTime >= time && H >= resH) {
				resH = H;
			}
			minTime = Math.min(minTime,time);
			
		}
		System.out.println(minTime+ " " + resH);
		
		
		
		
	}

}
