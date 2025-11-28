package week11_04.Minsang.BOJ_2529;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_2529{
	static int k;
	static char[] A; // 숫자 배열을 받는게 아니니까 문자 배열로 char를 써야지
	static ArrayList<String> list;
	static boolean visited[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		k = Integer.parseInt(br.readLine());
		
		// 구해야하는 것 : k+1 자리의 최대 정수 (1), k+1 자리의 최소 정수 (2) => 문자열 방식으로 출력
		
		// 일단 잘 모르겠는 건 .
		// 부등호 기호 <,  > 두개 있는 건 어떻게 처리해야하지 ? -> char 배열로 받아서 < 또는 > 비교
		// <, > 이건 어떻게 입력 받아야함 ?
		// bfs -> 아니래 ... 백트래킹이래 !!!!!!!!!!!!!!!!!!!!!!!!!!
		// 출력할때 문자열로 출력하는 방법 ? -> 문자열 형태로 만들어서 출력하면 됨
		
		A = new char[k];
		
		st = new StringTokenizer(br.readLine()); // 부등호 전체를 한줄에 입력받기 때문에 한번에 읽어서 토큰화함
		for(int i = 0; i <k; i++) {
			A[i] = st.nextToken().charAt(0);
		}
		
		// 0~9까지의 숫자 중 어떤 숫자를 사용했는지 기록하기 위한 배열 준비 (숫자 중복 안되니까 !)
		visited = new boolean[10];
		
		// 순열로 10개의 숫자 중에서 뽑을거야
		// 1개씩 숫자를 뽑고, 방문 표시 -> 다음 자리로 넘어간다 -> (k+1)번째 자리까지 뽑기
		list = new ArrayList<>();
		
		backtracking(0, "");
		
		Collections.sort(list); // 백트래킹의 경우에는 전체 조합을 만들고, 부등호 비교를 하는 순 -> collections 함수로 정렬
		
		System.out.println(list.get(list.size()-1)); // 최대 정수 -> 문자열로 변환
		System.out.println(list.get(0)); // 최소 정수 -> 문자열로 변환
		
	}
	
	// 백트래킹 방식
	static void backtracking(int depth, String num) { // 순열로 nPr 숫자 조합할거임, 가지치기 할거니까 파라미터로 depth, num 넘겨주기
		if(depth == k+1) { // 숫자 자리수를 depth로.
			if(checked(num)) { // checked 메서드 추출
				list.add(num); // list에 넣어서 return
			}
			return;
		}
		
		for(int i = 0; i < 10; i++) {
			if(!visited[i]) { // 순열 -> 중복 체크 위해서 (방문 안했으면)
				visited[i] = true; /// 방문하고 방문 체크
				backtracking(depth+1, num+i); // backtracking 여기서 한번 돌려주고, 중복 x -> depth+1할때마다 다음자리수  문자열 추가 num + i
				visited[i] = false; // 방문 x 체크
				
			}
		}
	}
	
	static boolean checked(String num) { // 부등호 기준으로 숫자 비교해서 체크
		for(int i = 0; i < k; i++) {
			
			char sign = A[i]; // 부등호 
			int left = num.charAt(i) - 0; // 왼쪽
			int right = num.charAt(i+1) - 0; // 오른쪽
			
			if(sign == '<' && !(left < right)) { // false 체크
				return false;
			} else if (sign == '>' && !(left > right)) { // false 체크
				return false;
			}
		}
		
		// 최종 return true
		return true;
		
	}

}
