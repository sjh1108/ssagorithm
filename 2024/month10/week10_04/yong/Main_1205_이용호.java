package algostudy.baek.week10_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1205_이용호 {
/*
 * 백준 1205 : 등수 구하기
 * 비 오름차순 -> 내림차순
 * 등수는 보통 위에서 부터 결정
 * 같은 점수는 가장 작은 등수로 -> 1 2 2 4
 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 리스트에 있는 점수 개수
		int newScore = Integer.parseInt(st.nextToken()); 
		int P = Integer.parseInt(st.nextToken()); // 랭킹 제한
		

		if(N == 0) 
		{
			System.out.println(1);
			return;
		}
		
		int[] scores = new int[N];
	
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) 
		{
			scores[i] = Integer.parseInt(st.nextToken());
		}
		
		int rank = 1;
		for(int i = 0; i < N; i ++) 
		{
			if(scores[i] > newScore) 
			{
				rank++;
			}
			else 
			{
				break;
			}
		}
		
		//랭킹 꽉차있고, 마지막 점수 newScore이상이면 진입 x
		if(N == P && newScore <= scores[N-1]) 
		{
			System.out.println(-1);
		}
		else 
		{
			System.out.println(rank);
		}
		
	}
}
