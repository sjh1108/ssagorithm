package algo2025_10_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1270_전쟁땅따먹기 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N,Pnum;
	static long[] peoples;
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine());
		
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
			Pnum = Integer.parseInt(tokens.nextToken());
			peoples = new long [Pnum];
			
			for (int j = 0; j < Pnum; j++) {
				// 습관적 parseInt조심; 런타임에러 떴음.
				peoples[j] = Long.parseLong(tokens.nextToken());
			}
			
			long findNum = 0;
			int cnt = 0;
			for (int j = 0; j < Pnum; j++) {
				if(cnt == 0) {
					findNum = peoples[j];
					cnt = 1;
				}else if(peoples[j] == findNum) {
					cnt++;
				}else {
					cnt--;
				}
			}
			// 병사수 확인후 과반수이면 병사 출력 아니면 "SYJKGW" 출력
			int realCnt = 0;
            for (int j = 0; j < Pnum; j++) {
                if (peoples[j] == findNum) {
                	realCnt++;
                }
            }
            if (realCnt > Pnum / 2) {
                System.out.println(findNum);
            } else {
                System.out.println("SYJKGW");
            }
			
		} //tc
	} //main
}
