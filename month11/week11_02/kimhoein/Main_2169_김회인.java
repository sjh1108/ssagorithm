package study;
import java.io.*;
import java.util.*;

public class Main_2169_김회인 {
	
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	int N = Integer.parseInt(st.nextToken());
    	int M = Integer.parseInt(st.nextToken());
    	
    	int map[][] = new int[N][M];
    	int dp[][] = new int[N][M];
    	
    	int temp[] = new int[M];
    	int temp2[] = new int[M];
    	
    	for(int i=0; i<N; i++)
    	{
    		st = new StringTokenizer(br.readLine());
    		
    		for(int j=0; j<M; j++)
    		{
    			map[i][j] = Integer.parseInt(st.nextToken());
    		}
    		
    	}
    	
    	dp[0][0] = map[0][0];
    	
    	for(int i=1; i<M; i++)
    	{
    		dp[0][i] = dp[0][i-1] + map[0][i];
    	}
    	
    	for(int i=1; i<N; i++)
    	{
    		temp[0] = dp[i-1][0] + map[i][0];
    		
    		for(int j=1; j<M; j++)
    		{
    			temp[j] = map[i][j] + Math.max(temp[j-1], dp[i-1][j]);
    		}
    		
    		temp2[M-1] = dp[i-1][M-1] + map[i][M-1];
    		//System.out.println("temp2 : " + temp2[M-1]);
    		for(int j=M-2; j>=0; j--)
    		{
    			temp2[j] = map[i][j] + Math.max(temp2[j+1], dp[i-1][j]);
    		}
    		
    		for(int j=0; j<M; j++)
    		{
    			dp[i][j] = Math.max(temp[j], temp2[j]);
    			
    		}
    	}
    	
//    	for(int i=0; i<N; i++)
//    	{
//    		
//    		for(int j=0; j<M; j++)
//    		{
//    			System.out.print(dp[i][j] + " ");
//    		}
//    		System.out.println("");
//    		
//    	}
    	
    	
    	bw.write(Integer.toString(dp[N-1][M-1]));
    	bw.flush();
    	bw.close();
    	br.close();
    }
    

}
