/*
 * 서로 다른 카드 조합, 모든 합 중 K 번째로 작은 값
 * 
 * 풀이 흐름
 * 1. 카드 정렬
 * 2. 합의 범위 이분탐색(카드 처음 ~ 마지막) -> mid
 * 3. 투포인터를 활용한 mid 이하인 카드 조합 개수 계산(카드 합 세기 활용)
 * 4. 처음 K개 이상 되는 합 찾기
 */
import java.io.*;
import java.util.*;

public class 카드_합의_K번째_수 {
	
	static int N;
	static long K;
	static long[] cards;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Long.parseLong(st.nextToken());
		
		cards = new long[N];
		
		st = new StringTokenizer(br.readLine());
		
		for(int i = 0; i < N; i++) {
			cards[i] = Long.parseLong(st.nextToken());
		}
		
		Arrays.sort(cards);
		
		// 두 카드의 합이 될수 있는값을 이분탐색
		long low = cards[0] + cards[1]; // 가장 작은 합
		long high = cards[N-2] + cards[N-1]; // 가장 큰 합
		
		while(low < high) {
			long mid = (high + low) / 2;
			
			// countPairs(mid) -> 두 카드의 합이 mid이하인 조합의 개수를 반환
			// 조합 개수가 K개 보다 많으면 오른쪽 범위를 줄임(mid 감소)
			if(countPairs(mid) >= K) {
				high = mid;
			}
			// 조합 개수가 K개 보다 적으면 왼쪽 범위 줄임(mid 증가0
			else {
				low = mid +1;
			}
		}
		System.out.println(low);

	}
	// 카드 합 세기 풀이를 활용
	// 투포인터로 카드 조합 개수 계산(합이 target 이하인 조합의 개수)
	static long countPairs(long target) {
		int left = 0;
		int right = N - 1;
		
		long count = 0;
		
		while(left <= right) {
			long sum = cards[left] + cards[right];
			
			if(sum <= target) {
				count += right - left;
				left++;			
			}
			else {
				right--;
			}
		}
		return count;
	}

}
