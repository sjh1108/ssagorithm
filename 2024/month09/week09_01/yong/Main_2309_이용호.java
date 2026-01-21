package algo.baek.b2309;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_2309_이용호 {
static int[] dwarf, result,sortedResult;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		dwarf = new int[9];
		for(int i = 0; i < 9;i++) {
			dwarf[i] = Integer.parseInt(br.readLine());
		}
		result = new int[7];
		sortedResult = new int[7];
		comb(0,0);
		Arrays.sort(sortedResult);
		for(int height : sortedResult) {
			System.out.println(height);
		}
		
	}
	public static void comb(int depth, int start) {
		if(depth == 7) {
			int sum = 0;
			for(int idx : result) {
				sum += dwarf[idx];
			}
			if(sum == 100) {
				int i = 0;
				for(int idx : result) {
					sortedResult[i] = dwarf[idx];
					i++;
				}
				
			}
			return;
		}
		
		for(int i = start; i < 9; i++) {
			result[depth] = i;
			comb(depth+1,i+1);
		}
	}

}
