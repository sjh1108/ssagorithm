package month08.week04.kjs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2738_행렬덧셈 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
//		System.out.println("N : " + N);
		int M = Integer.parseInt(st.nextToken());
//		System.out.println("M : " + M);
		
		int[][] map = new int[N][M];		
		int[][] map2 = new int[N][M];
		int[][] result = new int[N][M];
		
		for(int i=0; i<N; i++) {
			StringTokenizer st1 = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st1.nextToken());

			}
		}
		for(int i=0; i<N; i++) {
			StringTokenizer st2 = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map2[i][j] = Integer.parseInt(st2.nextToken());
			}
		}
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				result[i][j] = map[i][j]+map2[i][j];
			}
		}
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				System.out.print(result[i][j]+ " ");
			}
			System.out.println();
		}
		
		
		
		
	}

}
