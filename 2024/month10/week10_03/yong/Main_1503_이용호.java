package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1503_이용호 {
	/*
	 * 집합 S에 속하지 않는 x, y, z
	 *| N - xyz| 최솟값
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		boolean[] ban = new boolean[1002]; // 1-based
		//집합에 들어가는 숫자 금지
		if(M > 0) {
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < M; i++) {
				ban[Integer.parseInt(st.nextToken())] = true;
			}
		}
		int result = Integer.MAX_VALUE;
		for(int x = 1;  x <= 1000; x++) {
			if(ban[x]) continue; // 금지된 숫자 패스
			for(int y = 1; y <= 1000; y++) {
				if(ban[y]) continue;
				for(int z = 1; z <= 1001; z++) {
					if(ban[z]) continue;
					int diff = Math.abs(N - (x * y * z));
					result = Math.min(result, diff);
				}
			}
		}
		System.out.println(result);

	}

}
