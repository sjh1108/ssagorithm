package bk.silver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 문제 이해
 * - 단어 N개가 주어질 때 조건에 맞게 정렬
 * - 1. 길이가 짧은 것부터
 * - 2. 길이가 같으면 사전 순으로
 * 
 * 주의
 * - 중복제거
 * 
 * 전략
 * - ArrayList의 sort메서드 사용
 * - Comparator 직접 작성
 * - HashSet으로 중복제거
 * 
 * 오답노트
 * - 중복제거 어떻게 하는지 모르겠어서 찾아봤음
 * - HashSet을 사용하여 간단하게 중복제거 가능
 * 
 * - 정렬 어떤게 오른차순인지 헷갈렸음
 */
public class _Main_1181_단어정렬 {

	static int N; // N개의 단어 input
	static ArrayList<String> list; // 단어 입력
	static HashSet<String> set; // 중복제거
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		set = new HashSet<>(); // 중복제거
		for(int i = 0; i < N; i++) {
			set.add(br.readLine());
		}// 입력끝
		
		list = new ArrayList<>(set);
		
		// 람다로 정렬기준 만들기 Comparator
		list.sort((s1, s2)->{
			// 문자열 길이 같으면
			if(s1.length() == s2.length()) {
				// 사전 오름차순
				return s1.compareTo(s2); 
			}
			// 길이 다르면 길이 오름차순
			return s1.length() - s2.length();
		});
		
		// list.get(i)하는 방법 까먹어서 이걸로 했는데 더 깔끔한듯
		for(String s : list) {
			sb.append(s).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
