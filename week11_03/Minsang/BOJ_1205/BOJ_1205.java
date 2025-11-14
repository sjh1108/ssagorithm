package week11_03.Minsang.BOJ_1205;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1205 {
	static int N, S, P; // N: 랭킹 개수, S: 새로운 점수, P: 랭킹에 올라갈 수 있는 점수 개수
	// 출력해야할 것 : 태수의 새로운 점수의 랭킹 리스트에서 몇등인지 ?
	static int[] Rank;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 첫째줄 입력받을 것 : N, 태수 점수, P
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		
		Rank = new int[N];
		
		// 랭킹 개수가 0일 경우에는 태수가 1등
		if(N == 0) {
			System.out.println(1);
			return; // 종료
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			Rank[i] = Integer.parseInt(st.nextToken());
		}
		
		// 리스트에 있는 점수 N개 = 랭킹 리스트에 올라갈 수 있는 점수 P개 && 새로운 점수는 리스트에 있는 점수들보다 작으면
		// 리스트 갱신 x -> -1 출력
		if(N == P && S <= Rank[N-1]) {
			System.out.println(-1);
		} else {
			
			int Ranking = 1;
			
			for(int i = 0; i < N; i++) {
				if(S < Rank[i]) {
					Ranking++;
				} else {
					break;
				}
				
			}
			System.out.println(Ranking);
		}

	}

}