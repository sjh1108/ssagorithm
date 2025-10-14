package exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_2023_박기택 
{
	
	
	static int N;
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	N = Integer.parseInt(br.readLine());
	
	DFS(2, 1);
	DFS(3, 1);
	DFS(5, 1);
	DFS(7, 1);
	
	
	
	}
	
	
	
	private static void DFS(int number, int jarisu) 
	{
		
		if (jarisu == N)
		{
			if(isPrimeNum(number)) 
			{
				System.out.println(number);
			}
			
			return;
			
		}
		
		
		 for (int i = 0; i < 10; i++)
		 {
			 if(i % 2 == 0)
			 {
				 continue;
			 }
			 
			 if (isPrimeNum(number * 10 + i))
			 {
				 DFS(number * 10 + i, jarisu + 1);
			 }
		 }
		
		
		
	}
	
	
	private static boolean isPrimeNum(int num) 
	{
		if (num < 2) // 0,1은 소수 불가
		{
			return false;
		}
		
		for (int i = 2; i <= Math.sqrt(num); i++)
		{
			if (num % i == 0)
				return false;
		}
		
		return true; // if와 for 안걸리면 여기로 감.

		
	}
	
	
	/*
	 * 틀린거
	 * 
	 * 1.
	 * if(isPrimeNum) 
	 * 
	 * 2. DFS안에서 재귀 가능함. 소수면 다시 돌려 굿
	 * 
	 * 3. 조건 사고 -> 이벤트 그냥 쪼개서 생각. 입출력, 조건문 또는 반복문이면 끝!! (핵심사고)
	 * 
	 * 4. for (int i = 2; i < Math.sqrt(num); i++)
	 * 
	 * 
	 * 	 
	 */
	
	
	
	
	
	
	
	
	
	
	
	
}
