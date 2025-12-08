import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1781_컵라면 {

	/*
	 * BOJ_1781 : 컵라면 (Gold_2)
	 * 자료구조 및 알고리즘 : 그리디, 우선순위 큐, 정렬
	 *
	 * [문제 요약]
	 * - N개의 문제마다 데드라인과 컵라면 수(보상)가 주어진다.
	 * - 각 문제를 푸는 데는 단위 시간 1이 소요된다.
	 * - 데드라인을 넘기지 않고 얻을 수 있는 컵라면의 최대 개수를 구하라.
	 *
	 * [핵심 아이디어]
	 * - 데드라인이 이른 순서대로 문제를 정렬하고 순차적으로 접근한다.
	 * - 일단 현재 문제를 푼다고 가정하고 우선순위 큐(선택된 문제 목록)에 넣는다.
	 * - 만약 '선택된 문제의 수(소요 시간)'가 '현재 문제의 데드라인'을 초과한다면,
	 * 물리적으로 모든 문제를 풀 수 없으므로, 지금까지 선택한 것 중 '보상이 가장 작은 문제'를 포기(poll)한다.
	 * - 이를 통해 항상 데드라인 내에서 가장 큰 보상을 유지할 수 있다.
	 *
	 * [구현 메모]
	 * - PriorityQueue에는 문제의 인덱스를 담고, Comparator를 통해 실제 컵라면 수를 비교한다.
	 * - Min-Heap을 사용하여 가장 적은 컵라면을 주는 문제를 O(logN)으로 빠르게 제거한다.
	 *
	 * [시간 복잡도]
	 * - 정렬: O(N log N)
	 * - 큐 삽입/삭제: N번 루프 * O(log N)
	 * - 전체 복잡도: O(N log N)
	 */

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		// arr[i][0]: 데드라인, arr[i][1]: 컵라면 수
		int[][] arr = new int[n][2];
		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		// 1. 데드라인 기준 오름차순 정렬
		// 데드라인이 같다면 컵라면 수 기준 정렬 등은 굳이 필요 없음. 데드라인이 빠른 것부터 처리하며 큐의 크기(시간)를 검증하기 위함
		Arrays.sort(arr, (a, b) -> a[0] - b[0]);
		
		// 2. 선택된 문제들을 관리할 우선순위 큐 (Min-Heap)
		// 큐에는 문제의 인덱스(i)를 저장하되, 정렬 기준은 '컵라면 수(arr[i][1])' 오름차순
		PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.comparingInt(a -> arr[a][1]));
		
		for(int i=0; i<n; i++) {
			// 일단 현재 문제를 푼다고 가정하고 큐에 추가
			q.add(i);
			
			// 3. 데드라인 초과 검사
			// q.size()는 현재까지 선택한 문제들을 푸는 데 걸리는 총 시간과 동일함.
			// 만약 이 시간이 현재 문제의 데드라인(arr[i][0])보다 크다면 불가능한 스케줄임.
			while(q.size() > arr[i][0]) {
				// 따라서, 선택된 문제 중 가장 보상(컵라면)이 적은 문제를 제거하여 시간을 확보함
				q.poll();
			}
		}
		
		// 큐에 남아있는 문제들이 최종적으로 선택된 최적의 해 집합임
		int total = 0;
		while(!q.isEmpty()) {
			total += arr[q.poll()][1];
		}
		System.out.println(total);
	} 
	
}