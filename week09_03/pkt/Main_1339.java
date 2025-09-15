package week0911;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class Main_1339
{
	static long w[];
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
       
        w = new long[26];
        
        for (int i = 0; i < N; i++) 
        {
			String str = br.readLine();
			int len = str.length();
			
			
			
 			for (int j = 0; j < len; j++) 
			{
				int idx = str.charAt(j) - 'A';
				w[idx] += Math.pow(10, len - j - 1); //+ AAAAAA
				
			}
 			
        }
        
        Long[] alpha = new Long[26];
			
			for (int j = 0; j < 26; j++) 
			{
			alpha[j] = w[j];
		}
			
			Arrays.sort(alpha, Collections.reverseOrder());
			
			long sum = 0;
			int num = 9;
			for (int j = 0; j < 26; j++) 
			{
				if(alpha[j] == 0)
				{
					break;
				}
				sum += alpha[j] * num;
				num--;
			}
		System.out.println(sum);	 
	}
}


