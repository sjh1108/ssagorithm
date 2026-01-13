package week; // 브3

import java.util.Scanner;

/**
 * 문제 이해
 * - 한 변의 길이가 1인 정사각형을 아래 그림과 같이 
 *   겹치지 않게 빈틈없이 계속 붙여 나간다. 
 * - 가장 아랫부분의 정사각형이 n개가 되었을 때, 
 *   실선으로 이루어진 도형의 둘레의 길이를 구하시오.
 * 
 * 문제 규칙
 * - 1 : 4
 * - 3 : 12
 * - 걍 곱하기 4하면되는 거 아님? 
 * 
 * 문제 발생
 * - 왜 fail이지. 
 * - n의 개수가 10^9까지임.
 * - 그럼 답이 10^9 * 4까지 수용가능해야함.
 *
 */
public class Main_15894_수학은체육과목입니다 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long N  = sc.nextInt();
		
		System.out.println(N * 4);
		
	}
}
