package java_home_work.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_1157_장상민 {
	static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static String str;
	static int[] cnt;
	
	public static void main(String[] args) throws IOException {
		cnt = new int[26];
		str = input.readLine().toUpperCase();
		
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i); 
            cnt[c - 'A']++;
        }
        
		int maxCnt = 0;
		int maxIdx = 0;
		boolean same = false;
		
		for (int i = 0; i < cnt.length; i++) {
			 if(cnt[i] > maxCnt) {
				 maxCnt = cnt[i]; 
				 maxIdx = i;
				 same = false;
			 } else if(cnt[i] == maxCnt) {
				 same = true;
			 }
		}
		System.out.println(same ? "?" : (char)('A'+maxIdx));
		
	}
}
