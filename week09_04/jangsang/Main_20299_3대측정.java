package algo2025_09_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_20299_3대측정 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens; 
	    public static void main(String[] args) throws IOException {
	        tokens = new StringTokenizer(input.readLine());
    	
	        int N = Integer.parseInt(tokens.nextToken()); // 팀 개수
	        int K = Integer.parseInt(tokens.nextToken()); // 팀 총합 기준
	        int L = Integer.parseInt(tokens.nextToken()); // 개인 최소 기준

	        StringBuilder nums = new StringBuilder();
	        int teamCount = 0;
	        boolean firstOut = true; // 출력 시 맨 앞 공백 방지용

	        for (int i = 0; i < N; i++) {
	        	tokens = new StringTokenizer(input.readLine());
	            int a = Integer.parseInt(tokens.nextToken());
	            int b = Integer.parseInt(tokens.nextToken());
	            int c = Integer.parseInt(tokens.nextToken());

	            int sum = a + b + c;

	            if (a >= L && b >= L && c >= L && sum >= K) {
	                teamCount++;
	                if (!firstOut) {
	                    nums.append(' ');
	                }
	                nums.append(a).append(' ').append(b).append(' ').append(c);
	                firstOut = false;
	            }
	        }

	        System.out.println(teamCount);
	        if (nums.length() > 0) {
	            System.out.println(nums.toString());
	        } else {
	            System.out.println();
	        }
	    }
	}
