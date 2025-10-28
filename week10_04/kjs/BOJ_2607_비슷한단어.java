package Test;

	import java.io.*;
	import java.util.*;

	public class BOJ_2607_비슷한단어 {
	    public static void main(String[] args) throws Exception {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	        int n = Integer.parseInt(br.readLine());
	        String first = br.readLine().trim();

	        int[] a = new int[26]; // 알파벳 배열 
	        for (int i = 0; i < first.length(); i++) {
	            a[first.charAt(i) - 'A']++;
	        }

	        int result = 0;

	        for (int i = 1; i < n; i++) {
	            String t = br.readLine().trim();
	            int[] b = new int[26];
	            for (int j = 0; j < t.length(); j++) {
	                b[t.charAt(j) - 'A']++;
	            }

	            int DiffLen = Math.abs(first.length() - t.length());
	            if (DiffLen > 1) continue;

	            int sum = 0;
	            for (int k = 0; k < 26; k++) {
	            	sum += Math.abs(a[k] - b[k]);
	            }

	            boolean good = false;
	            if (DiffLen == 0) {
	            	good = (sum == 0 || sum == 2);
	            } else {
	            	good = (sum == 1);
	            }

	            if (good) result++;
	        }

	        System.out.println(result);
	    }
	}

