package com.codingtest.bk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 문제 정리 
 * - 아홉 난쟁이의 키가 주어졌을 때, 
 * - 키의 합이 100이 되는 일곱 난쟁이를 오름차순으로 출력
 * 
 * 전략  
 * - 2중for문으로도 풀 수 있는데 조합연습 해보자. 
 * - 9개 중 7개를 고르는 조합+백트래킹으로 풀 수도 있지만
 * - 제외할 2명을 찾는 방식이 더 간단하다.
 *   (사실상 9C7 = 9C2 = 36 으로 동일하긴 함) 
 * 
 * 자료구조
 * - 정렬만 필요하니 배열로 충분해보임 
 *
 */
public class Main_2309_일곱난쟁이 {

	static int N = 9, R = 2; // 고정되어 있지만 변수로 사용하자 
	static int sum; // 키의 합 
	static int[] person; // 전체 키를 넣을 배열  
	static boolean found = false; // 추가 : 정답 찾으면 종료하기 위한 플래그 
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		person = new int[N];
		for(int i = 0; i < N; i++ ) {
			person[i] = Integer.parseInt(br.readLine());
			sum += person[i]; // 키를 다 합치기 
		}// 입력 끝
		
		Arrays.sort(person); // 오름차순 정렬해두기
	
		// 조합 시작 
		combi(0, 0, new int[R]);
		
		
	}

	// 9C2를 할 거
	// 준비물
	// - n개 원소를 가진 배열 = person[]
	// 파라미터 
	// - cnt : 현재까지 뽑은 조합 원소 개수, 배열에 저장될 인덱스
	// - start : 조합 시작할 원소의 인덱스 
	private static void combi(int cnt , int start, int[] fake) {
		if(found) return; // 가지치기 

		// 2개를 뽑았으면 종료
		if(cnt == R) {   
			// 검사해서 답이면 출력  
			if(check(fake)) {
				for(int i = 0; i < N; i++) {
					// 난쟁이중 가짜를 빼내자 
					boolean notFake = true;
					for(int j = 0; j < R; j++) {
						if(person[i] == fake[j]) {
							notFake = false; // 가짜 발견 
							break; // 추가 : 더 돌필요 없음 
						}
					}
					
					if(notFake) System.out.println(person[i]);	
				}
				found = true; // 추가 : 정답 찾음. 더 돌필요 없음 
			};
			return;
		//더 뽑아야하면 재귀 
		} else {
			for(int i = start; i < N; i++) {
				fake[cnt] = person[i]; // 남은것중 하나를 뽑아 
				combi(cnt + 1, i + 1, fake); //하나 더 뽑았고, 방금 i했으니 i+1부터 시작
				// 오답노트 : combi 매개변수 진짜 안 외워짐 
			}
		}
	}

	private static boolean check(int[] result) {
		//sum 바뀌면 곤란하니까 복사 
		int copySum = sum;
		// 하나씩 꺼내서 빼봐 
		for(int height : result) {
			copySum -= height;
		}
		// 100이 됐어? true 반환 
		return copySum == 100; 
	}
}
