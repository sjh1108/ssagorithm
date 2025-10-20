import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	/*
	 * 이 수는 최대 3000자리 이다 -> N은 3000까지
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String num = br.readLine();
		char[] numList = num.toCharArray();
		int idx = 0;
		
		for(int i = 1;; i++) { // 1 ~ 3000 될수 있는 후보 번호들
			String possible = "" + i;
			//현재 번호 될수있으면 다음수 진행
			char[] posChar = possible.toCharArray();
			
			for(char p : posChar) {
				if(!(numList[idx] == p)) continue;
				idx++;
				if(idx < numList.length) continue;
				break;
			}
					
			if(idx >= numList.length) {
				System.out.println(i);
				break;
			}
			
		}
	}

}
