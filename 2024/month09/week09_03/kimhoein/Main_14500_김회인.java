package study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_14500_김회인 {
	static int delta_1[][] = {{0,0},{1,0},{0,1},{1,1}};
	static int delta_2[][] = {{0,0},{1,0},{2,0},{3,0}};
	static int delta_3[][] = {{0,0},{1,0},{2,0},{0,1}};
	static int delta_4[][] = {{0,0},{1,0},{1,1},{2,1}};
	static int delta_5[][] = {{0,0},{0,1},{0,2},{1,1}};
	static int total[][][] = {delta_1,delta_2,delta_3,delta_4,delta_5};
	static int change[][] = {{1,1},{1,-1},{-1,1},{-1,-1}};
	
	static int map[][];
	static int N,M;
	static int max=0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st= new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		for(int i=0; i<N; i++)
		{
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++)
			{
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
				
		for(int i=0; i<N; i++)
		{
			for(int j=0; j<M; j++)
			{
				cal(i,j);
				cal_2(i,j);
			}
		}
		
		bw.write(Long.toString(max));
		bw.flush();
		bw.close();
		br.close();
		
	}
	
	static void cal(int x, int y)
	{
		for(int i=0; i<total.length; i++)
		{
			for(int w=0; w<4; w++)
			{
				int temp=0;
				for(int j=0; j<4; j++)
				{
					int dx = x+total[i][j][0] * change[w][0];
					int dy = y+total[i][j][1] * change[w][1];
					
					if(!(dx >=0 && dx <N && dy >=0 && dy<M))break;
					
					temp += map[dx][dy];
				}				
				max = Math.max(temp, max);
			}
			
		}
	}
	
	static void cal_2(int x, int y)
	{
		for(int i=0; i<total.length; i++)
		{
			for(int w=0; w<4; w++)
			{
				int temp=0;
			
				for(int j=0; j<4; j++)
				{
					int dx = x+total[i][j][1] * change[w][0];
					int dy = y+total[i][j][0] * change[w][1];
					
					if(!(dx >=0 && dx <N && dy >=0 && dy<M))break;
					
					temp += map[dx][dy];
				}
				max = Math.max(temp, max);
			}
		}
	}
}
