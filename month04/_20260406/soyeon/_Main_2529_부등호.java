package bk.silver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 정리
 * - 부등호 k개가 입력됨
 * - 그 사이사이에 숫자 0~9를 넣어서 문자열을 만드는 거
 * - 그중에서 가장 큰 숫자와 작은 숫자를 출력하면됨
 * 
 * 전략
 * - 일단 실버이므로 드가자 전략
 * 
 * 오답노트
 * - DFS를 너무 오랜만에 풀어서 생각도 못했다. 이 문제는 누가봐도 순열인데..
 * - 문자열을 사용해야하는 문제에 너무 취약함. 관련 메소드도 너무 늦게 생각남
 * 
 *
 */
public class _Main_2529_부등호 {

	
	static int k;
	static char[] arr;
	static boolean[] visited = new boolean[10]; // 0~9
	static String max, min = ""; // 이걸 String으로 해야함
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		k = Integer.parseInt(br.readLine());
		arr = new char[k];
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int i = 0; i < k; i++) {
			arr[i] = st.nextToken().charAt(0); // String을 char로 바꿔줘야하네
		}// 입력끝
		
		
		// 전부 탐색해 
		for(int i = 0; i < 10; i++) {
			visited[i] = true;
			permu(i, 0, i + ""); // 숫자는 빈자리수를 0으로 만들어야해서 String으로 만들어야함 ㅠㅠ
			visited[i] = false; // 이걸 왜 해야하는지 한참생각함. 진짜 뇌가 굳었다.
		}
		
	}
	
	// 여기 내부를 거의 쓰지 못했다. 다시 풀어보자 꼭
	private static void permu(int now, int count, String num) {
		// 종료 조건~
		if (count == k) { // k개의 부등호를 다 만족했으면 결과 저장
            if (max.isEmpty() || num.compareTo(max) > 0) max = num;
            if (min.isEmpty() || num.compareTo(min) < 0) min = num;
            return;
        }
		
		for (int next = 0; next < 10; next++) {
            if (!visited[next]) { // 중복 체크
                if ((arr[count] == '<' && now < next) || (arr[count] == '>' && now > next)) {
                    visited[next] = true;
                    permu(next, count + 1, num + next);
                    visited[next] = false; // 백트래킹 (원상 복구)
                }
            }
        }
		
	}
	
	
}
