package com.codingtest.bk;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_2075_N번째큰수 {

	static int N, ans;
	static int[][] map;
	static Point[] big; // big에 가장 큰 수들을 열별로 담아 관리 
	
	static class Point{
		int r, c, v;

		public Point(int r, int c, int v) {
			super();
			this.r = r;
			this.c = c;
			this.v = v;
		}
		
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		map = new int[N][N];
		big = new Point[N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] = sc.nextInt();
				
				if(i == N-1) {
					big[j] = new Point(i, j, map[i][j]);
				}
			}
		}// 입력 끝
		
		find();
		
		System.out.println(ans);
	}
	
	// 최대값 찾기 
	private static void find() {
		int max, cnt;
		ArrayList<Integer> maxColIdx;
		for(int n = 0; n < N; n++) { // n 번째 큰수 찾기
			maxColIdx = new ArrayList<>(); // 문제 2: 초기화 안했다는 슬픈 소식 
			
			max = Integer.MIN_VALUE; // 비교할 때 초기화 // 너니? 설마? 
			for(int i = 0; i < N; i++) {
				if(max < big[i].v) {
					max = big[i].v;
				}
			}	
			
			// 같은 숫자가 몇개인지 (문제 1 : 이거 찾느라 천년 걸림)
			// ㅇㅏㄴㅣ ㅇㅣㄱㅔ ㅇㅏㄴㅣㄹㅏㄱㅗ?
			// 그럼 전부 
			// 아 맙소사 범위!!!!!!!!!!!!!!!!!!!!!!!!
			// 아닌데?? int -10억 되지않나?
			// 아 설마 음수..? 
			for(int i = 0; i < N; i++) {
				if(max == big[i].v) {
					maxColIdx.add(i);
				}
			}
			
			remakeBig(maxColIdx);
			ans = max;
		}
	}
	
	// 나머지 가장 큰 값을 찾아 big에서 해당 인덱스 갱신  
	private static void remakeBig(ArrayList<Integer> maxColIdx) {
		for(int idx : maxColIdx) {
			Point pre = big[idx];
			if(pre.r -1 < 0) return;
			big[idx] = new Point(pre.r -1, pre.c, map[pre.r -1][pre.c]);			
		}
	}
}
