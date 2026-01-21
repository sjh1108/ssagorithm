package week12_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;


// 듣보잡 실버4
public class Main_1764 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		
		HashSet<String> set = new HashSet<>();
		
		for (int i = 0; i < N; i++) {
			set.add(br.readLine());
		}
		
		//List<String>[] arr = new ArrayList<>(); <-  나 바보 실수. 
		List<String> res = new ArrayList<>();
		
		
		for (int i = 0; i < M; i++) {
			
			String str = br.readLine();
			if(set.contains(str)) {
				res.add(str); // 담겨있나 판단.
			}
		}
		
		Collections.sort(res); // 사전순
		
		System.out.println(res.size());
		
		for (String result : res) {
			System.out.println(result);
		}
	}
}

// 해시맵은 키-값 매핑이 목적이라 이 문제에서는 과함.
// 중복여부만 판단한다면 해시셋으로 충분함. 

