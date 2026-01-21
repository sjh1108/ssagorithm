import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_1337_올바른_배열 {
	
	/*
	 * BOJ_1337 : 올바른 배열(Silver_4)
	 * 자료구조 및 알고리즘 : 구현, 정렬, 투 포인터
	 * 
	 * 현재 주어진 배열을 정렬했을 때 5개의 수가 오름차순으로 연속되도록 숫자를 추가하고자 한다.
	 * 단, 추가하는 숫자의 개수를 최소화하고자 함. 따라서 연속된 숫자 5개를 구성하기 위해 필요한 숫자가 가장 적은 구간을 찾아내야 한다.
	 */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 배열 크기
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		// 투 포인터 사용 및 구간 확인을 위한 배열 정렬
		Arrays.sort(arr);
		
		// 최악의 경우는 숫자 1개. 즉, 4개의 숫자를 더 추가해야 하는 경우임
		// 따라서 2개 이상의 숫자가 포함된 구간만 확인할 것이며, 최소값을 저장할 변수 min의 초기값은 4로 설정
		// 지정한 구간의 제일 작은 수와 제일 큰 수의 차이가 5 미만인 경우 연속된 숫자 5개를 구성할 수 있다. 이 때 부족한 숫자의 개수를 갱신한다.
		// 탐색 중 min == 0이 되는 순간 즉시 루프 종료
		int left = 0, right = 1, min = 4;
		while(right < n && min > 0) {
			int diff = arr[right] - arr[left];
			if(diff < 5) {
				min = Math.min(min, 5 - (right - left + 1));
				right++;
			} else {
				left++;
				// 구간의 길이 1은 절대 취급하지 않음
				if(left == right) right++;
			}
		}
		
		System.out.println(min);
	}

}
