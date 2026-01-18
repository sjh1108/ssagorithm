import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_14003_손홍민 {
	
	static int[] arr, tails;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		// 길이별로 가능한 최소 끝값을 저장
		tails = new int[n];
		// 특정 길이의 최장 증가 부분 수열이 마지막으로 도달한 원본 배열의 인덱스를 저장
		int[] idxAtLen = new int[n];
		// 역추적용 배열, 각각 현재 인덱스 i 이전의 인덱스를 기록
		int[] prev = new int[n];
		Arrays.fill(prev, -1);
		
		// 현재까지 기록된 부분 수열의 길이
		int curSize = 0;
		arr = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			int next = Integer.parseInt(st.nextToken());
			arr[i] = next;
			
			// 현재 값이 들어갈 위치를 이분 탐색으로 찾음
			// tails 배열에서 next보다 크거나 같은 값이 처음 나오는 위치를 반환
			int res = binarySearch(next, curSize);
			tails[res] = next;
			
			// 현재 인덱스의 이전 인덱스를 기록
			prev[i] = (res > 0) ? idxAtLen[res-1] : -1;
			// 길이 res+1짜리 부분 수열의 마지막 원소 인덱스는 현재 i
			idxAtLen[res] = i;
			
			// 만약 새로 추가된 원소가 tails의 끝에 들어갔다면 부분 수열의 길이 증가
			if(res == curSize) curSize++;
		}
		
		// 최장 증가 부분 수열의 마지막 원소 인덱스
		int cur = idxAtLen[curSize-1];
		
		// 역추적을 위해 스택 사용. 큰 수부터 기록
		Deque<Integer> stack = new ArrayDeque<>();
		while(cur >= 0) {
			stack.addLast(arr[cur]);
			cur = prev[cur];
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(curSize + "\n");
		while(!stack.isEmpty()) {
			sb.append(stack.pollLast() + " ");
		}
		System.out.println(sb);
	}
	
	static int binarySearch(int x, int size) {
		int left = 0, right = size;
		
		// x가 들어갈 위치를 이분 탐색으로 찾음
		while(left < right) {
			int mid = (left + right) / 2;
			if (tails[mid] < x) left = mid + 1;
            else right = mid;
		}
		
		// x가 들어갈 인덱스 반환
		return left;
	}

}
