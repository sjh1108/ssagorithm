package algo2025_10_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_2607_비슷한단어 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	static String word;
	static int[] alph;
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine());
		word = input.readLine();
		
		alph = new int[26];
		for (char c : word.toCharArray()) {
			alph[c-'A']++;
		}
		
		int wordCnt = 0;
		for (int i = 0; i < N-1; i++) {
			String word2 = input.readLine();
			
			int[] alph2 = new int[26];
			for (char c : word2.toCharArray()) {
				alph2[c-'A']++;
			}
			int cnt = 0;
			for (int j = 0; j < 26; j++) {
				cnt += Math.abs(alph[j] - alph2[j]);
			}
			
			
			if(cnt == 0 || cnt == 1 || (cnt == 2 && word.length() == word2.length())) {
				wordCnt++;
			}
		}
		System.out.println(wordCnt);
		
		
		
	}
}
