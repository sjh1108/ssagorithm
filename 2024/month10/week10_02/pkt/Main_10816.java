package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main_10816 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] myCards = new int[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine()); 
		for (int i = 0; i < N; i++) {
			myCards[i] = Integer.parseInt(st.nextToken());
		}
		
		int M = Integer.parseInt(br.readLine());
		int[] checkForCards = new int[M];
		st = new StringTokenizer(br.readLine()); 
		for (int i = 0; i < M; i++) {
			checkForCards[i] = Integer.parseInt(st.nextToken());
		}
		
		Map<Integer, Integer> map = new HashMap<>();
		Arrays.sort(myCards); // myCards를 정렬 각 키 갯수 세기 위해-10 -10 2 3 3 ...
		int idx = 0;
		int temp = 10000001; // 같지 않으면 리셋하기 위함 — idx를 올바르게 사용하기 위해
		for (int x : myCards) {
			if(temp != x) {
				idx = 0;
			}
			map.put(x, ++idx);
			temp = x; 
		}
		
		StringBuilder sb = new StringBuilder();
		
		int part = 0;
		for (int x : checkForCards) {
			if(map.get(x) == null) { // 0인 애들이 null 나와서 처리해줌
				part = 0;
			} else {
				part = map.get(x);
			}
			sb.append(part + " ");
		}
		
		System.out.println(sb);
	}
}
