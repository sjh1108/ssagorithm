package week_11_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_5643_키순서 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			
			int N = Integer.parseInt(br.readLine()); // 학생수
			int M = Integer.parseInt(br.readLine()); // 갯수
			
			//  불린
			boolean[][] graph = new boolean[N+1][N+1];
			
			// 그래프에 값 넣기
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				graph[a][b] = true;
			}
			
			// 플로이드 위셜
			for (int k = 1; k <= N; k++) {
				for (int i = 1; i <= N; i++) {
					for (int j = 1; j <= N; j++) {
						graph[i][j] = graph[i][j] || (graph[i][k] && graph[k][j]);
					}
				}
			}
			
			// 자기자신 뺴고, ans++
			int ans = 0;
			label1 : for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					if(i == j) continue;
					if(!graph[i][j] && !graph[j][i]) continue label1;
				}
				ans++;
			}
			
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
			
			
		}
		System.out.println(sb);
	}
}

