package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_15652 {
	
	static int N, M;
    static int[] arr;
    static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[M];

		dfs(0,1);
		System.out.print(sb);
		
		
	}

	private static void dfs(int depth, int start) {
		
		if(depth == M) { // 걸려서 출력하는 거 만들면 됨. arr에서 가져와서.
			for (int num : arr) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}
		
		// 어차피 그냥 하면 오름차순되고, 중복허용 안하기 위해 i=start부터 시작하고, 계속 올라감. 리턴되면 다시 그 전껄로 가고.
		for (int i = start; i <= N; i++) {
			arr[depth] = i;
			dfs(depth + 1, i);
		}
	}
}
