package week0911;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_15650 
{
	static int N;
	static int M;
	static int[] arr;
	static int[] output;
	static boolean[] visited;
	
	
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr =new int[N];
		for (int i = 0; i < N; i++) 
		{
			arr[i] = i + 1;
		}
		
		output = new int[M];
		visited = new boolean[N];
		
		com(0, 0);

	}
	
	
	private static void com(int depth, int start) 
	{
		if(depth == M)
		{
			for (int i = 0; i < M; i++) 
			{
				System.out.print(output[i] + " ");
			}
			System.out.println();
			return;
		}
		
		for (int i = start; i < N; i++) 
		{
				output[depth] = arr[i];
				com(depth + 1, i + 1);
		}

	}
}

