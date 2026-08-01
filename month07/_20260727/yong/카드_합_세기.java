import java.util.*;
import java.io.*;
public class 카드_합_세기 {
/*
 * 숫자 카드 N장, i 번째 카드 정수 Ai 적혀있음
 * 서로 다른 두장 골라 두 카드에 적힌 수의 합
 * 두 수의 합이 X 이하가 되는 카드 조합의 개수
 * N 최대 200000 -> 재귀 안될거같음 -> 조합은 아닌듯?
 * 
 * 정렬후 접근
 * 투포인터
 * 
 */

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int X = Integer.parseInt(st.nextToken());
		int[] cards = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			cards[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(cards);
		
		int left = 0;
		int right = N-1;
		long result = 0;
		while(left < right) {
			int leftSumRight = cards[left] + cards[right];
			// 가장 작은수와 가장 큰수의 합이 X보다 작다는 조건을 만족하면
			// cards[left] + cards[left+1]도 조건을 만족 함(정렬했으므로)
			// cards[left] + cards[left+1] ~ cards[right] 까지 조건을 만족함 -> right - left개의 조합 가능
			// 개수 더한 후 left 포인터 이동
			if(leftSumRight <= X) {
				result += right - left;
				left++; // 
			}
			// X보다 크면 가장 큰수인 right 포인터를 한칸 땅김
			else {
				right--;
			}
		}
		System.out.println(result);
	}
	


}
