package algostudy.swea.a4012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_4012_이용호 {
static int[][] score;
static boolean visited[];//중복 방지용이 아니라 음식A와 B구분용
static int mingap;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int TC = 1;TC <= T;TC++) {
			
			int N = Integer.parseInt(br.readLine());
			score = new int[N][N];//음식 점수 배열
			visited = new boolean[N];//중복 선택 방지와 음식A,B 구분
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					score[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			mingap = Integer.MAX_VALUE;//초기값은 최대로
			comb(0,0,N);
			System.out.println("#"+TC+" "+mingap);
		}

	}
	static void comb(int depth,int start, int N) {//중복없고 순서 상관없는 조합base
		if(depth == N/2) { //절반 선택했으면 visited 절반은 F, 나머지 절반은 T
			//visited true는 음식A 재료
			//visited false는 음식B 재료
			int sumA = 0;
			int sumB = 0;
			for(int x = 0;x < N;x++) {
				for(int y = x+1;y < N;y++) {
					if(visited[x] && visited[y]) {//음식 A재료 점수합
						sumA += score[x][y] + score[y][x];
						
					}else if(!visited[x] && !visited[y]){ //음식B 재료 점수합
						sumB += score[x][y] + score[y][x];
					}
					
				}
			}
			mingap = Math.min(Math.abs(sumA-sumB), mingap); //min(|A음식 총 점수 - B음식 총 점수|)
			return;
		}
		
		for(int i = start;i<N;i++) {//start로 다음재료 선택시 현재 인덱스 이후부터 선택하게함 -> 중복 없이 순서 무관 조합
			visited[i] = true;	//현재 재료 선택(중복 방지용 아님!!)
			comb(depth+1,i+1,N); //다음 재료 선택하러
			visited[i] = false; //백트레킹
		}
		
	}

}
