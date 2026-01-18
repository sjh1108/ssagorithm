package week01_04.sjh1108.BOJ_2873;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        
        if(R % 2 == 0 && C % 2 == 0){
            int minX = -1;
            int minY = -1;
            int min = Integer.MAX_VALUE;

            for(int i = 0; i < R; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < C; j++) {
                    if((i+j) % 2 == 1){
                        int value = Integer.parseInt(st.nextToken());
                        if(min > value){
                            minX = i;
                            minY = j;
                            min = value;
                        }
                    } else st.nextToken();
                }
            }

            sb.append(solve(C, R, minY, minX));
        }
        else{
            if(R%2 == 1) { 
				sb.append(mapSizeIsNotOdds('R','L','D', C, R));
			}
			else {
				sb.append(mapSizeIsNotOdds('D','U','R', R, C));
			}
        }

        System.out.println(sb.deleteCharAt(sb.length()-1));
    }

    private static StringBuilder solve(int x, int y, int ex, int ey){
        StringBuilder sb = new StringBuilder();

        int uy = ey / 2 * 2;
		sb.append(mapSizeIsNotOdds('R', 'L', 'D', x, uy));
		
		for(int i = 0; i < ex; i++) {
			if(i%2 == 0) {
				sb.append("DR");
			}else {
				sb.append("UR");
			}
		}
		for(int i = ex; i < x-1; i++) {
			if(i%2 == 0) {
				sb.append("RD");
			}else {
				sb.append("RU");
			}
		}
		sb.append("D");
		sb.append(mapSizeIsNotOdds('L', 'R', 'D', x, y-uy-2));

        return sb;
    }

    private static StringBuilder mapSizeIsNotOdds(char f, char s, char b, int x, int y){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x-1; j++){
                if(i%2 == 0)
                    sb.append(f);
                else
                    sb.append(s);
            }
            sb.append(b);
        }

        return sb;
    }
}