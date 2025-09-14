package ssagorithm.bk.s;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - N개의 피연사자들 사이에 N-1개의 연산자를 넣어 
 * 		연산값중 min,max를 구하는 문제
 * 
 * 전략
 * - 순열
 * - 순열하기 편하게 리스트에 개별로 넣어주자 
 * 
 * 입력 
 * - N
 * - N-1개 피연산자 
 * - + - * / 순으로 연산자 개수 
 *
 * 후기
 * - 순열을 머리론 알겠는데 코드로 자면 왤캐 하나씩 빼먹는지 모르겠네.
 * 	 다 내공이 부족한 탓이겠지요. 일단 return 잊어버리지말기 
 * - 상당히 지저분하게 푼 것 같아서 다른 사람 코드를 봐야겠다 
 */
public class Main_14888_연산자끼워넣기 {

	static int N; // 피연산자 개수 
	static int[] num; // 피연산자  
	static List<Integer> oper; // + - * / 를 개수만큼 중복해서 넣어줄 거임 
	static int[] perm; // 순열 결과
	static boolean[] visited;//순열 방문처리 
	static int max, min; // 출
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N =  Integer.parseInt(br.readLine());
		num = new int[N];
		oper = new ArrayList<>();
		max = Integer.MIN_VALUE; // 오답노트 : 음수 고려못함 
		min = Integer.MAX_VALUE;
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 4; i++) { // 오답노트 : 연산자 종류 수 만큼 반복 
			int cnt = Integer.parseInt(st.nextToken());
			for(int c = 0; c < cnt; c++) {
				oper.add(i); // ex) + 2개 - 3개라면 0,0,1,1,1이 들어오게 함 
			}
		}// 입력 끝 
		
		
		perm = new int[N-1];
		visited = new boolean[N-1];// 초기화 끝
		
		//순열
		permutation(0);
		
		
		//출력 
		sb.append(max).append("\n").append(min);
		System.out.println(sb);
	}
	
	private static void permutation(int cur) {
		if(cur == N-1) {
			int result = num[0]; // 첫번째 피연산자로 초기화 
			for(int c = 0; c < N-1; c++) { // 연산자수만큼 반복 
				if(perm[c] == 0) {
					result += num[c+1];
				}else if(perm[c] == 1) {
					result -= num[c+1];
				}else if(perm[c] == 2) {
					result *= num[c+1];
				}else if(perm[c] == 3) {
					result /= num[c+1]; //뒤로오는 피연산자가 0일 경우가 없으니 ㄱㅊ 						
				}
			}
			min = Math.min(min, result);
			max = Math.max(max, result);
			
			return;//오답노트 : 리턴을 빼먹음......... 
		}
		
		for(int i = 0; i < N-1; i++) {	
			if(!visited[i]) {
				perm[cur] = oper.get(i);
				visited[i] = true;
				permutation(cur+1);
				visited[i] = false;				
			}
		}
		
	}
}
