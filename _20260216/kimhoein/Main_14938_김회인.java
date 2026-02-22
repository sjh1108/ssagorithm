import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main_14938_김회인 {
	/*
	 * 플로이드 워셜
	 */
	static int INF = Integer.MAX_VALUE/2-1;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());
		
		int[] item = new int[N+1];
		int[][] dist = new int[N+1][N+1];
		
		st = new StringTokenizer(br.readLine());
		
		for(int i=1; i<=N; i++)
		{
			item[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i=0; i<=N; i++)
		{
			Arrays.fill(dist[i], INF);
		}
		
		for(int i=0; i<r; i++)
		{
			//System.out.println("i : " + i);
			st  = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			dist[a][b] = c;
			dist[b][a] = c;
			//System.out.println("a : " + a+ " " + b + " " + c);
		}
		
		//System.out.println("adsfa");
		
		for(int i=1; i<=N; i++)
		{
			for(int j=1; j<=N; j++)
			{
				for(int k=1; k<=N; k++)
				{
					if(dist[j][i] + dist[i][k] < dist[j][k]) dist[j][k] = dist[j][i] + dist[i][k];
				}
			}
		}
		
		int max=0;
		
		for(int i=1; i<=N; i++)
		{
			int total=0;
			total += item[i];
			
			for(int j=1; j<=N; j++)
			{
				if(i == j) continue; 
				//System.out.println("dist[i][j] : " + dist[i][j] + " " + item[j]);
				if(dist[i][j] <= m) total += item[j];
			}
			//System.out.println("total : " + total);
			max = Math.max(max, total);
		}
		
		bw.write(Integer.toString(max));
		bw.flush();
		bw.close();
		
    }
}