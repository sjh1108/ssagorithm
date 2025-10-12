package algorithm.d1012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_15649_N과M1 {

		static int n, m;
		static int[] arr;
		static int[] output;
		static boolean[] visited;
		static StringBuilder sb = new StringBuilder();

		public static void main(String[] args) throws IOException {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			
			arr = new int[n+1];
			for(int i=1; i<=n; i++) {
				arr[i] = i;
			}
			visited = new boolean[n+1];
			output = new int[m];
			permutation(0,n,m);
			System.out.println(sb);
			
		}
		
		static void permutation(int depth, int n, int r) {
			if(depth == r) {
				for(int i = 0; i<depth; i++) {
					sb.append(output[i]).append(" ");
				}
				sb.append("\n");
				return;
			}
			for(int i=1; i<=n; i++) {
				if(!visited[i]) {
					visited[i] = true;
					output[depth] = arr[i];
					permutation(depth +1, n, r);
					visited[i] = false;
				}
			}
			
		}

}
