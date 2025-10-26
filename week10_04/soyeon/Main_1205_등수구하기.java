package bk.silver; // 4

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 문제이해
 * - 노래 랭킹 리스트
 * - 비 오름차순
 * - ex) 100 90 90 80 이면 1 2 2 4등
 * - P : 랭킹 리스트에 올라 갈 수 있는 점수의 개수
 * - N : 리스트에 있는 점수 수
 * - T : 태수 새 점수 
 * - 태수의 새로운 점수가 주어질 때 몇 등인지 구하는 포르개름 
 * - 랭킹에 못올라가면 -1
 * 
 * 주의
 * - 랭킹 리스트가 꽉 차 있을 때, 새 점수가 이전 점수보다 더 좋을 때만 점수가 바뀜
 * - 모든 점수는 최대 20억 -> int가능  
 * - P가 0인경우는 없지만 N은 0일 수 있음
 * 입력
 * - N T P
 * - 비오름차순 랭킹 리스트 점수 
 *
 * 후기
 * - 런타임에러
 */
public class Main_1205_등수구하기 {

	static int N, T, P;
	static int[] score;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		score = new int[N]; 
		
		// N 이 0인 경우를 고려 -> 무조건 1등 
		if(N == 0) {
			System.out.println(1); 
			return; // 이거 안해줘서 런타임에러
		}
			
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) { 
			score[i] = Integer.parseInt(st.nextToken());
		}
		
		// 나보다 큰 점수 수와 현재 랭킹을 분리해서 진행 
		int cnt = 1;
		int lank = 1;
		boolean isVal = false;
		for(int i = 0; i < N; i++) {
			if(cnt > P) break; // 랭킹이 꽉차면 빠져나옴 
			
			// 나보다 점수가 크면 카운트도 랭킹도 +1
			if(score[i] > T) {
				cnt++;
				lank++;
			// 같으면 랭킹이 유지됨 
			}else if(score[i] == T){
				cnt++;
			// 나보다 작은 게 있어야 랭킹에 들어갈 수 있으니 가능여부 판단 
			}else {
				isVal = true;
			}
		}
		
		// 랭킹 자리가 비어있거나, 점수가 들어갈 수 있을 시  lank반환 
		if(P > N || isVal) {
			System.out.println(lank);	
		// 아닐시 -1 
		}else {
			System.out.println(-1);
		}
		
		
		
	}
}
