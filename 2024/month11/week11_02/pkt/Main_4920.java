package week_11_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;


// 테트리스 게임
public class Main_4920 {
	
	static int N;
	static int[][] map;
	static int max;
	
	static int[][][] blocks = {
			
			// 모든 모형을 세팅해놓고, 모든 좌표에 넣기 위함. 
			// x,y랑 행,열은 다름. 그러니까, 안헷갈리려면 그림 그려서 확인
			// 각도 회전보다 이게 더 안전함. 
			
			// 블럭이 13개임. 
			{{0,0},{0,1},{0,2},{0,3}}, // ㅡ
			{{0,0}, {1,0}, {2,0}, {3,0}}, // ㅣ
			
			{{0,0}, {0,1}, {1,1}, {1,2}}, // z
			{{0,0}, {1,0}, {1,-1}, {2,-1}},
			
			{{0,0}, {0,1}, {0,2}, {1,2}}, // L
			{{0,0}, {1,0}, {2,0}, {2,-1}},
			{{0,0}, {1,0}, {1,1}, {1,2}},
			{{0,0}, {0,1}, {1,0}, {2,0}},
			
			{{0,0}, {0,1}, {0,2}, {1,1}}, // ㅗ
			{{0,0}, {1,0}, {1,-1}, {2,0}},
			{{0,0}, {1,0}, {1,-1}, {1,1}},
			{{0,0}, {1,0}, {1,1}, {2,0}},
			
			{{0,0}, {1,0}, {0,1}, {1,1}} // ㅁ
	};
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int tc = 0;
		
		while(true) { // 이렇게 두면 전역을 새로운 케이스마다 주요 요소를 계속 리셋해줘야 함. 중요. - 고쳐야 함. 
			
			max = Integer.MIN_VALUE; // 값 다시 갱신. 
			
//			if(N == 0) break; // 전역변수로  선언한 N은 0이니까 여기에 들어가면 안됨. 프로그램이 바로 종료당함.
			
			
			String line = br.readLine(); // 아오 페리시치
			line = line.trim(); // 아오 페리시치
			N = Integer.parseInt(line);
			if(N == 0) break;
			tc++; // 원래는 코드 3줄 위에 뒀는데, 자체 코드 리뷰해보니까, 유효한 테스트케이스 개수에 증가시키는게 정석이라 이 위치가 나음.  
			
			map = new int[N][N];
			// 행: 0 ~ N-1
			// 열: 0 ~ N-1
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 알고리즘 실행
			// for문 처리
			// i,j
			// shape
			// 좌표 4지정 탐색
			// 0,1
			
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					
					
					label:
					// 논리적으로 좌표하나당 모양 다 체크하고, 넘어가기. 범위 넘어간 거만 확인하기. 
					for (int shape = 0; shape < 13; shape++) { // 모양
						
						int sum = 0; // sum을 여기에 두어야 초기화 및 합산 가능
						
						
						for (int d = 0; d < 4; d++) { // 좌표 4지점 탐색.
							
							int nx = i + blocks[shape][d][0];
							int ny = j + blocks[shape][d][1];
							
							if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue label;
							
							// 4지점 하나씩 저장하기. 
							sum += map[nx][ny];
						}
						
						// 좌표 4지점 체크가 완료되었다면.
						if(sum > max) max = sum;
					}
				}
			}
			
			// 출력을 위한 연결부
			sb.append(tc).append(".").append(" ").append(max).append("\n");	
		}
		// 출력부
		System.out.println(sb);
	}
}
