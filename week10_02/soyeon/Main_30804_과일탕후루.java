package bk.silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - N개의 과일
 * - 1~9번의 과일 종류
 * - 탕후루 양끝을 제거하여 과일을 두종류 이하 만들기
 * - 그 중 과일이 제일 많은 것의 과일 개수 구하기
 * 
 * 전략
 * - 투포인터 + 해시맵
 * 
 * 후기 
 * - 완전히 방향을 잘못 잡고 있었음. 
 * - 재귀, 큐, 셋 등을 이용하려고 했었음.
 * - 답안 코드 보면서 더 간결하게 풀이 가능한 걸 확인함 
 * - 현재 구간에 포함된 과일 종류가 2개 이하일 때 오른쪽 확장, 초과시 왼쪽을 줄이는 방법
 * - gpt에게 도움을 구함. 여전히 이해가 잘 안 가서 설명을 들어볼 예정. 
 *
 */
public class Main_30804_과일탕후루 {

	static int N; // 과일 개수 
	static int[] fruits;
	static HashMap<Integer, Integer> cnt; // 과일 종류별 개수를 담을 map
	static int max;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		fruits = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			fruits[i] = Integer.parseInt(st.nextToken());
		}
		
		cnt = new HashMap<Integer, Integer>();
		int left = 0;
		
		make(0);
		
		System.out.println(max);
	}
	
	
	private static void make(int left) {
		
		for(int right = 0; right < N; right++) {
			int cur = fruits[right];
			// 찾는 키가 존재한다면 찾는 키의 값을 반환하고 없다면 기본 값을 반환
			cnt.put(cur, cnt.getOrDefault(cur, 0) + 1); // 단순카운팅을 왜이렇게 어렵게 하는 거지
			
			while(cnt.size() > 2) {
				int leftFruit = fruits[left];
				cnt.put(leftFruit, cnt.get(leftFruit) - 1);
				if(cnt.get(leftFruit) == 0) {
					cnt.remove(leftFruit);
				}
				left++;
			}
			
			max = Math.max(max, right - left + 1);
		}
	}

}
