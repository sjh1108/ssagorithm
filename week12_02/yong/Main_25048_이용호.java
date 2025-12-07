package algostudy.baek.week12_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * N개의 스위치 M개의 컴퓨터
 * 각 스위치에 포트개수 ai, 설치비용 bi
 * 남는 포트 없도록 연결
 * 여러 연결 방식중 최소
 * 스위치 사이클 x
 * 
 * ------------------------
 * 컴퓨터에 연결할수 있는 개수
 * 첫 스위치 : 인터넷 연결 포트 필요 1개
 * 두번째 스위치 부터: 이전 스위치에서 연결 포트 1개 필요 + 지금 스위치에서 연결포트 1개 필요
 * dp[i][w] : i번째 스위치까지 고려했을 때  w개의 컴퓨터 연결하는 최소 비용
 */
public class Main_25048_이용호 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] ai = new int[N];
		int[] bi = new int[N];
		
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			// i번째 포트 개수, 설치 비용
			ai[i] = Integer.parseInt(st.nextToken()); 
			bi[i] = Integer.parseInt(st.nextToken());
			
		}
		int M = Integer.parseInt(br.readLine());
		// 컴퓨터 1대면, 이미 있는 랜선으로 충분 -> 비용 0
		if(M == 1) {
			System.out.println(0);
			return;
		}
		long INF = Long.MAX_VALUE / 4;
		long[][] dp = new long[N+1][M+1];
		for(int i = 0; i <= N; i++) {
			for(int j = 0; j <= M; j++) {
				dp[i][j] = INF;
			}
			 dp[i][0] = 0;
		}

       for(int s = 1; s <= N; s++) {
    	   // 현재 스위치 포트 수, 설치 비용
    	   int port = ai[s-1];
    	   long cost = bi[s-1];
    	   
    	   int rootw = port - 1; // ai - 1
    	   int subw = port - 2; // ai - 2
    	   for(int w = 1;w <= M; w++) {
    		   // 현재 스위치를 루트 스위치로 사용한다면
    		   if(w == rootw) {
    			   dp[s][w] = Math.min(dp[s-1][w], dp[s-1][w - rootw] + cost);
    		   }
    		   // 현재 스위치를 서브 스위치로 사용한다면
    		   else if(subw > 0 && w > subw) { // 포트 꽂을곳 있어야하고,dp[s-1][w-subw]이 있어야함
    			   dp[s][w] = Math.min(dp[s-1][w],dp[s-1][w - subw] + cost);
    		   }
    		   // 사용 안하는 경우
    		   else {
    			   dp[s][w] = dp[s - 1][w];
    		   }
    	   }
    	   
       }
       long ans = dp[N][M];
       System.out.println(ans == INF ? -1 : ans);
    }
	
}
