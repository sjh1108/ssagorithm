package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_1270 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		StringBuilder sb = new StringBuilder();
		
		for (int tc = 1; tc <= T; tc++) {
		
			StringTokenizer st = new StringTokenizer(br.readLine());
			int Ti = Integer.parseInt(st.nextToken());
			
			// 해시맵을 설정함. -> 병사들의 번호, 카운트를 위함
			HashMap<Long, Integer> map = new HashMap<>(); // 병사수엄청나가능 - Long
			
			long maxKey = 0;
			int maxCount = 0;
			
			for (int i = 0; i < Ti; i++) {
				long num = Long.parseLong(st.nextToken());
				int cnt = map.getOrDefault(num, 0) + 1; // 키에 값넣은 것의 갯수를 카운팅 + 1
				map.put(num, cnt); // map에 키에 대응되는 값을 갱신
				
				// 일단,cnt 최고값 갱신
				if(cnt > maxCount) { 
					maxCount = cnt;
					maxKey = num;
				}
			}
			// 과반수 이상이면 그 병사 번호, 아니면 문자열 출력할 수 있게
			if(maxCount > Ti / 2) sb.append(maxKey).append("\n");
			else sb.append("SYJKGW\n"); // 오타실수 ㄷ
		}
		 System.out.print(sb);
	}
}
