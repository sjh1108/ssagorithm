package study;

import java.io.*;
import java.util.*;

public class Main {
   /*
    * 일단 현재 진 부분 문자열을 만든다
    * 
    * 진 부분 문자열을 만들때
    * 
    * 1개로 이루어진 부분 문자열 2개 3개... 로 각각 구한다
    * 어찌 되었건 1개로 이루어진 부분 문자열이 가장 작다
    * 
    * 조건 문자열의 시작이 0 이라면 건너 뛰고 다시 구할 것
    * 
    * 재귀에 가깝게 풀이한다
    * dp[현재수] 를 넣어주고 만약 함수 시작시 첫 시작하는 부분이라면
    * 이루어진 부분 문자열을 넣어준다
    * 
    * 만약 다른 dp[현재수] =  0이 아닌 다른 값이 존재한다면 이미 이전에 더 작은 값이 이미 실현하다가
    * 실패한 경우 이므로 재귀 x
    * 
    * 그게 아니라면 현재수가 한자리 수라면 더이상 재귀 x
    * 
    * 만약 모든 현재수를 찾았다면 더이상 재귀 x
    * 
    * 재귀에 성공하게 되는 경우는?
    * 상대방에게 넘기는 수가 10미만 일때 dp 값 리턴 
    * 여기서 dp 값은 첫턴에 이기기 위해 골라야 하는 수
    * 
    */
	
   
	static int[] dp;
	static int num;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        //StringTokenizer st = new StringTokenizer(br.readLine());
        
        dp = new int[1000000];
        
        //dp[2309] = 2309;
        num = 239;
        
        int ans = Partial(num,num);
        System.out.println(ans);
        
        bw.flush();
        bw.close();
        br.close();
    }
    
    static int Partial(int num, int parent)
    {
    	int depth = 1;
    	
    	if(dp[num] != 0) return-1;
    	dp[num] = parent;
    	
    	while(depth <= num)
    	{
    		depth *= 10;
    		int temp = num;
    		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
    		
    		while(temp  != 0)
    		{
    			int partial = temp%depth;
    			temp /= 10;
    			
    			if(depth/10 <= partial && partial != num) {
    				System.out.println(num + " : " + partial);
    				pq.add(partial);
    			}
    		}
    		
    		while(!pq.isEmpty())
    		{
    			int partial = pq.poll();
    			
    			if(10 > num - partial) return partial;
    			
    			int num_new = num-partial;
    			System.out.println("num_new : " + num_new);
    			if(parent == num) parent = partial;
    			int ans = Partial(num_new, parent);
    			if(-1 != ans) return ans;
    		}
    		
    	}
    	//Partial(num);
    	
    	return -1;
    }
    
}

틀린이유