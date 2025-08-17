package Boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1325_장상민 {
	static Queue<int[]> queue = new ArrayDeque<>();
	static ArrayList<Integer> comMap;
	static int[] maxHacked;
	static int N, M;
	
	private static void bsf(String a, String b) {
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		//컴퓨터 번호
		//신뢰하는 관계 줄
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		//arrayList초기화
		//maxHacked초기화
		
		// 신뢰하는 관계 입력받고 
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
		}
		//bsf
		
	}
}
