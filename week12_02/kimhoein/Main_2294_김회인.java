package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.*;

public class Main_2294_김회인 {
	/*
	 * 선정 문제 어려워서 지피티한테 학습용으로 추천 받음
	 * 동전 1번 상위 문제??
	 * 여러개의 동전을 받아서 그 동전으로 K 만큼의값어치를하는 가장 적은 수의 동전을 쓰는 문제
	 * 동전은 겹쳐도 된다
	 * 
	 * 1. 같은 값의 동전이 여러개 입력 될 수 있다 -> hash로 중복 제거
	 * 1_2. dp[동전 가치] 설정 해주면서 동시에 hash로 넣어줘서 나중에 따로 돌려주기
	 * 2. K는 10000 까지 dp[K] 까지
	 * 3. K보다 더 큰 가치의 동전이 있을 수 있다 dp[K]터지고 시간 복잡도만 사용하므로 에초에 dp에 넣지 않는다.
	 * 4. dp[i] = Math.min(dp[i], dp[n] + dp[i-n]); 으로 점화식 설정
	 */
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	int N = Integer.parseInt(st.nextToken());
    	int K = Integer.parseInt(st.nextToken());
    	
    	int[] dp = new int[K+1];
    	HashSet<Integer> dp_hash = new HashSet<>();	// 중복방치
    	
    	Arrays.fill(dp, 10001);	// 10001 이상은 나올 수 없으므로 최대 값 설정 사실 K+1 해줘도 문제 없다
    	
    	for(int i=0; i<N; i++) {		// dp 입력
    		int n = Integer.parseInt(br.readLine());
    		if(n > K) continue;			// K 보다 큰 동전 가치는 필요 없으니 버린다.
    		dp_hash.add(n);				// 중복 방치
    		dp[n] = 1;					// dp 값 설정
    	}
    	
    	List<Integer> coinList = new ArrayList<>(dp_hash);
    	
    	for(int i : coinList) {		// 하나의 dp[i] 에
    		for(int n = i; n<=K ; n++) {		// hash를 돌린다.
    			//if(i-n < 1) continue;
    			dp[n] = Math.min(dp[n], dp[n-i] + 1);
    		}
    	}
    	
    	if(10001 == dp[K]) dp[K] = -1;
    	
    	bw.write(Integer.toString(dp[K]));
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
}
