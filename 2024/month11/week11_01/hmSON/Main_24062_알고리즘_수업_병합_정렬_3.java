import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_24062_알고리즘_수업_병합_정렬_3 {
	
	/*
	 * BOJ_24062 : 알고리즘 수업 - 병합 정렬 3(Gold_4)
	 * 자료구조 및 알고리즘 : 구현, 병합 정렬, 애드 혹
	 * 
	 * 두 배열이 주어진다. 배열 A는 정렬해야 할 초기 상태이다.
	 * 배열 A를 정렬하다가 정렬 B와 동일한  상태가 되는지 확인해야 한다.
	 * 
	 * 매 저장마다 배열 전체를 체크하면 시간 초과가 발생한다. N의 최대값이 500_000이다.
	 * 따라서 값 저장이 일어날 때마다 해당 위치가 일치하는지 확인해야 한다.
	 */
	
	// 배열 크기, 일치 여부가 확인된 마지막 인덱스(0~matchCnt까지는 arr와 target이 동일)
	static int n, matchCnt = -1;
	// 원본 배열, 목표 배열, 병합 정렬에 사용할 임시 저장용 배열
	static int[] arr, target, temp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		arr = new int[n];
		target = new int[n];
		temp = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			target[i] = Integer.parseInt(st.nextToken());
		}
		
		// 배열의 초기 상태와 목표 배열을 비교해서 몇번째 인덱스까지 일치하는지 확인
		for(int i=0; i<n; i++) {
			if(arr[i] == target[i]) matchCnt = i;
			else break;
		}
		
		// 초기 배열 == 목표 배열인 경우
		if(matchCnt == n-1) {
			System.out.println(1);
			return;
		}
		
		// 병합 정렬, 끝나고도 목표 배열을 찾지 못한 경우 0 출력
		mergeSort(0, n-1);
		System.out.println(0);
	}
	
	static void mergeSort(int left, int right) {
		// 부분 배열 길이 1 -> 리턴
		if(left >= right) return;
		
		// 병합 정렬
		int mid = (left + right) / 2;
		mergeSort(left, mid);
		mergeSort(mid+1, right);
		
		int p = 0, id1 = left, id2 = mid+1;
		while(id1 <= mid && id2 <= right) {
			int n1 = arr[id1], n2 = arr[id2];
			if(n1 <= n2) {
				temp[p++] = n1;
				id1++;
			} else {
				temp[p++] = n2;
				id2++;
			}
		}
		
		while(id1 <= mid) {
			temp[p++] = arr[id1];
			id1++;
		}
		
		while(id2 <= right) {
			temp[p++] = arr[id2];
			id2++;
		}
		
		// 임시 배열의 값을 실제 배열 arr로 옮기는 과정
		// 동시에 목표 배열과의 비교도 수행
		for(int i=left; i<=right; i++) {
			int next = temp[i-left];
			
			// 이미 일치한 상태인 인덱스의 값을 바꾸는 경우 -> 그 이후에도 일치할 가능성이 없다고 판단
			if(i <= matchCnt && next != target[i]) {
				System.out.println(0);
				System.exit(0);
			}
			
			// 현재 다루는 인덱스가 비교해야 할 위치이고, 값도 동일한 경우
			if(i == matchCnt + 1 && next == target[i]) {
				matchCnt++;
				// 그 이후 다음 값들의 비교 결과가 동일할 가능성이 존재함
				while(matchCnt+1 < n && arr[matchCnt+1] == target[matchCnt+1]) {
					matchCnt++;
				}
			}
			
			// 모든 값이 목표 배열과 일치함을 확인했다면 1 출력 후 즉시 종료
			if(matchCnt == n-1) {
				System.out.println(1);
				System.exit(0);
			}
			
			// 다음 과정을 위해 정렬 결과를 arr 배열에 실적용
			arr[i] = next;
		}
	}

}
