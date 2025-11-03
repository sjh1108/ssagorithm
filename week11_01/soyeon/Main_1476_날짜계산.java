package week;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - E : 지구 (1 <= E <= 15)
 * - S : 태양 (1 <= S <= 28)
 * - M : 달   (1 <= M <= 19)
 * - 세 수는 서로 다른 범위를 가짐
 * 
 * - 우리가 알 고 있는 1년은 1 1 1로 나타냄
 * - 15년은 15 15 15
 * 
 * - 1년이 지날때마다 세수는 1식 증가
 * - 범위를 넘어가면 다시 1이 됨
 * 
 * 전략
 * - 최소공배수같은 걸 고민했는데 해결이 도무지 안 돼서
 * - 무식한 방법 사용하기로 함
 * - 전부 비교해보자
 * 
 * 입력
 * - E, S, M
 * 
 * 출력
 * - E S M으로 표시되는 가장 빠른 연도를 출력
 * 
 * 오답노트
 * - 15 28 19 와 같이 나머지가 0이 되는 경우를 어떻게 고려해야하는지 1시간 고민함...
 *  
 */
public class Main_1476_날짜계산 {

	static int E, S, M;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		E  = Integer.parseInt(st.nextToken());
		S  = Integer.parseInt(st.nextToken());				
		M  = Integer.parseInt(st.nextToken());
		// 입력 끝
		int year = 1;
		while(true) {

			// 15, 28, 19가 배제됨을 방지
			if(E == 15) E = 0;
			if(S == 28) S = 0;
			if(M == 19) M = 0;
			
			if(year%15 == E && year%28 == S && year%19 == M) {
				System.out.println(year);
				return;
			}		
			year++;
		}
	}
}
