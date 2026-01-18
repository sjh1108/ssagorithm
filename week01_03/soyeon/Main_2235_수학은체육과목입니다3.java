package week; // 실버 4

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 문제 이해
 * - A이상 B 이하의 정수를 모두 더한 값
 * - A = 4, B = 21인 경우 이환이는 456789101112131415161718192021 로 적음
 * 
 * - A <= B <= 999 정수 
 * 
 * - 이환이가 적어온 숫자들의 나열이 주어지면 선생이 부른 두 수를 찾기
 * 
 * 입력 
 * - 이환이가 적은 답 문자열 S (S는 숫자. 길이는 1<=S<=2889)
 * 
 * 출력
 * - A, B를 공백으로 구분하여 출력
 * - 답이 두가지 이상이면 그중 A가 가장 작은 것 출력
 * 
 * 생각
 * - 답이 두가지 이상인 경우가 뭘까. 910 -> 9 10과 910 910
 * - 여러 방식을 시도해보았으나 코드가 복잡해지고 조건만 늘어나서 그냥 다 돌리기로함
 * 
 * 반성
 * - 도저히 답이 안 나와서 검색의 힘을 빌렸지만 아직 이해가 가지 않는다.
 * - 이거 왜 되는 거임?
 */
public class Main_2235_수학은체육과목입니다3 {

	static int A, B;
	static int[] arr;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String S = br.readLine(); //입력끝
		int targetlen = S.length();
				
		for(int A = 1; A <= 999; A++) {
			StringBuilder sb = new StringBuilder();
	
			for(int B = A; B <= 999; B++) {
				sb.append(B);
				
				 // 길이가 목표보다 커지면 더 붙일수록 더 커지므로 break
				if(sb.length() > targetlen) break;
				// 길이가 같아졌을 때만 비교
				if(sb.length() == targetlen && sb.toString().equals(S)) {
					System.out.println(A + " " + B);
					return; // A 오름차순이므로 가장 작은 A의 해를 바로 출력
				}
			}
		}
	}
}
