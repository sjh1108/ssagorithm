package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_2580_김회인 {
	static class num
	{
		int n;
		num(int n)
		{
			this.n = n;
		}
	}
	
	static num map[][];
	static num bucket[][][][];
	static boolean stop = false;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		map = new num[9][9];
		bucket = new num[3][3][3][3];
		
		for(int i=0; i<9; i++)
		{
			st = new StringTokenizer(br.readLine());
			for (int j=0; j<9;j++)
			{
				 
				num n = new num(Integer.parseInt(st.nextToken()));
				map[i][j] = n;
				bucket[i/3][j/3][i%3][j%3] = n;

			}
		}
		
		//System.out.println(" " + row[0].size());
		
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				
				if(map[i][j].n != 0) continue;
				
				//check_rezo(i,j);
			}
		}
		
		//bw.write(Integer.toString(N-len));
		sudoku(0,0);
		
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				bw.write(Integer.toString(map[i][j].n) + " ");
			}
			bw.write("\n");
		}
		bw.flush();
		bw.close();
		br.close();

	}
	
	public static void sudoku(int row,int col)
	{
		//System.out.println(row + " " + col);
		
		if(col==9)
		{
			col=0;
			row++;
		}
		
		if(row==9)
		{
			stop = true;
			return;
		}
		
		if(map[row][col].n ==0)
		{
			for(int i=1; i<=9; i++)
			{
				if(check_zero(row,col,i)) 
				{
					map[row][col].n = i;
					sudoku(row,col+1);
				}
				if(stop) return;
				map[row][col].n = 0;
			}
			return;
		}
		
		sudoku(row,col+1);
	}
	
	static boolean check_zero(int x, int y,int n)
	{
		for(int i=0; i<9; i++)
		{
			if(map[i][y].n == n) return false;
		}
		
		for(int i=0; i<9; i++)
		{
			if(map[x][i].n == n) return false;
		}
		
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(bucket[x/3][y/3][i][j].n == n) return false;
			}
		}
		
		return true;
	}
	
}
