package Rank5.gold_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_12015 {
	
	static int[] memo;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		// 현재 리스트 사이즈를 비교할 변수
		int curSize = 0;
		// 원소를 저장하는 배열이 아닌, 최장 부분 수열을 기록할 배열임
		memo = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			int next = Integer.parseInt(st.nextToken());
			// 아직 리스트에 아무것도 없다면 -> 즉시 사이즈 카운트, 리스트 0번째에 새 값 입력
			if(curSize == 0) {
				curSize++;
				memo[0] = next;
				continue;
			}
			// 이분 탐색으로 새 값이 입력될 수 있는 인덱스 반환
			int res =binarySearch(next, curSize);
			// 만약 리스트의 뒤에 새 값을 추가해야 한다면 사이즈 카운트
			if(res == curSize) curSize++;
			// 결과와 관계없이 이분 탐색으로 찾은 인덱스 위치에 새 값 입력
			memo[res] = next;
		}
		
		System.out.println(curSize);
	}
	
	static int binarySearch(int x, int size) {
		// ans : 반환하고자 하는 결과 인덱스, 초기값은 size로 설정
		int left = 0, right = size, ans = size;
		
		while(left <= right) {
			int mid = (left + right) / 2;
			// 조건 1 : 삽입하려는 값보다 작다면, 이 위치에는 삽입할 수 없음
			// 조건 2 : 삽입하려는 값보다 크다면, 이 위치에 삽입될 가능성이 있음
			// 조건 3 : 삽입하려는 값과 동일하다면 변화 없음. 즉시 탐색 종료
			if(memo[mid] < x) {
				left = mid + 1;
			} else if(memo[mid] > x){
				ans = mid;
				right = mid - 1;
			} else {
				return mid;
			}
		}
		
		return ans;
	}

}
