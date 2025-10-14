package algostudy.baek.m9w4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14889_이용호 {
/*
 * 모인사람 N명(짝수) 1~N 배정
 * Sij -> i번 사람과 j번 사람 시너지
 * 팀 반반으로 나눠야한다 -> 조합으로 3명뽑고 능력치 차 계산
 */
	static int N, minGap;
	static int[][] S;
	static boolean[] team;
	static int[] teamA, teamB;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		S = new int[N][N];
		
		for(int i = 0; i < N; i++) { // 능력치 입력 받기
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				S[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		minGap = Integer.MAX_VALUE;
		team = new boolean[N];
		
		comb(0,0);
		System.out.println(minGap);

	}
	
	static void comb(int idx, int start) {
		if(idx == N/2) {
			//팀 배분
			teamA = new int[N/2]; teamB = new int[N/2];
			int AIdx = 0; int BIdx = 0;
			for(int i = 0; i < N; i++) {
				if(team[i]) {
					teamA[AIdx] = i;
					AIdx++;
				}
				else {
					teamB[BIdx] = i;
					BIdx++;
				}
			}
			//팀원 능력치 합 Sij + Sji
			int Asum = 0; int Bsum = 0;
			for(int i = 0; i < N/2; i++) {
				for(int j = 0; j < N/2; j++) {
					if(i == j) continue;
					Asum += S[teamA[i]][teamA[j]];
					Bsum += S[teamB[i]][teamB[j]];
				}
			}
			//최솟값 갱신
			minGap = Math.min(minGap, Math.abs(Asum-Bsum));
			return;
		}
		for(int i = start; i < N; i++) {
			team[i] = true;
			comb(idx+1, i+1);
			team[i] = false;

		}
	}

}
