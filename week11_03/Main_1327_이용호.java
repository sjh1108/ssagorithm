package baek_week11_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1327_이용호 {
/*
 * 뒤집기 -> 역으로
 * 어떤 수 뒤집으면 오른쪽으로 K개 뒤집어야 함
 * 반드시 K개 뒤집어야함(즉 선택하는 카드 오른쪽엔 적어도 K개 있어야함)
 * bfs로 다 뒤집어 보기
 * 구간 뒤집기 어떻게 하지 -> 문자열로 ( s[start:i] + reverse[i:i+k] + s[i+k, end] ) 
 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		
		int[] arr = new int[N];
		for(int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		// 초기 상태
		StringBuilder sb = new StringBuilder();
		for(int i : arr) {
			sb.append(i);
		}
		String start = sb.toString();
		
		// 정답
		char[] temp = start.toCharArray();
		Arrays.sort(temp);
		String target = new String(temp);
		
		Queue<String> q = new LinkedList<>();
		Map<String, Integer> dist = new HashMap<>(); // 상태 : 연산횟수 
		
		q.offer(start);
		dist.put(start, 0);
		
		while(!q.isEmpty()) {
			String now = q.poll();
			int nowCnt = dist.get(now);
			
			// 정답 도달
			if(now.equals(target)) {
				System.out.println(nowCnt);
				return;
			}
			
			// 모든 구간 뒤집기
			for(int i = 0; i <= N - K; i++) {
				String next = reverse(now, i , K); // i 부터 k개 뒤집기
				
				if(!dist.containsKey(next)) { 
					dist.put(next, nowCnt+1);
					q.offer(next);
				}
			}
		}
		
		System.out.println(-1);
	}
	static public String reverse(String s, int idx, int K) {
		StringBuilder sb = new StringBuilder();
		sb.append(s.substring(0,idx));
		// 뒤집기
		StringBuilder mid = new StringBuilder(s.substring(idx, idx+K));
		sb.append(mid.reverse());
		sb.append(s.substring(idx+K));
		return sb.toString();
	}

}
