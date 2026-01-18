package week09_02;

//입력 → 범위 계산 → 모든 시작점 탐색 → 8×8 검사 → 최소값 갱신 → 출력

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1018 
{
	
	static boolean[][] arr;
	static int min = 64; // 최솟값
	
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		
		arr = new boolean[N][M];
		// 배열 입력
		for (int i = 0; i < N; i++) 
		{
			String str = br.readLine();
			for (int j = 0; j < M; j++) 
			// 문자열에서 뽑아낸 문자가 'W'라면?	
			if(str.charAt(j) == 'W')
			{
				arr[i][j] = true; // W일때는 true
			}
			else
			{
				arr[i][j] = false; //B일때는 false
			}
		}
		
		// 큰 도화지 위에 8×8짜리 창문을 덮어 놓고, 그 창문을 왼쪽 위에서 오른쪽 아래로 끝까지 밀어갈 수 있는 범위를 계산하는 역할.
		// N - 7 = 마지막 시작 행의 인덱스 + 1
		int N_row = N - 7;
		int M_col = M - 7;
		
		
		for (int i = 0; i < N_row; i++) // i는 0부터 N-8까지 . 시작점 가능한 거 다 탐색하는 거임.
		{
			for (int j = 0; j < M_col; j++) 
			{
				// find(i, j)를 호출해서, "이 영역을 고치려면 최소 몇 칸을 칠해야 하는가?"
				find(i,j);
			}
		}
		System.out.println(min);
	}
	
	
	
	
	static void find(int x, int y)
	{
		int end_x = x + 8;
		int end_y = y + 8;
		int count = 0;
		
		boolean TF = arr[x][y]; // 첫 번째 칸의 색
		
		for (int i = x; i < end_x; i++) 
		{
			for (int j = y; j < end_y; j++) 
			{
				// 올바른 색이 아닐 경우 count 1 증가
				if(arr[i][j] != TF)
				{
					count++;
				}
				
				/*
				 * 다음 칸은 색이 바뀌므로
				 * true라면 false로, false라면 true로
				 * 값을 변경한다.
				 * 
				 */
				TF = (!TF);	
			}
			TF = !TF; // 줄 바뀔 때 반전	
		}

		/*
		 * 
		 * 첫번째 칸을 기준으로 할 때의 색칠할 개수(count)와
		 * 첫번째 칸의 색의 반대되는 색을 기준으로 할 때의
		 * 색칠할 개수(64-count) 중 최솟값을 count에 저장.
		 * 아,, 만약 첫 애가 검정이면 63개 다 다시 색칠해야 함. ㄷㄷ 
		 */
		
		count = Math.min(count,  64 - count);
		
		
		/*
		 * 이전까지의 경우 중 최솟값보다 현재 count값이 
		 * 더 작을 경우 최소값을 갱신
		 */
		
		min = Math.min(min, count);		
	}	
}

