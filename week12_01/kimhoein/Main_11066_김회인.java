package study;
import java.io.*;
import java.util.*;

/*
 * dp 문제 
 * dp[start][end] = Math.min(dp[start][end], dp[start][mid] + dp[mid+1][end] + Prefix[end]-Prefix[start-1]);
 * 임을 안다면 쉽게 풀리는 문제
 * 다만 저렇게 까지 접근해야하는 접근은 되었으나
 * 문제는 저렇게 식으로 도출화 하고
 * 도출화된 식을 코드로 만드는 부분 두가지를 모두 못했다.
 * dp는 어렵다...
 * 
 */
class Main_11066_김회인 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        int t = Integer.parseInt(br.readLine());
        for(int T=0 ; T<t ; T++)
        {
        	int n = Integer.parseInt(br.readLine());
        	int[][] dp = new int[n+1][n+1];
        	int[] Prefix  = new int[n+1];
        	
        	st = new StringTokenizer(br.readLine());
        	//System.out.println("11");
        	for(int i=1; i<=n; i++)
        	{
        		int num = Integer.parseInt(st.nextToken());;
        		
        		if(i==1) Prefix[i] =num;
        		else Prefix[i] =num + Prefix[i-1];
        	}
        	
        	for(int gap=1; gap<n; gap++)
        	{
        		for(int start =1 ; start+gap <= n; start++) {
        			int end = start + gap;
        			dp[start][end] = Integer.MAX_VALUE;
        			
        			for(int mid = start; mid<end; mid++) {	// 이부분을 복잡하게 생각하다고 오히려 꼬였다. mid 분할 한다는 생각을 하는 부분이 어려웠다.
        				dp[start][end] = Math.min(dp[start][end], dp[start][mid] + dp[mid+1][end] + Prefix[end]-Prefix[start-1]);
        			}
        		}
        	}
        	
        	System.out.println(dp[1][n]);
        	
        }
    }
    
}