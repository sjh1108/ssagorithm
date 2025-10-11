package baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main_11650_이용호 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		int[][] arr = new int[N][2];
		
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken()); // x
			arr[i][1] = Integer.parseInt(st.nextToken()); // y
		}
		
		// x오름차순 정렬, x 같으면 y 오름차순 정렬(람다식 버전)
		Arrays.sort(arr,(a, b) -> {
			if (a[0] == b[0]) { //x 오름차순
				return a[1] - b[1];
			}
			else { //y 오름차순
				return a[0] - b[0];
			}
		});
		
		// 익명 클래스 버전
		Arrays.sort(arr, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[0] == o2[0]) {
					return o1[1] - o2[1];
				}
				else {
					return o1[0] - o2[0];
				}
			}
		});
		
		for(int i = 0; i < N; i++) {
			System.out.print(arr[i][0]+ " ");
			System.out.println(arr[i][1]);
		}
		
	}

}
