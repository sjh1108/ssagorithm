package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1157_단어공부_B3 {

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine().toUpperCase();
        int[] cnt = new int[26];
        for (int i=0; i <str.length(); i++) {
            char ch = str.charAt(i);
            cnt[ch - 'A']++;
        }
        int max = Integer.MIN_VALUE;
        char answer = '?';

        for (int i = 0; i < 26; i++) {
            if (cnt[i] > max) {
                max = cnt[i];
                answer = (char)(i + 'A');
            } else if (cnt[i] == max) {
                answer = '?';
            }
        }

        System.out.println(answer);

	}

}
