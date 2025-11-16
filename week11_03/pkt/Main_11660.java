package week_11_02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


// 구간 합 구하기 5
public class Main_11660 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        long[][] prefix = new long[N + 1][N + 1]; // 합은 long이 안전. 
        
        for (int i = 1; i <= N; i++) {
        	st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				// 값보다 누적합이 중요함. 
				int value = Integer.parseInt(st.nextToken());
				prefix[i][j] = prefix[i-1][j] // p[2][2]라고 가정하면 p[1][2]까지 누적
								+ prefix[i][j-1] // p[2][1]까지 누적
										-prefix[i-1][j-1] // 겹치는 p[1][1] 하나 빼고(실수: 수식 + -> -)
												+ value;	// p[2][2]부분 마지막 조각 하나 더하기. 
				// prefix에 각 구간 합을 만들어주고							
			}
		}
        
        for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken()); 
            
            long sum = prefix[x2][y2]- prefix[x1 - 1][y2]- prefix[x2][y1 - 1] + prefix[x1 - 1][y1 - 1];
            sb.append(sum).append('\n');
        }
        System.out.println(sb);
    }
}
