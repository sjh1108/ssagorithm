import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main_1507_김회인 {
	/*
	 * 플로이드 워셜인데
	 * dist[j][i]+ dist[i][k] == dist[j][k]
	 * 즉 출발지 경유지 목적지를 거쳐가는 곳과
	 * 출발지 목적지의 거리가 같다면
	 * 출발지 목적지 바로 가는 도로는 굳이 존재 하지 않아도 괜찮다를 라는 발상을 이용해서 문제를 풀이한다
	 * 
	 */
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(br.readLine());
		int[][] dist = new int[N+1][N+1];
		int[][] origin = new int[N+1][N+1];
		
		for(int i=1; i<=N; i++)
		{
			st = new StringTokenizer(br.readLine());
			
			for(int j=1; j<=N; j++)
			{
				dist[i][j] = Integer.parseInt(st.nextToken());
				origin[i][j] = dist[i][j];
			}
			
		}
		
		for(int i=1; i<=N; i++)
		{
			for(int j=1; j<=N; j++)
			{
				for(int k=1; k<=N; k++)
				{
					//System.out.println("dist[j][i] : " + dist[j][i] + " dist[i][k] + " + dist[i][k] + " dist[i][j] : " + dist[i][j]);
					
					if(i == j || i == k || j== k) continue;
					
					if(dist[j][i] + dist[i][k] < dist[j][k])
					{
						System.out.println("-1");
						return;
					}
					
					if(dist[j][i]+ dist[i][k] == dist[j][k]) {
						origin[j][k] = 0;
						//System.out.println("1");
					}
				}
			}
		}
		
		
		int total = 0;
		
		for(int i=1; i<=N; i++)
		{
			for(int j=1; j<=N; j++)
			{
				total += origin[i][j];
			}
		}
		
		System.out.println(total/2);
		
		//bw.write(Integer.toString(max));
		bw.flush();
		bw.close();
		
    }
}