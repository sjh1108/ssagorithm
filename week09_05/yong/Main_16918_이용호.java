package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_16918_이용호 {
/*
 * 홀수 때는 전부 폭탄
 * 0, 4, 8... map1
 * 2, 6, 10...map2
 */
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int R, C, N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		char[][] map1 = new char[R][C];
		char[][] map2 = new char[R][C];
		char[][] map3 = new char[R][C];
		char[][] fullMap = new char[R][C];
		
		for(int i = 0; i < R; i++) {
			String line = br.readLine();
			for(int j = 0; j < C; j++) {
				map1[i][j] = line.charAt(j);
				map2[i][j] = 'O';
				fullMap[i][j] = 'O';
			}
					
		}
		map2 = explode(map1);
		map3 = explode(map2); //map2의 폭탄이 터짐
		
		if(N  == 1) {
			for(int i = 0; i < R; i++) {
				System.out.println(map1[i]);
			}
		}
		else if(N % 2 == 0) {
			for(int i = 0; i < R; i++) {
				System.out.println(fullMap[i]);
			}
		}
		else if(N % 4 == 3) {
			for(int i = 0; i < R; i++) {
				System.out.println(map2[i]);
			}
		}
		else {
			for(int i = 0; i < R; i++) {
				System.out.println(map3[i]);
			}
		}
	}
	static char[][] explode(char[][] map) {
        char[][] newMap = new char[R][C];

        // 일단 전부 폭탄으로 채움
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                newMap[i][j] = 'O';
            }
        }

        // 폭탄이 있는 자리와 그 주변을 '.'으로 바꿈
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == 'O') {
                    newMap[i][j] = '.';
                    for (int d = 0; d < 4; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if (nx < 0 || ny < 0 || nx >= R || ny >= C) continue;
                        newMap[nx][ny] = '.';
                    }
                }
            }
        }
        return newMap;
    }

}
