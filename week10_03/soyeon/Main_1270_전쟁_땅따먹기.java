package bk.silver; //2

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 병사수가 최대 200x10만 = 2천만
 * - 병사 번호가 2^31 (long 주의)
 *
 *
 */
public class Main_1270_전쟁_땅따먹기 {

	static int N, T;
	static HashMap<Long, Integer> map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < N; i++) {
			map = new HashMap<>();  // 오답노트 : 선언을 안함; 
			st = new StringTokenizer(br.readLine(), " ");
			T = Integer.parseInt(st.nextToken());
			for(int t = 0; t < T; t++) {
				long n = Long.parseLong(st.nextToken()); // long 주의!!!!!!! 
				// key: n에 value가 있다면 +1 없다면 null이니까 기본값 0에 +1
				map.put(n, map.getOrDefault(n, 0) + 1); 
			}
			 
			long maxKey = 0; // 아무값이나 넣음.. 
			int maxValue = 0;
			for(long key : map.keySet()) { // 모든 키 순회 
				int value = map.get(key);
				if(value > maxValue) {
					maxValue = value;
					maxKey = key;
				}
			}
			
			if(maxValue > T/2) {
				sb.append(maxKey);
			} else {
				sb.append("SYJKGW");
			}
			sb.append("\n");
			// 과반수 로직 틀림 
//			sb.append(maxKey == 0 ? "SYJKGW" : maxKey);
		}
		
		System.out.println(sb.toString());

	}
}
