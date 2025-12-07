package study;


import java.io.*;
import java.nio.Buffer;
import java.util.StringTokenizer;

public class Main_25048_김회인 {

	static final long IMPOSSIBLE = Long.MAX_VALUE >> 2;
	
	/*
	 *  ex 1, 2, 3의 개수를 가진 스위치의 개수를 구한다...
	 *  1 - 2 - 3
	 *    - 3 - 2  
	 *    2^ 3이다 부분 집합으로 푼다면
	 *    2 ^ n 따라서 부분집합으로 풀기는 불가능
	 *    
	 *    M = 비용의 합
	 *    그래서 dp[M] 으로 한줄로 미리 만들어서
	 *    비용별 dp를 만들고
	 *    하나의 스위치의 개수가n 이라면 for문을 돌때 dp[0] 이용 해서 dp[n] = n 값 넣어주고
	 *    전에 돌았던 부분을 계쏙 돌아줌
	 *    
	 *    1,2,3 이 예시라면
	 *    dp[0 + 1] = 1
	 *    
	 *    2 일때
	 *    dp[0 + 2] = 2
	 *    dp[1 + 2] = 3 
	 *    
	 *    3일때
	 *    dp[0 + 3]
	 *    dp[1 + 3]
	 *    dp[2 + 3]
	 *    
	 *    다만 한줄로 할 경우에는 덮어 씌워질 위험이 있어서
	 *    포트 개수 -> M-1 이 아니라
	 *    M-1 -> 포트 개수로 이동 해야함
	 *     
	 */
    public static void main(String[] args) throws Exception {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
       
    	int N = Integer.parseInt(br.readLine());
    	
    	int[] port = new int[N+1];	// 각 장비 별 포트
    	long[] cost = new long[N+1]; // 비용
    	
    	for(int i=1; i<=N; i++) {
    		st= new StringTokenizer(br.readLine());
    		port[i] = Integer.parseInt(st.nextToken()) -2; // 인터넷 선과 연결해야할 포트 하나 아래 선과 연결해야할 포트 하나
    		cost[i] = Integer.parseInt(st.nextToken());
    	}
    	
    	int M = Integer.parseInt(br.readLine());
    	
    	long[] dp = new long[M];
    	
    	for(int i=0; i<M; i++)
    	{
    		dp[i] = IMPOSSIBLE;
    	}
    	
    	dp[0] = 0;
    	
    	
    	for(int i=1; i<=N; i++) {
    		for(int j = M-1; j>=port[i]; j--) {	// dp M은 포트 개수 기준이고 port[i] 보다 더 작을 수는없다 포트를 모두 채워야 하므로
    			if(dp[j-port[i]] != IMPOSSIBLE) {	// 존재하는 지점을 찾아서 비교 해준다.
    				dp[j] = Math.min(dp[j], dp[j-port[i]] + cost[i]);
    			}
    		}
    	}
    	
    	System.out.println(dp[M-1] == IMPOSSIBLE ? -1 : dp[M-1]);		// 만약 IMPOSSIBLE 이면 만들기 불가능이므로 -1 출력
    }
}
