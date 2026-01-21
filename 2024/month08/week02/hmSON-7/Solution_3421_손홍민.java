import java.io.*;
import java.util.*;

class Solution_3421_손홍민 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
    // 각각 재료 수, 맞지 않는 조합의 수, 카운트에 사용할 조합 수, 사용된 재료 정보를 저장할 비트
    // 맞지 않는 재료 조합의 인접 행렬을 비트마스킹으로 구현
	static int n, deny, combCnt, setBit;
	static int[] bits;
	
	public static void main(String[] args) throws Exception {
		int t = Integer.parseInt(br.readLine());
		for(int i=1; i<=t; i++) {
			init();
			sb.append("#").append(i).append(" ");
			bt(0, 0);
			sb.append(combCnt).append("\n");
		}
		System.out.println(sb);
	}
	
	public static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		deny = Integer.parseInt(st.nextToken());
		
        // 맞지 않는 조합에 대한 그래프는 무방향 그래프
        // 이를 인접행렬(비트)로 구현
		bits = new int[n];
		for(int i=0; i<deny; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			bits[a] = bits[a] | (1<<b);
			bits[b] = bits[b] | (1<<a);
		}
		// 카운트 변수 및 중복 방지 비트 초기화
		combCnt = 1;
		setBit = 0;
	}
	
    // 새로운 조합이 등장할 때마다 조합수 카운트 및 setBit 최신화
	public static void bt(int start, int cnt) {
		if(cnt == n) return;
		
		for(int i=start; i<n; i++) {
			if((bits[i] & setBit) != 0) continue;
			
			setBit = setBit | (1 << i);
			combCnt++;
			bt(i+1, cnt+1);
			setBit = setBit & ~(1 << i);
		}
	}
	
}