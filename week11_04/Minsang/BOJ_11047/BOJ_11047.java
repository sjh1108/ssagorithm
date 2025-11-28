package week11_04.Minsang.BOJ_11047;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11047{
	static int N, K;
	static int[] A;
	
	// 구해야하는 것 : K 합을 만들기 위해 필요한 동전 개수의 최솟값
	
	// 접근 -> 그리디로 해야한다 ?
	// 근데 난 그리디 할줄 모름.
	// 그리디 : 각 단계에서 최적이라고 생각되는 것을 선택해 나가는 방식
	// 그럼 여기서 ? 가장 큰 가치를 지닌 동전부터 선택하는 것이 당연한거지 ?
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		A = new int[N];
		
		for(int i = 0;  i < N; i++) {
			A[i] = Integer.parseInt(br.readLine()); // 동전 종류 한줄씩 받고 있으니까 st.nextToken 필요 없고, br.readLine으로 받으면 됨
		}
		
		int cnt = 0; // 개수 출력해야하니까 일단 cnt=0 초기화 해주고
		
		// 필요한 동전 개수의 최솟값을 구해야하니까 금액이 큰것부터 비교해보자 ?? ----> 이게 그리디 ?!
		// 그러니까 for문을 N-1에서부터 시작해서 0까지 하나씩 -하면서 돌아
		for(int i = N-1; i >= 0; i--) {
			if(A[i] <= K) { // 금액이 일단 K보다 작아야하는것이 조건
				cnt += K/A[i]; // 합 / 금액 (A[i])로 나눌때마다 동전 개수 ++
				K = K%A[i]; // 나눴을때 남은 금액 => 나머지 필요하니까 % -> K%A[i]로 나누면서 for문 돌면 됨
			}
		}
		System.out.println(cnt);

	}

}
