package week12_03.Minsang.BOJ_10814;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class BOJ_10814 {
	// 정수만 입력받는게 아니라, Integer랑 String으로 입력 -> Map을 써볼까 ?
	static int N;
	static Map<Integer, List<String>> map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		map = new HashMap<>(); // 초기화
		
		// Map에 넣기
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int key = Integer.parseInt(st.nextToken());
			String value = st.nextToken();
			
			// key 값으로비교 -> 비어있다면, 초기화 시켜주고 ?
			map.putIfAbsent(key, new ArrayList<>());
			// key 가져와서 value 값 추가
			map.get(key).add(value);
			
		}
		
		// 나이순으로 일단 정렬 (key 값으로 정렬)
		// keySet : key를 배열 형태로 모아줌
		List<Integer> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys);
		
		// 나이가 같으면 가입한 순 -> 배열 인덱스 순서 ?
		// key: 나이, value: 가입 순서
		// foreach문으로 나이 순서로 정렬
		for(int key : keys) {
			
			// 가입한 순서 정렬하기 위해 초기화
			List<String> values = map.get(key);
			
			for(String value : values) {
				
				sb.append(key).append(" ").append(value).append("\n");
			}
			
		}
		
		System.out.println(sb);
	}

}
