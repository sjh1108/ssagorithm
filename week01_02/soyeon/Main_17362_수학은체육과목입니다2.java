package week; // 브 4

import java.util.Scanner;

/**
 * 문제 이해
 * - 엄지 1, 검지 2, 중지 3, 약지 4, 새끼 5, 약지 6, 중지 7, 검지 8, ... 
 * - 주어지는 숫자에 해당하는 손가락을  엄지부터 1,2,3,4,5로 치환해서 출력하기
 * 
 * 입력
 * - 자연수 n <= 10^9
 * - int 범위 내
 * 
 * 전략
 * - 1 1 / 2 2 / 3 3 / 4 4 / 5 5
 * - 6 4 / 7 3 / 8 2 / 9 1 / 10 2
 * - 11 3 / 12 4 / 13 5 / 14 4 / 15 3
 * - 16 2 / 17 1 / 18 2 / 19 3 / 20 4
 * - ...
 * - 1000 2
 * 
 * - 패턴 발견
 * - 1, 9, 17, 
 * - + 8만큼 반복해서 1이 나옴
 * - %8의 값이
 * - 1이 나오면 1
 * - 2가 나오면 2
 * - ... 5가 나오면 5
 * - 6이 나오면 4
 * - 7이 나오면 3
 * - 0이 나오면 2
 * - 
 */
public class Main_17362_수학은체육과목입니다2 {
	
	static int N;
	static int ans;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		
		int n = N%8;
		
		switch (n) {
		case 1:
			ans = 1;
			break;
		case 2:
			ans = 2;
			break;			
		case 3:
			ans = 3;
			break;
		case 4:
			ans = 4;
			break;
		case 5:
			ans = 5;
			break;
		case 6:
			ans = 4;
			break;
		case 7:
			ans = 3;
			break;
		case 0:
			ans = 2;
			break;
		default:
			break;
		}
		
		System.out.println(ans);
	}
}
