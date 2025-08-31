package exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2630_박기택 
{
	static int N;
	static int white;
	static int blue;
	static int[][] board;
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
	
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++) 
			{
				board[i][j] = Integer.parseInt(br.readLine());
			}
		}
		
		partition(0, 0, N);
		
		System.out.println(white);
		System.out.println(blue);
		
	}
	
	private static void partition(int row, int col, int size) //저기 숫자가 아니라, 전역으로 먹이는 거라서, 
	{
		if(checkColor(row, col, size))
		{
			if(board[row][col] == 0)
			{
				white++;
			}
			else
			{
				blue++;
			}
			
			return;
		}
		
		
		// parttition
		int newSize = size / 2;
		
		partition(row, col, newSize);
		partition(row , col + newSize, newSize); // 행 아래로 생각
		partition(row + newSize, col, newSize);
		partition(row + newSize, col + newSize, newSize);

	}
	

	// 여기서 색이 같은지 확인
	private static boolean checkColor(int row, int col, int size)
	{
		
		int color = board[row][col];
		
		for(int i = row; i < row + size; i++) // =동호 붙이지 말기
		{
			for (int j = col; j < col + size; j++) 
			{
				if(board[row][col] != color)
				{
					return false;
				}
			}
		}
		return true;
	}
}
