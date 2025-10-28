package bk.silver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 이해
 * - 비슷한 단어란
 * - 두 단어를 비교했을 때
 * - 두 단어가 같은 구성을 갖는 경우,
 * -  또는 한 단어에서 한 문자를 더하거나, 빼거나, 하나의 문자를 다른 문자로 바꾸어 나머지 한 단어와 같은 구성을 갖게 되는 경우
 * - 비슷한 단어가 몇개인지 출력하시오
 * 
 * 전략
 * - 단어당 A-Z 크기의 배열을 만들어서 포홤된 수 만큼 값으로 넣어보자
 * - 그걸 비교해서 차이가 2이상이면 비슷하지않다고 판단
 *
 * 후기
 * - 이거 지금 교체를 고려하지 못해서 답이 안 나오는 듯...
 * - 내일 좀 더 풀어보겠습니다.
 */
public class Main_2607_비슷한단어 {

	static int N;
	static char[] standard;
	static char[] word; // n번 재정의
	static int[] sAZ = new int[26];
	static int[] wAZ;
	static int ans;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		standard = br.readLine().toCharArray();
		
		for(int s = 0; s < standard.length; s++) {
			sAZ[standard[s] - 'A'] += 1;
		}
		
		for(int i = 0; i < N-1; i++) { // 아 ... N-1해야함 
			word = br.readLine().toCharArray();
			wAZ = new int[26];
			
			for(int w = 0; w < word.length; w++) {
				wAZ[word[w] - 'A'] += 1; // 아... 반복을 i로 돌았음 
				
			}		
			int diff = 0; // 차이를 저장. 1이하인 경우 비슷한 단어
			for(int az = 0; az < 26; az++) {
				if(sAZ[az] != wAZ[az]) {
					diff += Math.abs(sAZ[az] - wAZ[az]);
				}
			}
			
			if(diff <= 1) ++ans;
		}
		
		
		System.out.println(ans);
	}
}

