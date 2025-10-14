package algo2025_09_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main_1339_단어수학 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N;
	static int[] alph;
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine());
		alph = new int[26]; //A-Z
		
		for (int i = 0; i < N; i++) {
		String string = input.readLine();
		int s = string.length();
			for (int j = 0; j < s; j++) { // 1의자리 알파벳부터 시작해야함.
				int num = string.charAt(j) - 'A';
				alph[num] += Math.pow(10,s-j-1);  //10의 제곱으로 자리수 가중치주고 
			}
		}
		
		Integer[] w = new Integer[26]; // 래퍼클래스 -> 정렬을 위함.
		for (int i = 0; i < 26; i++) {
			w[i] = alph[i];
		}
		
		Arrays.sort(w, Collections.reverseOrder());
		
		int num = 9;
		int sum = 0;
		for (int i = 0; i < 26; i++) {
			if(w[i] == 0) {
				break;
			}
			sum += w[i]*num;
			num--;
		}
		System.out.println(sum);
		
		
	}
}
