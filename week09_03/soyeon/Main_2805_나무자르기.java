package ssagorithm.bk.s;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - N : 나무 수 (최대 100만) 
 * - M : 필요한 나무길이 (최대 20억????????)
 * - 나무의 키가 주어지는데 한번에 H의 높이 이상의 나무를 잘라서 가져감
 * - 잘려진 나무(나무키 - H)들이 최소 M이면서 가장 작은 값이되는 
 * 		높이의 최대값 H를 출력 
 *
 * 전략
 * - 마인크래프트 같은 건가? 싶어서 봤더니 자르는 높이의 경우의 수가 20억임
 * - 수상해서 알고리즘 분류를 보았습니다.
 * - 이분 탐색 가보자
 * 
 * - 나무를 내림차순으로 정렬하고 
 * 		index 0부터 원하는 높이에서 잘라 
 * 		필요한 나무길이 이상이 되면 다시 재귀하는 식으로 짜보려고했는데
 * 		마음대로 되지 않음
 * 
 * 후기
 * - 보통... 20억의 배열을 생성하지는 않겠지요. 
 * 		얼마큼부터 당황스러운 크기인지 짐작이 잘 안 가길래 
 * 		검색해보니 천만이상은 곤란해보임 
 * - 3시간 이상 답이 안 나와서 해답봄.
 * 		- N이 100만이어도 전부탐색하고 이분해도 되는 건가?
 * 			-> 이분하면 획기적으로 탐색 시간이 줄어드니까 겁먹지말자
 * 		- 시간복잡도를 신경쓰면서부터 자꾸 완탐해도 되는 걸 꺼리게 되는 것 같음
 * 			- ex) 마인크래프트 
 * 		- 제대로 계산 못할 거면 일단 완탐을 작성하고 시초난 후에 생각하자   
 */
public class Main_2805_나무자르기 {
	static int N, M; // 100만, 20억
	static Integer[] tree; // sort 내림차순을 위해 
	static int minSum; // 가능한 최소의 나무길이 합 
	static int ans; // 가능한 최대 높이
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st=new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		tree = new Integer[N];
		
		int max = 0;
		st=new StringTokenizer(br.readLine());		
		for(int n = 0; n < N; n++) {
			 tree[n] = Integer.parseInt(st.nextToken());
			 max = Math.max(tree[n], max); // 오답노트 : max를 10억으로 할필요 x 
		}
		
		Arrays.sort(tree, Collections.reverseOrder());// 내림차순 
		
		divide(0, max); // 나올 수 있는 나무 최고 높이 
		
		System.out.println(ans);
	}
	

	// 매개변수를 색종이 접기 문제처럼 탐색범위로 생각하지 말기
	// 여기서는 h의 범위를 기준으로 이분하고 있음 
	private static void divide(int start, int end) {
        if (start > end) return; // 잊지말기 

        int mid = (start + end) / 2;
        long sum = 0;

        for (int h : tree) { 
            if (h > mid) sum += h - mid;
        }

        if (sum >= M) { 
            // 충분히 가져감 → 더 높이 잘라도 됨
            ans = mid; // 다음 재귀때 이곳에 진입할 수 있다면 더 높은 값이니 따로 조건 필요 x 
            divide(mid + 1, end);
        } else {
            // 부족 → 더 낮춰야 함
            divide(start, mid - 1);
        }
    }
}
