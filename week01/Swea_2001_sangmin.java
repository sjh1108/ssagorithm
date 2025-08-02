package java_home_work.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Swea_2001_sangmin {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int T = Integer.parseInt(br.readLine()); // 테스트 케이스 개수

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int N = Integer.parseInt(st.nextToken()); // 배열 크기
            int M = Integer.parseInt(st.nextToken()); // 파리채 크기
            
            int[][] map = new int[N][N];
            
            // 배열 입력 받기
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int maxKill = 0;

            // 파리채로 MxM 크기 영역 탐색
            for (int i = 0; i <= N - M; i++) {
                for (int j = 0; j <= N - M; j++) {
                    int sum = 0;
                    for (int x = 0; x < M; x++) {
                        for (int y = 0; y < M; y++) {
                            sum += map[i + x][j + y];
                        }
                    }
                    maxKill = Math.max(maxKill, sum);
                }
            }

            System.out.println("#" + t + " " + maxKill);
        }
	}
}
