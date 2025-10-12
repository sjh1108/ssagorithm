import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_14002_손홍민 {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		// 숫자 배열
		int[] arr = new int[n];
		// 각 숫자가 추가될 때마다 최장 증가 부분 수열의 길이를 기록
		int[] dp = new int[n];
		// 각 숫자가 추가될 때마다 이전 숫자의 인덱스가 무엇인지 기록
		// 이는 최장 증가 부분 수열을 만든 뒤 역추적을 통해 수열의 구성을 출력하기 위함
		int[] prev = new int[n];
		// prev 배열의 초기값은 -1 : 자신보다 앞에 있는 작은 숫자가 없음
		Arrays.fill(prev, -1);
		Arrays.fill(dp, 1);
		
		int maxLen = 0, maxIdx = 0;
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			int next = Integer.parseInt(st.nextToken());
			arr[i] = next;
			// 이전 숫자들과 비교하면서 자신보다 작은 숫자의 개수를 기록
			// 동시에 자신보다 작으면서 제일 뒤에 있는 숫자의 위치를 prev에 기록 -> 역추적시 해당 위치로 이동하기 위함
			for(int j=0; j<i; j++) {
				if(arr[i] > arr[j] && dp[i] < dp[j]+1) {
					dp[i]++;
					prev[i] = j;
				}
			}
			
			// 현재까지의 최대 길이와 그 부분수열의 마지막 숫자가 가진 인덱스를 기록
			if(dp[i] > maxLen) {
				maxLen = dp[i];
				maxIdx = i;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(maxLen + "\n");
		
		// 역추적
		// 데크를 스택처럼 사용하여 큰 수부터 기록
		Deque<Integer> stack = new ArrayDeque<>();
		while(maxIdx >= 0) {
			stack.addLast(arr[maxIdx]);
			maxIdx = prev[maxIdx];
		}
		
		// 부분 수열 출력
		while(!stack.isEmpty()) {
			sb.append(stack.pollLast() + " ");
		}
		System.out.println(sb);
	}

}
