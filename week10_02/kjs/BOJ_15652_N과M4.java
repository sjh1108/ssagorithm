package algorithm.d1012;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15652_N과M4 {

	 static int N, M;
	    static int[] pick;      
	    static StringBuilder sb = new StringBuilder();

	    public static void main(String[] args) throws Exception {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        StringTokenizer st = new StringTokenizer(br.readLine());

	        N = Integer.parseInt(st.nextToken());
	        M = Integer.parseInt(st.nextToken());
	        pick = new int[M];

	        dfs(0, 1);     

	        System.out.print(sb);
	    }


	    static void dfs(int depth, int start) {
	        if (depth == M) {
	            for (int i = 0; i < M; i++) {
	                sb.append(pick[i]).append(' ');
	            }
	            sb.append('\n');
	            return;
	        }

	        for (int num = start; num <= N; num++) {
	            pick[depth] = num;     
	            dfs(depth + 1, num);    
	        }
	    }

}
