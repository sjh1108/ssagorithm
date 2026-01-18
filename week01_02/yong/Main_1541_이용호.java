package algostudy.baek.week01_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_1541_이용호 {
/*
 * "-" 한번 나오면 그 뒤에 전부 -
 * String.split()은 정규식을 사용하기 때문에 +는 반복 연산자로 인식되어 escape가 필요
 * -는 정규식 아니라 괜찮
 * split 안쓰려했는데, 그러면 숫자 자릿수 생각해서 코드 작성해줘야함   
 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String nums = br.readLine();
		int ans = 0;
		// -로 스플릿
		String[] splitByMinus = nums.split("-");
		// 스플릿 한것들 더해주기
		for(int i = 0; i < splitByMinus.length; i++) {
			int temp = 0;
			// 그냥 +를 기준으로 split 하면 오류남
			String[] splitByPlus = splitByMinus[i].split("\\+");
			for(int j = 0; j < splitByPlus.length; j++) {
				temp += Integer.parseInt(splitByPlus[j]);
			}
			// 첫 묶음은 더하기(- 나오기 전 묶음들)
			if(i == 0) {
				ans += temp;
			}
			else {
				ans -= temp;
			}
			
		}
		System.out.println(ans);
	}

}
