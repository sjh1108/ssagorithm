package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ1157_단어공부 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String a = br.readLine().trim();
		a = a.toLowerCase();
		int [] c = new int[26];
//		for(int i =0; i<26; i++) {
//			
//		}

		for(int i=0; i<a.length(); i++) {
			char ch = a.charAt(i);
			c[ch-'a']++;
		}
		
		int max = -1;
		int maxIdx =-1;
		boolean same = false;
		
		for(int i=0; i<26; i++) {
			if(c[i]>max) {
				max=c[i];
				maxIdx=i;
				same = false;
			}else if(c[i] == max && max !=0) {
				same = true;
			}
		}
		
		if(same == true) {
			System.out.println("?");
		}else {
			char K = (char) ('A'+maxIdx);
			System.out.println(K);
		}
		
//		for(int i=0; i<a.length(); i++) {
//			System.out.print(b[i]+" ");
//		}
		

	}

}
