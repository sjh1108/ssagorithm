package bk.silver; // 실버 3

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
 * 문제를 풀기에 앞서
 * - 옛날에 답보고 풀었던 문제라서 해시맵인 걸 알고 시작
 * - 빈도수를 셀때 해시맵을 써야한다는 말만 기억나는데 이유는 기억이 안난다
 * 
 * 문제 이해
 * - 전쟁에서 땅따먹음
 * - 한 땅에서 특정 군대의 병사가 절반을 초과하면 얻음
 * - 군대는 번호로 표시
 * 
 * 출력
 * - 각 땅을 지배한 군대의 번호를 출력
 * - 전쟁중이라면 'SYJKGW' 출력
 * 
 * 입력
 * - 땅의 개수 n (<=200)
 * - i번째 땅의 병사수 Ti(<=100,000), 병사번호 x Ti개 
 * - 근데!! 이 병사 번호가... 2^31이세요 .. int넘지않나요?
 * 
 * 전략
 * - 기억났다. 
 * - 병사 번호가 너무 많아서 배열로 만드는 무식한 방법x
 * - 병사 수도 10만이라서 정렬도 부담 
 * - Arraylist로 하기엔 크기를 21억개로 할수가없음.
 * - 
 * =>  위 이유들 때문에 HashMap 사용 필요
 * - HashMap은 key:value라서 병사 번호 21억 이딴거 해결가능
 * - 드가자
 */
public class _Main_1270_전쟁_땅따먹기 {

	static int N;
	static int T;
	static HashMap<Integer, Integer> map;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			T = Integer.parseInt(st.nextToken());
			map = new HashMap<>(); // 오답노트 : 이거 선언 안하면 계속 NPE뜬다
			
			int ans = -1;
			for(int j = 0; j < T; j++) {
				
				int key = Integer.parseInt(st.nextToken());
				// 오답노트 : map.getOrDefault(,)를 담을 변수는 기본형이면 안 됨
				Integer count = map.getOrDefault(key, 0); // 센 적 없으면 0
				
				map.put(key, ++count); // 병사수 하나 더 더해서 넣어두기
				
				// 과반수를 찾았으면 멈추기
				if(count > T/2) {
					ans = key;
					break;
				}
			}
			
			if(ans > 0) {
				sb.append(ans).append("\n"); // ans번호의 군대가 점령
			}else {
				sb.append("SYJKGW").append("\n"); // 전쟁중
			}
			
				
		}
		
		System.out.println(sb.toString());
		
	}
}
