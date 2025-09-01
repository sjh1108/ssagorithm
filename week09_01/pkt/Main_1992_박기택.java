package exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Main_1992_박기택
{
	
	static int N;
	static int[][] board;

	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		board = new int[N][N];
		
		
		for (int i = 0; i < N; i++)
		{
			
			char[] ch = br.readLine().toCharArray();
			
			
			for (int j = 0; j < N; j++) {
				
				board[i][j] = (int)(ch[j] - '0'); 
				
			}
		}
		
		
		partition(0, 0, N);
		
	}
	
	
	private static void partition(int row, int col, int size)
	{
		
		if(colorCheck(row, col, size))
		{
			if(board[row][col] == 0)
			{
				System.out.print(0);
			}
			
			else
			{
				System.out.print(1);
			}
			
			return;
		}
		
		int newSize = size / 2;
		
		System.out.print("(");
		partition(row, col, newSize);
		partition(row, col + newSize, newSize);
		partition(row + newSize, col, newSize);
		partition(row + newSize, col + newSize, newSize);
		System.out.print(")");	
	}
	
	private static boolean colorCheck(int row, int col, int size)
	{
		int color = board[row][col];
		for (int i = row; i < row + size; i++) 
		{
			for (int j = col; j < col + size; j++) 
			{
				if(board[i][j] != color)
				{
					return false;
				}
			}
			
		}
		
		return true;	
	}
}
