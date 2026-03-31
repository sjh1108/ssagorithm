package study;

import java.io.*;
import java.util.StringTokenizer;

public class Main_1352_김회인 {
    static int N;
    
    static int[] alpha = new int[26];
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(br.readLine());
        
        back(0, 0, 0);
        
        System.out.println("-1");
        
    }
    
    static void back(int rank, int sum, int count)
    {
       if(N == sum)
       {
          
          print(sum);
          System.exit(0);
          return;
       }
       
       for(int i = sum+1; i> rank; i--)   // 역으로 돌아서 사전에서 최선의 경우를 찾으려 함
       {
          if(sum + rank > N) continue;
          alpha[count] = i;
          back(i,sum + i,count+1);
          alpha[count] = 0;
          
       }
    }
    
    static void print(int sum)
    {
    	StringBuilder sb = new StringBuilder();
    	char[] c =new char[sum+1];
    	
    	
    	for(int i=0; i<26;i++)
    	{
    		if(alpha[i] == 0) continue;
    		c[alpha[i]] = (char)(i + 'A');
    		alpha[i]--;
    	}
    	
    	int count =1;		// 1부터 초기 값 설정 해두어서 1 부터 시작
    	
    	for(int i=0; i<26;i++)
    	{
    		
    		if(alpha[i] == 0) continue;
    		
    		for(int j=0; j<alpha[i];)
    		{
    			if(c[count] != 0) {
    				count++;
    				continue; // 초기 값이 아니라면? 넘겨줌
    			}
    			j++; // 값 들어가면 카운트
    			
    			c[count] = (char)(i + 'A');
    			count++;
    			
    		}
    	}
    	
    	
    	for(int i=1; i<c.length; i++)
    	{
    		sb.append(c[i]);
    	}
    	
    	System.out.println(sb);
    }

   
}