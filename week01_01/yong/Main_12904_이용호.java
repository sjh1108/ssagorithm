package algostudy.baek.week01_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * 문자열 뒤에 A 추가
 * 문자열 뒤집고 뒤에 B 추가
 * 추가하는거로 구현 해봤는데 어려움
 * 뒤에 A 제거
 * 뒤에 B 제거 하고 뒤집기
 */
public class Main_12904_이용호 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();
		String T = br.readLine();
		
		// T에서 제거해 나갈거니까 초기S와 길이 같을때까지 하나씩 제거
		while(S.length() < T.length()) {
			// 끝에 글자 확인
			char lastChar = T.charAt(T.length()-1);
			// 끝 글자 제거
			T = T.substring(0,T.length()-1);
			// 끝 글자 B였으면 문자열 뒤집기
			if(lastChar == 'B') {
				T = new StringBuilder(T).reverse().toString();
			}
//			System.out.println("T:" +  T);
		}
		if(T.equals(S)) {
			System.out.println(1);
		}
		else {
			System.out.println(0);
		}
	}
	

}
