package week0911;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14888 {
	
	static int N;
	static int[] A;
	static int[] cal;
	static int max = Integer.MIN_VALUE;
	static int min = Integer.MAX_VALUE;
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 첫째 줄에 수의 개수 N
		N = Integer.parseInt(br.readLine());
		
		//둘째 줄에는 A1, A2, ..., AN이 주어진다.
		StringTokenizer st = new StringTokenizer(br.readLine());
		A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		
		// +의 갯수[0], -의 갯수[1], x의 갯수[2] /의 갯수[3] 가 주어짐.
		cal = new int[4]; 
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 4; i++) {
			cal[i] = Integer.parseInt(st.nextToken());
		}
		
		dfs(1, A[0]); // 시작: 첫 번째 숫자에서 출발
		System.out.println(max);
		System.out.println(min);

	}
	
	
	// 연산자 선택 트리를 모두 탐색
	// 깊이: N, 각 깊이마다 선택할 수 있는 연산자는 4개
	// 모든 루트를 확인
	// 루트까지 내려가면 연산결과 완성.. max와 min 갱신 가능.
	private static void dfs(int depth, int sum) {
		
	}

}
