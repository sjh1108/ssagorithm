package study;
import java.io.*;
import java.util.*;

class Main_9215_김회인 {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        //StringTokenizer st = new StringTokenizer(br.readLine());
      
        String s = br.readLine();
        String s2 = br.readLine();
        int sl = s.length()+1;
        int s2l = s2.length()+1;
        
        int dp[][] = new int[sl][s2l];
        
        for(int i=1; i<sl; i++)
        {
        	for(int j=1; j<s2l; j++)
        	{
        		if(s.charAt(i-1) == s2.charAt(j-1))
        		{
        			dp[i][j] = dp[i-1][j-1] + 1;
        		}
        		else
        		{
        			dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
        		}
        	}
        }
        
//        for(int i=0; i<sl; i++)
//        {
//        	for(int j=0 ;j<s2l; j++)
//        	{
//        		System.out.print(dp[i][j] + " ");
//        	}
//        	System.out.println("");
//        }

        bw.write(Integer.toString(dp[sl-1][s2l-1]));
        bw.flush();
        bw.close();
        br.close();

    }
    
}