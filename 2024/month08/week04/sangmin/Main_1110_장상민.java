package month08.week04.sangmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_1110_장상민 {
	static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static int n, tens, ones, cnt, newNum, start;
	static boolean cycle = true;
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		n = Integer.parseInt(input.readLine());
		start = n;
		cnt = 0;
		
		while(cycle) {
			tens = n / 10;
			ones = n % 10;
			newNum = ones * 10 + ((tens + ones) % 10);
		
			cnt++;
			n = newNum;
			
			if(start == newNum) break;
		}
		System.out.println(cnt);
	}
}
