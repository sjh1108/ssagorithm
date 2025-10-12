package bk.silver; // S3

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/** 
 * 문제 이해
 * - 입력 : 자연수 N M
 * - 1부터 N까지 자연수 중에서 M개를 고른
 * - 중복 순열 오름차순 출력 (2 1 같은 게 안 된다는 뜻) 
 * 
 * 전략
 * - 출력이 많을 수도 있으니 StringBuilder 
 * 
 * 후기
 * - 비내림차순이 왜 조건에 있는지 이해를 못해서 몇번 틀림
 * - 이해해도 어떻게 해결해야하는지 헤맴 
 * - 오랜만에 푼 문제라 중복순열에 곤란해하는 나를 발견
 * - 비상비상
 *
 */
public class Main_15652_N과M4 {
	
	static int N, M;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		//입력끝
		
		permutation(0, 1, new int[M]);
		
		System.out.println(sb.toString());
		
	}

	// 출력을 오름차순에 한정지으려면 조합마냥 start를 넣어줘야한다. 
	private static void permutation(int depth, int start, int[] nums) {
		if(depth == M) {
			for(int n : nums) {
				sb.append(n);
				sb.append(" ");
			}
			sb.append("\n");
			return;
		}
		
		for(int i = start; i <= N; i++) {
			
			nums[depth] = i;
			permutation(depth+1, i, nums);
			
		}
		
	}
}
