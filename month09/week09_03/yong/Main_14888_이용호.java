package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_14888_이용호 {
/*
 * 음수를 양수로 나눌때 양수로 나누고 몫을 음수로 바꾼 것과 같다 -6 / 2 = -3
 * 우선순위 무시하고 앞에서 진행한다
 * 나눗셈은 장수 나눗셈으로 몫만 취한다
 * 항상 결과는 -10억 ~ 10억 (연산중에도 이 값을 넘지 않음)
 */
	static int arr[];
	static int maxR, minR;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); //수 개수
		arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine()); // 수열의 수들
		for(int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] operNum = new int[4]; // 연산자 개수(+,-,x,/)
		//연산자 배열 생성
		int[] oper = new int[N-1]; // 연산자는 총 N-1개
		st = new StringTokenizer(br.readLine());
		
		int idx = 0;
		for(int i = 0; i < 4; i++) {
			operNum[i] = Integer.parseInt(st.nextToken());
			//연산자 개수만큼 연산자배열에 추가
			for(int j = 0; j < operNum[i]; j++) {
				oper[idx] = i;
				idx++;
			}
		}
		
//		System.out.println(Arrays.toString(oper));
		//연산자 순서 순열로 정해서 계산하기
		int[] seq = new int[N-1];
		boolean[] visited = new boolean[N-1];
		maxR = Integer.MIN_VALUE;
		minR = Integer.MAX_VALUE;
		permu(0, seq, N-1, visited, oper);
		System.out.println(maxR + "\n" + minR);
	}
	static void permu(int idx, int[] seq, int N, boolean[] visited, int[] oper) {
		if(idx == N) {
//			System.out.println(Arrays.toString(seq));
			int result = col(seq);
			maxR = Math.max(maxR, result);
			minR = Math.min(minR, result);
			return;
		}
		for(int i = 0; i < N; i++) {
			if(!visited[i]) {
				visited[i] = true;
				seq[idx] = oper[i];
				permu(idx+1, seq, N, visited, oper);
				visited[i] = false;
			}
		}
	}
	static int col(int[] seq) {
		int result = arr[0];
		for(int i = 0; i < seq.length; i++) {
			switch(seq[i]) {
			//더하기 일때
			case 0:
				result += arr[i+1];
				break;
			//마이너스 일때
			case 1:
				result -= arr[i+1];
				break;
			//곱하기 일때
			case 2:
				result *= arr[i+1];
				break;
			//나누기 일때
			case 3:
				if(result < 0) {//음수 /`
					result = (Math.abs(result) / arr[i+1]) * -1;
				}
				else{
					result = result / arr[i+1];
				}
				break;
			}
			
		}
		return result;
	}
}
