package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.*;

public class Main_7579_김회인 {
	/*
	 * 메모리 기준이 아니라 100 * 100으로 앱 비활성화 비용으로 dp를 계산한다
	 * 처음 초기 값 입력을 제외하고
	 * cost 이상인 값만 업데이트 가능하고 dp[i][j] = Math.max(dp[i-1][j-cost[i]] + memory[i],dp[i-1][j]);
	 * 이용해서 값을 구한다. 
	 */
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	int N = Integer.parseInt(st.nextToken());
    	int M = Integer.parseInt(st.nextToken());
    	int[][] dp = new int[N][10001];
    	int memory[] = new int[N];
    	int cost[] = new int[N];
    	
    	StringTokenizer s1 = new StringTokenizer(br.readLine());
    	StringTokenizer s2 = new StringTokenizer(br.readLine());
    	
    	for(int i=0; i<N; i++) {
    		memory[i] = Integer.parseInt(s1.nextToken());
    		cost[i] = Integer.parseInt(s2.nextToken());
    	}
    	int ans = Integer.MAX_VALUE;
    	
    	for(int i=0; i<N; i++) {
    		for(int j=0; j<=10000; j++) {
    			
    			//if(j<cost[i]) continue;
    			
    			if(i ==0) {
    				if(j >= cost[i])dp[i][j] = memory[i];
    			}
    			else
    			{
    				if(j >= cost[i])dp[i][j] = Math.max(dp[i-1][j-cost[i]] + memory[i],dp[i-1][j]);
    				else dp[i][j] = dp[i - 1][j];
    			}
    			if(dp[i][j] >= M) ans = Math.min(ans, j);
    		}
    		
    	}
    	
    	bw.write(Integer.toString(ans));
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
}
