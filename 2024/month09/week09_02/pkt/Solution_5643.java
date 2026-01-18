package week09_02;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_5643 
{

	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder(); // 스트링 빌더 -> 나중에 결과로 받을 놈.
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++)
		{
			int N = Integer.parseInt(br.readLine()); // 학생수
			int M = Integer.parseInt(br.readLine()); // 두 학생수를 비교한 횟수
			
			boolean[][] graph = new boolean[N+1][N+1]; // 그래프 생성
			// 정점 번호를 1부터 N까지 쓰기 위해서예요.
			for (int i = 0; i < M; i++)
			{
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				graph[a][b] = true; // true -> a에서 b로 가는 -> a < b
				
			}
			
			// 1. 3중 for문을 활용하여 부셔버림 1 = 2 / 2 = 3 // 1 = 3
			
			for (int k = 1; k <= N; k++) // 중간 학생 k
			{
				for (int i = 1; i <= N; i++) // 출발 학생 i
				{
					for (int j = 1; j <= N; j++) { // 도착 학생 j
						graph[i][j] = graph[i][j] || (graph[i][k] && graph[k][j]); // = 뒤에가 우항
						//  사실은 여기 방문처리 안전장치를 둬야 더 안정할 수도 - 주헌
						// graph[i][j] 얘는 필요함.
					}
				}
			}
			
			// 2
			
			int ans = 0; // 결과 중 하나. 
			label1 : for(int i = 1; i <= N; i++) // 학생 i
			{
				for (int j = 1; j <= N; j++) // 학생 j
				{
					if(i==j) continue; // 자기자신은 패스
					
					// i와 j 사이에 어떤 경로도 없으면 (i<j도 아니고 j<i도 아님) 
					// 즉. 누가 더 큰지 작은지 알 수 없다.
					// label에서 label로 감. 
					if(!graph[i][j] && !graph[j][i]) continue label1;
					// 둘 중 하나라도 연결이면 ans에 더해줘라. 
				}
				ans++;
			}
			
			sb.append("#").append(t).append(" ").append(ans).append("\n");
		}	
		System.out.println(sb);
	}
}



