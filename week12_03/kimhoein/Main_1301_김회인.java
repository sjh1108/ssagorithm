package study;
import java.io.*;
import java.util.*;

public class Main_1301_김회인 {
   
	static int N;
	static long[][][][][][][] dp;
	
	static final long NOT_CALCULATED = -1L;
    
	/*
	 * 7차원 배열 선언해서
	 * 이전의 값과 겹치지 않는 두수를 뽑아준다
	 * 두수를 뽑아준 만큼
	 * 기존 dp에 저장된 숫자값을 빼준다.
	 * 재귀를 지속하며
	 * dp에 계산한 값을 미리 넣어주어서
	 * 계산에 대한 값을 미리 만들어주어서 계산을 효율적으로 하는게 핵심
	 * 생각해내는게 어려워서 그렇지
	 * 생각보다 구조 자체는 복잡한 편은 아니다.
	 */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(br.readLine());
        
        int[] counts = new int[N+1];
        
        for(int i=1; i<=N; i++) {
        	counts[i] = Integer.parseInt(br.readLine());
        }
        
        dp = new long[11][11][11][11][11][6][6];
        
        // 진정한 아도겐
        for (int i1 = 0; i1 <= 10; i1++) {
            for (int i2 = 0; i2 <= 10; i2++) {
                for (int i3 = 0; i3 <= 10; i3++) {
                    for (int i4 = 0; i4 <= 10; i4++) {
                        for (int i5 = 0; i5 <= 10; i5++) {
                            for (int i6 = 0; i6 <= 5; i6++) {
                                for (int i7 = 0; i7 <= 5; i7++) {
                                    dp[i1][i2][i3][i4][i5][i6][i7] = NOT_CALCULATED;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        long total = solve(counts, 0, 0);
        
        bw.write(Long.toString(total));
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static long solve(int[] currentCounts, int secondLast, int last) {
    	int totalRemaining = 0;
    	
    	for(int i=1; i<= N; i++) {
    		totalRemaining += currentCounts[i];
    	}
    	
    	// 목걸이 완성 상태
    	if(totalRemaining == 0) return 1;
    	
    	// N 개수 따라서 c 개수 조절
    	int c1 = (N >= 1) ? currentCounts[1] : 0;
        int c2 = (N >= 2) ? currentCounts[2] : 0;
        int c3 = (N >= 3) ? currentCounts[3] : 0;
        int c4 = (N >= 4) ? currentCounts[4] : 0;
        int c5 = (N >= 5) ? currentCounts[5] : 0;
    	
        // dp 에 값 넣어주고 이미 한 계산이라면 리턴
        if(dp[c1][c2][c3][c4][c5][secondLast][last] != NOT_CALCULATED) return dp[c1][c2][c3][c4][c5][secondLast][last];
        
        long result=0;
        
        for(int next = 1; next <= N; next++) {
        	if(currentCounts[next] >0 && next != last && next != secondLast) {
        		currentCounts[next]--;
        		
        		result += solve(currentCounts, last, next);
        		
        		currentCounts[next]++;
        	}
        }
    	
        dp[c1][c2][c3][c4][c5][secondLast][last] = result;
        return result;
    }
}
