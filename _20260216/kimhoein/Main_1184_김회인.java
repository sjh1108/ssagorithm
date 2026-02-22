package study;
import java.io.*;
import java.util.*;

public class Main_1184_김회인 {
	/*
	 * 점을 기준으로 왼쪽 위 오른쪽 아래
	 * 점을 기준으로 오른쪽 위 왼쪽 아래를 조합해서 개수를 구한다
	 * hash_up.getOrDefault(sumB,0);를 사용해서 시간을 줄인게 추가적인 학습 부분
	 */
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		
		int n = Integer.parseInt(br.readLine());
		
		int[][] map = new int[n+1][n+1];
		int[][] Prefix = new int[n+1][n+1];
		
		for(int i=1; i<=n; i++)
		{
			st = new StringTokenizer(br.readLine());
			
			for(int j=1; j<=n; j++)
			{
				map[i][j] = Integer.parseInt(st.nextToken());
			}
			
		}
		
		for(int i=1; i<=n; i++)
		{
			for(int j=1; j<=n; j++)
			{
				Prefix[i][j] = map[i][j] + Prefix[i][j-1] + Prefix[i-1][j] - Prefix[i-1][j-1];
			}
		}
		
		int count = 0;
		HashMap<Integer, Integer> hash_up = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> hash_down = new HashMap<Integer, Integer>();
		int sumA;
		int sumB;
		
		for(int i=1; i<=n; i++)		// 시작점이 1 왼쪽 위 칸이 최소 1*1이 되기 위해서는 2부터 시작해야함 그 이하는 의미 없음
		{
			for(int j=1; j<=n; j++)
			{
				hash_up.clear();
				hash_down.clear();
				// 점 기준 왼쪽 위에 나올 수 있는 경우의 수 구하기
				for(int h=1; i-h>=0; h++)
				{
					for(int w=1; j-w>=0; w++)
					{
						sumA = Prefix[i][j] - Prefix[i-h][j] -Prefix[i][j-w] + Prefix[i-h][j-w];  
						
						hash_up.put(sumA,hash_up.getOrDefault(sumA, 0)+1);
					}
				}
				
				// 점 오른쪽 아래 기준 나올 수 있는 경우의 수 구하기
				
				for(int h=1; i+h<=n; h++)
				{
					for(int w=1; j+w<=n; w++)
					{
						sumB = Prefix[i+h][j+w] - Prefix[i][j+w] - Prefix[i+h][j] + Prefix[i][j];
						
						//hash_down.put(sumB, hash_down.getOrDefault(sumB, 0)+1);
						//if(!hash_up.containsKey(sumB)) continue;
						count += hash_up.getOrDefault(sumB,0);
					}
				}
				
//				for(int key : hash_up.keySet())
//				{
//					if(hash_down.containsKey(key))
//					{
//						count += hash_up.get(key) * hash_down.get(key);
//						
//					}
//				}
//				
				// 점 기준 오른쪽 위에 나올 수 있는 경우의 수 구하기
				hash_up.clear();
				hash_down.clear();
				
				for(int h=1; i-h>=0; h++)
				{
					for(int w=1; j+w<=n; w++)
					{
						sumA = Prefix[i][j+w] - Prefix[i-h][j+w] - Prefix[i][j] + Prefix[i-h][j];
						
						hash_up.put(sumA, hash_up.getOrDefault(sumA,0)+1);
					}
				}
				
				for(int h=1; i+h<=n; h++)
				{
					for(int w=1; j-w >=0; w++)
					{
						sumB = Prefix[i+h][j] - Prefix[i][j] - Prefix[i+h][j-w] + Prefix[i][j-w];
						
						//hash_down.put(sumB, hash_down.getOrDefault(sumB, 0)+1);
						//if(!hash_up.containsKey(sumB)) continue;
						count += hash_up.getOrDefault(sumB,0);
					}
				}
				
//				for(int key : hash_up.keySet())
//				{
//					if(hash_down.containsKey(key))
//					{
//						count += hash_up.get(key) * hash_down.get(key);
//					}
//				}
			}
		}
	
		
		bw.write(Integer.toString(count));
		bw.flush();
		bw.close();
		
	}

}
