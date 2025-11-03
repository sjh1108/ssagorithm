import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_24061_알고리즘_수업_병합_정렬_2 {
	
	/* 
	 * BOJ_24061 : 알고리즘 수업 - 병합 정렬 2(Silver_4)
	 * 자료구조 및 알고리즘 : 구현, 병합 정렬
	 * 서브 배열로 분리된 두 개의 배열 내 값을 비교한 뒤 작은 수부터 임시 배열에 담고, 실제 배열에 임시 배열의 값을 옮긴다.
	 * 이 때 수 하나가 옮겨지는 과정을 1회 저장으로 간주하고, K번째 저장을 한 뒤 배열 arr의 상태를 출력해야 한다.
	 * 단, 병합 정렬 과정의 총 저장 횟수보다 k가 큰 경우, -1을 출력해야 한다.
	 */
	
	// 배열의 크기, 목표 저장 시점, 저장 횟수
	static int n, stopCnt, cnt = 0;
	// 실제 배열, 병합 정렬에 사용할 임시 배열
	static int[] arr, temp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		stopCnt = Integer.parseInt(st.nextToken());
		
		arr = new int[n];
		temp = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		mergeSort(0, n-1);
		
		// K번째 저장 시점을 찾지 못한 채 병합 정렬 종료
		System.out.println(-1);
	}
	
	// 병합 정렬 알고리즘
	static void mergeSort(int left, int right) {
		// 데이터가 1개 -> 정렬할 필요 없음
		if(left >= right) return;
		
		// 현재의 배열을 2개의 서브 배열로 분리
		int mid = (left + right) / 2;
		mergeSort(left, mid);
		mergeSort(mid+1, right);
		
		// temp 배열의 포인터, 각각 서브 배열 1, 2의 포인터
		// 둘 중 한 쪽의 데이터를 전부 사용할 때까지 값 비교 후 더 작은 수를 temp에 추가
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
		
		// 두 서브 배열 중 한 쪽의 데이터가 남게 되므로 남은 데이터는 순서대로  temp에 추가
		while(id1 <= mid) {
			temp[p++] = arr[id1];
			id1++;
		}
		while(id2 <= right) {
			temp[p++] = arr[id2];
			id2++;
		}
		
		// temp 배열에 정렬된 내용을 arr 배열의 실제 위치에 저장
		// 또한 저장 과정에서 저장 횟수를 카운트하고, 만약 K번째 저장까지 도달한 경우 저장 후의 arr 배열 상태를 출력하고 실행 종료
		for(int i=left; i<=right; i++) {
			arr[i] = temp[i-left];
			cnt++;
			if(cnt == stopCnt) {
				StringBuilder sb = new StringBuilder();
				for(int j=0; j<n; j++) {
					sb.append(arr[j] + " ");
				}
				System.out.println(sb);
				System.exit(0);
			}
		}
	}

}
