package day1020;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2309_일곱난쟁이 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int [] a = new int [9];
		int sum = 0;
		for(int i = 0; i<9; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			a[i] = Integer.parseInt(st.nextToken());
			sum+=a[i];
		}
		for(int i=0; i<8; i++) {
			for(int j=i+1; j<9; j++) {
				if(sum-a[i]-a[j]==100) {
					a[i]=0;
					a[j]=0;
					Arrays.sort(a);
					for(int k=2; k<9; k++) {
						System.out.println(a[k]);
					}
					return;
				}
			}
		}
		
	}

}
