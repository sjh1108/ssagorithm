package month08.week04.pkt;

import java.io.*;
import java.util.*;

public class Main_1110_더하기사이클_박기택 {

	static int cycle;
	static int temp;
	static int N;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		temp = N;
		
		while(true) {
			int ten = N / 10; // 몫
			int one = N % 10; // 나머지
			int ess = (ten + one) % 10;
			int sum = one * 10 + ess;
			N = sum; 
			
			cycle += 1;
			
			if (N == temp) {
				break;
			}
			
			
		}
		
		System.out.println(cycle);

	}

}
