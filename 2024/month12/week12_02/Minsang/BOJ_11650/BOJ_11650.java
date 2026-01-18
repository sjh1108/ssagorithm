package week12_02.Minsang.BOJ_11650;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_11650 {
	static int N;
	static ArrayList<int[]> list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		list = new ArrayList<>();
		
//		for(int i = 0; i < N; i++) {
//			list.add(new int[] {0,0});
//		}
		
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());

			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			// list에 추가
			list.add(new int[] {x, y});
		}
		
		Collections.sort(list, (a,b) -> {
			if(a[0] == b[0]) { // x 좌표가 같을 때
				return a[1] - b[1]; // y 좌표로 비교하여 정렬
			}
			return a[0] - b[0]; // 그게 아니라면 x 좌표
		});
			
		// 배열 정렬이니까 foreach문으로
		for(int[] n : list) {
			sb.append(n[0]).append(" ").append(n[1]).append("\n");
		}
		
		System.out.println(sb);
	}

}