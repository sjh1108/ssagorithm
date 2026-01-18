package ssagorithm.bk.s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제이해
 * - N 개의 정수로 이루어진 수열
 * - 크기가 양수인 부분 수열 중 
 * - 수열의 원소를 다 더한 값이 S가 되는 경우의 수 구하기
 * 
 * 자료형 
 * - N 20
 * - S -100만 ~ 100만 
 * - N개 정수 
 * - 다 int로 가능
 * 
 * 전략
 * - N이 20인데 부분집합해도되나? 
 * - 2^20 = 1048576인디... 
 * - 시간제한 2초니까 일단 가보자 
 * 
 */
public class Main_1182_부분수열의합 {

	
	static int N, S;
	static int[] list;
	static boolean[] visited;
	static int cnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		list = new int[N];
		visited = new boolean[N];

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			list[i] = Integer.parseInt(st.nextToken());
 		}//입력끝
		
		// 부분집합 
		subset(0);
		
		// 출력
		if(S == 0) {
			sb.append(cnt-1); // 오답노트 : 아무것도 선택 안해서 합이 0이되는 공집합을 빼야함 
		}else {
			sb.append(cnt); 
		}
		
		System.out.println(sb.toString());
	}
	
	private static void subset(int depth) {
		if(depth == N) {
			// N개 숫자 다 부분집합 만들었으면 더해 
			int sum = 0;
			for(int i = 0; i < N; i++) {
				if(visited[i]) sum += list[i];
			}
			
//			System.out.println(sum);
			if(sum == S) ++cnt; // 더해서 S랑 같으면 cnt+1
			return;
		}
		
		visited[depth] = false;
		subset(depth+1);
		visited[depth] = true;
		subset(depth+1);
		
		
		
	}
}
