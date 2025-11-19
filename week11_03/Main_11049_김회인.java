package study;
import java.io.*;
import java.util.*;

public class Main_11049_김회인 {

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(br.readLine());
        int metrix[][] = new int[n][2];
        int r[] = new int[n+1];
        
        for(int i=0; i<n; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	metrix[i][0] = Integer.parseInt(st.nextToken());
        	metrix[i][1] = Integer.parseInt(st.nextToken());
        }
       
        r[0] = metrix[0][0];
        
        for(int i=0; i<n; i++)
        {
        	r[i+1] = metrix[i][1];
        }
        
        long[][] dp = new long[n][n];
        
        for(int len = 2; len <= n; len ++)
        {
        	for(int i=0; i<=n-len; i++)
        	{
        		int k = len+i-1;
        		dp[i][k] = Long.MAX_VALUE;
        		for(int j=i; j < k ; j++ )
        		{
        			long cost = dp[i][j] + dp[j+1][k] + (long)r[i] * r[j+1] * r[k+1];
        			
        			if(cost < dp[i][k]) dp[i][k] = cost;
        		}
        	}
        }
        
        bw.write(Long.toString(dp[0][n-1]));
        bw.flush();
        bw.close();
        br.close();
	}

}
