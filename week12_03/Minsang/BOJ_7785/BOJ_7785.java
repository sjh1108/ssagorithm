package week12_03.Minsang.BOJ_7785;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_7785 {
	static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		n = Integer.parseInt(br.readLine());
		
		HashMap<String, String> record = new HashMap<>();
		
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			record.put(st.nextToken(), st.nextToken());
		}
		
		// 역순으로 만들기 위해서 -> list로 설정해줬음
		List<String> keyset = new ArrayList<>(record.keySet());
		Collections.sort(keyset, Collections.reverseOrder());
		
		for(String key : keyset) {
			if(record.get(key).equals("enter")) {
				System.out.println(key);
			}
		}
	}

}
