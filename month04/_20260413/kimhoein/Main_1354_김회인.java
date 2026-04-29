package study;
import java.io.*;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;


public class Main_1354_김회인 {
    
	/*
	 * N 번 까지
	 * Ai = A[i/p]-x + A[i/Q]-y
	 * i/p-x <= 0 1
	 * i/Q-y <= 0 1
	 * 
	 *  굳이 배열에 넣어서 터뜨리지 말것
	 *  근데 그게 아니라면 hashmap에서 꺼내서 값 넣어줌
	 *  
	 *  hashmap
	 *  
	 *  구해야하는 값 부터 시작
	 *  top down 방식으로
	 *  모든 값을 구하는게 아니라
	 *  필요한 값만 구하고
	 *  만약 그 필요한 값이 hashmap에 존재하지 않는다면
	 *  재귀로 내려가서 그 값을 구한다
	 *  
		
	 */
	
	static long N;
	static long P;
	static long Q;
	static long X;
	static long Y;
    static HashMap<Long, Long> hash;
	public static void main(String[] args) throws InterruptedException, IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st  = new StringTokenizer(br.readLine());
       
       N = Long.parseLong(st.nextToken());
       P = Long.parseLong(st.nextToken());
       Q = Long.parseLong(st.nextToken());
       X = Long.parseLong(st.nextToken());
       Y = Long.parseLong(st.nextToken());
       
       hash = new HashMap<>();
       hash.put(0L, 1L);
       hash.put(1L, 2L);
       
       bw.write(Long.toString(dp(N)));
       bw.flush();
       bw.close();

    }
    
    static long dp(long num)
    {
    	if(num <= 0) return 1;
    	
    	if(hash.containsKey(num)) return hash.get(num);
    	
    	long result = dp(num / P - X) + dp(num / Q - Y);
    	
    	hash.put(num, result);
  	    return result;
    }
}
