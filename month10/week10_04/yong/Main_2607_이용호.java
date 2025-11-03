package algostudy.baek.week10_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 * 백준 2607 : 비슷한 단어
 */

public class Main_2607_이용호 {

	static char[] firstWord;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		String base = br.readLine();
		
		int[] baseCnt = new int[26];
		for(char c : base.toCharArray()) {
			baseCnt[c - 'A']++;
		}
		
		int simmilarCnt = 0;
		
		for(int i = 1; i < N; i++) {
			String word = br.readLine();
			int[] wordCnt = new int[26];
			
			for(char c : word.toCharArray()) {
				wordCnt[c - 'A']++;
			}
			
			int mismatch = 0;
			for(int j = 0; j < 26; j++) {
				mismatch += Math.abs(baseCnt[j] - wordCnt[j]);
			}
			
			if(mismatch > 2 || Math.abs(base.length() - word.length()) > 1) continue;
			simmilarCnt++;

		}
		System.out.println(simmilarCnt);
	}
		
}

