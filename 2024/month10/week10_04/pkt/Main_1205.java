package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

// 등수 구하기_1205
public class Main_1205 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int score = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());

		if(N == 0) { // 입력오류나기 싫으면 여기 배치.
			System.out.println(1);
			return;
		}
		

		st = new StringTokenizer(br.readLine());

		
		List<Integer> list = new ArrayList<>();

		
		for (int i = 0; i < N; i++) {
			list.add(Integer.parseInt(st.nextToken()));
		}

		Collections.sort(list, Collections.reverseOrder());

		// 태수 넣기 전 확인. 
		
		if(N == P && list.get(P - 1) >= score) { // 범위가 꽉 차있는데, 마지막 원소가 태수보다 큰 경우
			return;
		}

		list.add(score); // 원소 하나 추가 범위 하나 늘어남.

		Collections.sort(list, Collections.reverseOrder()); // 리버스 오더.


		int rank = 1;
		for (int i = 0; i < N+1; i++) {
			if(list.get(i) > score) rank++; // 랭크 하나씩 뒤로 물러나기. 큰 동률 두개가 앞에 있어도 rank++가능 
			else break;
		}
		
		System.out.println(rank);
		
	}
}

// -> 구체화해서 고칠 필요. 노 오히려 복잡해짐. 

//만약 가장 큰 경우
//		if(score > list.get(1)) {
//			System.out.println(1);
//			return;
//		}

// 만약 중간에서 큰 경우 - 중복 ㅇ

// 만약 중간에서 큰 경우 - 중복 x

// 만약 범위를 벗어나기 직전인데 - 종복인 경우, 안밀려남

// 만약 범위를 벗어나기 직전인데 - 종복인 경우, 밀려남