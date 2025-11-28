import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_1725_히스토그램 {
	
	/*
	 * BOJ_1725 : 히스토그램 (Platinum_5)
	 * 자료구조 및 알고리즘 : 분할 정복(Divide and Conquer), 투 포인터
	 *
	 * [문제 요약]
	 * - N개의 막대로 이루어진 히스토그램이 주어진다. (각 막대의 너비는 1)
	 * - 히스토그램 내에서 가장 넓이가 큰 직사각형의 크기를 구하라.
	 *
	 * [핵심 아이디어]
	 * - 히스토그램을 절반으로 나누어 분할 정복(DnC)을 수행한다.
	 * - 특정 구간 [left, right]의 최대 넓이는 다음 세 가지 경우 중 하나이다.
	 * 1. 왼쪽 부분 구간 [left, mid] 안에 존재하는 최대 넓이
	 * 2. 오른쪽 부분 구간 [mid+1, right] 안에 존재하는 최대 넓이
	 * 3. 가운데 경계선(mid, mid+1)을 포함하여 걸쳐 있는 직사각형의 최대 넓이
	 * - 1, 2번은 재귀로 해결하고, 3번은 중심에서 바깥쪽으로 확장하는 투 포인터로 해결한다.
	 *
	 * [구현 메모]
	 * - 중심 확장 로직: 너비는 1씩 증가하므로, 높이가 더 높은 쪽으로 확장해야
	 * 높이의 최솟값(minHeight) 감소를 최소화하여 더 큰 넓이를 기대할 수 있다(Greedy).
	 *
	 * [시간 복잡도]
	 * - 깊이 logN, 각 깊이별 전체 탐색 O(N) -> O(N log N)
	 */
	
	static int n;
	static int[] arr;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine());
		arr = new int[n];
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		// 전체 구간에 대해 분할 정복 시작
		System.out.println(dnc(0, n-1));
	}
	
	// 분할 정복 메서드: 구간 [left, right]에서 가장 큰 직사각형 넓이 반환
	static int dnc(int left, int right) {
		// 기저 조건: 막대가 하나인 경우 그 높이가 곧 넓이
		if(left == right) return arr[left];
		
		int mid = (left + right) / 2;
		
		// 1 & 2. 왼쪽 구간과 오른쪽 구간에서 각각 구한 최대 넓이 중 큰 값 선택
		int maxHalfRect = Math.max(dnc(left, mid), dnc(mid+1, right));
		
		// 3. 가운데 경계(mid, mid+1)를 포함하는 직사각형 구하기
		int p1 = mid, p2 = mid+1;
		int minHeight = Math.min(arr[p1], arr[p2]); // 현재 확장된 구간의 최소 높이
		int midRect = minHeight * 2; // 현재(너비 2) 넓이
		
		// 양쪽 구간 안에서 포인터 확장
		while(p1 > left && p2 < right) {
			// 높이가 더 높은 쪽으로 확장해야 유리함 (Greedy)
			if(arr[p1-1] >= arr[p2+1]) {
				minHeight = Math.min(minHeight, arr[--p1]);
			} else {
				minHeight = Math.min(minHeight, arr[++p2]);
			}
			// 확장 후 갱신된 높이와 너비로 넓이 계산 및 최댓값 갱신
			midRect = Math.max(midRect, minHeight * (p2 - p1 + 1));
		}
		
		// 오른쪽 끝에 도달하고 왼쪽이 남은 경우
		while(p1 > left) {
			minHeight = Math.min(minHeight, arr[--p1]);
			midRect = Math.max(midRect, minHeight * (p2 - p1 + 1));
		}
		
		// 왼쪽 끝에 도달하고 오른쪽이 남은 경우
		while(p2 < right) {
			minHeight = Math.min(minHeight, arr[++p2]);
			midRect = Math.max(midRect, minHeight * (p2 - p1 + 1));
		}
		
		// 좌/우 내부 최대 넓이와, 가운데 걸친 최대 넓이 중 최댓값 반환
		return Math.max(maxHalfRect, midRect);
	}

}