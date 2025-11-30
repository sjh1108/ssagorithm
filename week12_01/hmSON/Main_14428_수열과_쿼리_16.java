import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14428_수열과_쿼리_16 {
	
	/*
	 * BOJ_14428 : 수열과 쿼리 16(Gold_1)
	 * 자료구조 및 알고리즘 : 세그먼트 트리
	 *
	 * [문제 요약]
	 * - 길이가 N인 수열이 주어지고, 두 가지 쿼리를 처리해야 한다.
	 * 1. update(i, v): i번째 수를 v로 바꾼다.
	 * 2. findMin(i, j): i번부터 j번까지 중에서 크기가 가장 작은 값의 '인덱스'를 출력한다.
	 * (단, 최솟값이 여러 개라면 가장 작은 인덱스를 출력한다.)
	 *
	 * [핵심 아이디어]
	 * - 구간의 최솟값 자체가 아닌 '인덱스'를 구해야 하므로, 세그먼트 트리의 각 노드에는
	 * 해당 구간에서 최솟값을 가지는 원소의 '인덱스'를 저장한다.
	 * - 값을 비교할 때는 저장된 인덱스를 이용해 원본 배열의 값을 참조한다.
	 * - 두 자식 노드 중 더 작은 값을 가진 인덱스를 부모로 올리되, 값이 같다면
	 * 더 작은 인덱스(왼쪽 자식 쪽)를 선택하여 조건(가장 작은 인덱스 출력)을 만족시킨다.
	 *
	 * [구현 메모]
	 * - tree[]: 구간 내 최솟값의 인덱스를 저장.
	 * - -1: 더미 노드나 유효하지 않은 구간을 표현하기 위한 값.
	 *
	 * [시간 복잡도]
	 * - 트리 구축(Init): O(N)
	 * - 업데이트(Update): O(logN)
	 * - 조회(FindMin): O(logN)
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int n, p;
	static int[] arr;   // 실제 값을 저장하는 원본 배열
	static int[] tree;  // 해당 구간 최솟값의 '인덱스'를 저장하는 세그먼트 트리
	
	static void init() throws Exception {
		int n = Integer.parseInt(br.readLine());
		arr = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		// 1. 트리 크기(p) 결정 (리프 노드 시작점)
		p = 1;
		while(p < n) p <<= 1;
		
		tree = new int[2*p];
		
		// 2. 리프 노드 초기화
		// 값이 아닌 '인덱스(i)'를 저장함
		for(int i=0; i<p; i++) {
			if(i < n) tree[p+i] = i;
			else tree[p+i] = -1; // 범위 밖 더미 노드
		}
		
		// 3. 내부 노드 구축 (Bottom-up)
		for(int i=p-1; i>0; i--) {
			int leftIdx = tree[i << 1];
			int rightIdx = tree[i << 1 | 1];
			
			// 유효하지 않은 노드 처리 (-1)
			if(leftIdx == -1) tree[i] = -1; // 둘 다 없으면 -1 (사실 이 경우는 거의 없음)
			else if(rightIdx == -1) tree[i] = leftIdx; // 오른쪽이 없으면 왼쪽 선택
			else {
				// 두 자식 인덱스가 가리키는 실제 값(arr[idx])을 비교
				// 작거나 같다면 leftIdx를 선택 (값이 같을 때 더 작은 인덱스 우선)
				tree[i] = arr[leftIdx] <= arr[rightIdx] ? leftIdx : rightIdx;
			}
		}
	}
	
	static void update(int idx, int value) {
		arr[idx] = value; // 원본 배열 값 갱신
		idx += p; // 트리에서의 리프 노드 인덱스
		
		// 리프부터 루트까지 올라가며 인덱스 정보 갱신
		while(idx > 1) {
			idx >>= 1;
			int leftIdx = tree[idx << 1];
			int rightIdx = tree[idx << 1 | 1];
			
			// 초기화 로직과 동일하게 비교 수행
			if(leftIdx == -1) tree[idx] = -1;
			else if(rightIdx == -1) tree[idx] = leftIdx;
			else tree[idx] = arr[leftIdx] <= arr[rightIdx] ? leftIdx : rightIdx;
		}
	}
	
	static void findMin(int left, int right) {
		left += p; 
		right += p;
		
		int minIdx = -1; // 현재까지 찾은 최솟값의 인덱스
		
		while(left <= right) {
			// 왼쪽 자식(홀수) 선택 시
			if((left & 1) == 1) {
				int idx = tree[left];
				if(minIdx == -1) minIdx = idx; // 첫 값
				else {
					// 현재 구간의 최솟값(arr[idx])이 지금까지의 최솟값(arr[minIdx])보다 작으면 갱신
					if(arr[idx] < arr[minIdx]) minIdx = idx;
					// 값이 같다면 인덱스가 더 작은 쪽을 선택
					else if(arr[idx] == arr[minIdx]) minIdx = idx < minIdx ? idx : minIdx;
				}
				left++;
			}
			// 오른쪽 자식(짝수) 선택 시
			if((right & 1) == 0) {
				int idx = tree[right];
				if(minIdx == -1) minIdx = idx;
				else {
					if(arr[idx] < arr[minIdx]) minIdx = idx;
					else if(arr[idx] == arr[minIdx]) minIdx = idx < minIdx ? idx : minIdx;
				}
				right--;
			}
			
			left >>= 1; 
			right >>= 1;
		}
		
		// 문제에서는 1-based 인덱스를 요구하므로 +1 하여 출력
		sb.append((minIdx+1)+ "\n");
	}
	
	public static void main(String[] args) throws Exception {
		init();
		int cmds = Integer.parseInt(br.readLine());
		
		while(cmds-- > 0) {
			st = new StringTokenizer(br.readLine());
			int cmd = Integer.parseInt(st.nextToken());
			
			if(cmd == 1) { // 1 i v : i번째 수를 v로 변경
				int idx = Integer.parseInt(st.nextToken()) - 1;
				int val = Integer.parseInt(st.nextToken());
				update(idx, val);
			} else { // 2 i j : i~j 구간 최솟값 인덱스 출력
				int left = Integer.parseInt(st.nextToken()) - 1;
				int right = Integer.parseInt(st.nextToken()) - 1;
				findMin(left, right);
			}
		}
		
		System.out.println(sb);
	}
	
}