package bk.silver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 1부터 N까지 정수가 중복되지 않게 하나씩 적혀있는 N개의 카드 배정
 * - 일렬로 카드 배열
 * - 구간을 정해 구간을 통째로 뒤집는 연산 가능
 * 
 * - [2,5] : 2번째 카드부터 5번째 카드가 구간 (not index)
 * 
 * 입력
 * - 카드 개수 N (1000이하)
 * - 초기 카드 배열
 * 
 * 출력
 * - 조건에 맞게 배열하기 위한 연산 횟수 op (NxN 이하)
 * - 연산에 필요한 [L, R] 쌍  x op
 * 
 * 전략
 * - 정렬을.. 해보자
 * 
 * 오답 노트
 * - 최소 뒤집기를 하려다가 방법이 안 보임
 * - ArrayList를 사용한 방법을 찾았습니다... 
 * - 자료구조가 너무 약한 것 같다
 *
 *
 */
public class _Main_15887_욱제는결벽증이야 {

	static int N;
	static int[] arr;
	public static void main(String[] args) throws Exception{ 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N+1];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 1번부터 시작하므로 1~N+1 인덱스 사용
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}// 입력끝
		
		// 뒤집기 연산들을 저장
		List<String> ops = new ArrayList<>();
		
		// i번째 자리에 i를 넣음
		for (int i = 1; i <= N; i++) {
			
			// 이미 제자리에 있으면 넘어가고
            if (arr[i] == i) continue;

            // 현재 i가 어디있는지 찾기
            int pos = -1; // i 위치
            for (int j = i + 1; j <= N; j++) {
                if (arr[j] == i) {
                    pos = j;
                    break;
                }
            }
            
            // i~pos 구간 뒤집기
            // i가 i위치로 오게 하는 것을 최우선 하기

            int left = i; // 왼쪽 포인터
            int right = pos; // 오른쪽 포인터

            // 이 방법이 안 떠올랐음!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // 포인터 사용해서 뒤집는 방법을 꼭꼭 기억하도록 하자
            while (left < right) {
            	// swap
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }

            ops.add(i + " " + pos);
        }
		
		// 출력
		StringBuilder sb = new StringBuilder();
        sb.append(ops.size()).append('\n');
        for (String op : ops) {
            sb.append(op).append('\n');
        }

        System.out.print(sb);
		
	}
}
