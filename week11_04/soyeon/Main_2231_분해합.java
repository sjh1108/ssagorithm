package week;// 브2

import java.util.Scanner;

/**
 * 문제이해
 * - 자연수 N
 * - 자연수 N의 분해합 : N과 N을 이루는 각 자리수의 합
 * - 예 ) 245의 분해합 256(=245+2+4+5)
 * - 예 ) 245는 256의 생성
 * - 가장 작은 생성자를 구해라 
 * 
 * 유의사항
 * - N 은 최대 백만 
 *
 * 전략
 * - 문제 이해가 어렵네
 * - 그러니까 N을 만들 수 있는 생성자를 찾으라는 거지 
 * - 어느정도 구간까지는 쌩으로 해야할 것 같은데
 *
 * 예시
 * - 99 -> 99 + 9+9 (18) = 117
 * - 100 -> 100 + 1+0+0 (1) = 101 (100이 되면 -16)
 * 
 * - 245 -> 245 + 2+4+5 (11) = 256
 * - 256 -> 246 + 2+4+6 (12) = 258   (2씩증가)
 * 
 * - 259 -> 249 + 2+4+9 (15) = 264    
 * - 250 -> 250 + 2+5+0 (7) = 257    (10의자리수가 바뀌면 -8)
 * 
 * - 999 -> 999 + 9+9+9 (27) = 1026
 * - 1000 -> 1000 + 1+0+0+0 (1) = 1001  (1000이 되면 ...-25?)
 * 
 * 시도
 * - 아니 규칙 못찾겠음 뭐가문제지
 * - 그냥 N이 들어왔을 때, 자기 보다 작은 것부터 그냥 쭉 내려가보자
 * - 테케도 1개니까 
 */
public class Main_2231_분해합 {
	
	static int ans = Integer.MAX_VALUE;
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		
		
		int con = N-1; // 생성자 후보 변수. 생성자는 N-1을 초과할 수 없음
		while(true) {
			int num = con; // 계산을 위해만든 변수
			int sum = con; // 합을 구하는 변수
			String strAns = String.valueOf(con);
			int len = strAns.length(); // 자리수
			

			for(int i = 0; i < len; i++) { // 각 자리수를 더함
				sum += num%10;
				num/=10;
			}
			
			if(sum == N) {
				if(ans > con) {
					ans = con;
				}
			}

			// 생성자가 없는 거임 
			if(con + (len*9) < N) {
				// 오답노트. 없을 때 처리법을 놓침
				if(ans == Integer.MAX_VALUE) {
					System.out.println(0);
				}else {
					System.out.println(ans);					
				}
				return;
			}
			
			con--;
		
		}
	}
}
