package week12_03.Minsang.BOJ_10815;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class BOJ_10815 {
	static int N, K, M;
	// 정말 껌이다

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		// 값 하나만 비교해도 되니까 set으로 사용함
		HashSet<Integer> card = new HashSet<>();
				
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
//			card[i] = Integer.parseInt(st.nextToken());
			card.add(Integer.parseInt(st.nextToken()));
		}
		
	
		M = Integer.parseInt(br.readLine());
	
//		int[] arr = new int[M+1];
		
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= M; i++) {
			int tmp = Integer.parseInt(st.nextToken());
			
			// 숫자 비교
			if(card.contains(tmp)) {
				sb.append("1 ");
			} else {
				sb.append("0 ");
			}
//			arr[i] = Integer.parseInt(st.nextToken());
			
		}
		
		System.out.println(sb);
		
	}

}
