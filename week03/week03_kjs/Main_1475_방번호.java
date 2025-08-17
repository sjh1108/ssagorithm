package ssagorithm.week03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Boj1475_방번호 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 방번호 N 입력
		int N = Integer.parseInt(br.readLine());
		// 방번호 길이 변수 담기
		int len = Integer.toString(N).length();
//		System.out.println(len);
		int[] Narr = new int[len]; // 방 번호 각 배열 담기
//		int[] comp = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] ckArr = new int[10]; // 0~9 각 자리수가 있냐 없냐 확인용

		// Narr idx 0 -> 가장 높은 자리 수
		for (int i = len - 1; i >= 0; i--) {
			Narr[i] = N % 10;
			N /= 10;
//			System.out.print(Narr[i]);
		}

//		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		// 0~9 Narr[i] 값이 등장한 횟수 카운팅
		for (int i = 0; i < len; i++) {
			ckArr[Narr[i]]+=1;
		}
		
		//System.out.println("chArr 출력");
//		for (int i = 0; i < ckArr.length; i++) {
//			int j = ckArr[i];
//			//System.out.print(j + " ");
//		}
		//System.out.println();
		// 배열 6과9는 돌려서 사용할 수 있으니 합하여 올림
		int sixNine = (ckArr[6]+ckArr[9]+1)/2;
		ckArr[6]=sixNine;
		ckArr[9]=0;
		
		//System.out.println("== 정답 ==");
		for (int i : ckArr) {
			max = Math.max(max, i);
		}
		System.out.println(max);

	}

}
