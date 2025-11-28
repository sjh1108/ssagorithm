package samsung01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2839_설탕배달 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		int max = n/3;
		int min = n/5; 

		for(int i=0; i<=max; i++) {
			for(int j=0; j<=min; j++) {
				if(j*5+i*3 ==n) {
					System.out.println(i+j);
					return;
				}
			}
		}
		System.out.println("-1");
		
	}

}
