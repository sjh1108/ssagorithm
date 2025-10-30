import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_1515 {
	
	// BOJ_1515 : 수 이어 쓰기(Silver_2)
	// 자료구조 및 알고리즘 : 문자열, 그리디, 브루트 포스
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 일부 숫자가 지원 상태의 수를 모두 붙인 문자열
		String str = br.readLine();
		
		// 하나의 문자씩 비교하기 위해 str에 포인터를 지정
		int pt = 0;
		// 1번부터 하나씩 올라가면서 매칭되는 수를 찾음
		int cnt = 1;
		while(true) {
			String cur = Integer.toString(cnt);
			for(int i=0; i<cur.length(); i++) {
				// 현재 숫자의 큰 자릿수부터 한 자씩 비교하면서 동일하면 포인터를 한 칸 이동
				char c = cur.charAt(i);
				if(c == str.charAt(pt)) {
					pt++;
					// 문자열 전체를 탐색한 경우 즉시 루프 종료
					if(pt == str.length()) break;
				}
				
			}
			
			if(pt == str.length()) break;
			
			// 아직 문자열이 남아있는 경우 다음 숫자 확인
			cnt++;
		}
		
		System.out.println(cnt);
	}

}
