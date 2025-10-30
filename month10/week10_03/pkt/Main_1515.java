package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_1515 {
	public static void main(String[] args) throws IOException {

		// 1부터 N까지해서 찾는거네 원소 끝까지 그때 최소값

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String text = br.readLine();

		// String 다 돌아가며 가면 시간 초과남 - String은 불변객체이기에 새 문자열을 계속 기존꺼 복사
		// String형의 total에 계속 숫자를 붙여서
		// total.contains(입력값); 형태로 확인할 수 있으나 위의 이유로 시간초과.

		int chap = 0;
		int num = 1;
		while(true) {
			for (char c : String.valueOf(num).toCharArray()) { // num의 값을 배열로 변환 ['1','2','3' ...]
				if(text.charAt(chap) == c) chap++; // 같은 값있는지 확인 
				
				// 그리디하게 최소값을 찾아내면 출력하기
				if(chap == text.length()) {
					System.out.println(num);
					return;
				}
//				num++;
//				System.out.println(num);
				// num++; 애 위치가 실수.. num이 while이 아니라, 더 작은 범위인 for 돌때마다 실행됨.
				// 디버깅 시 이런 놈들을 혼내줘야 함. 
			}	
			num++;
		}
	}
}

