package month08.week02.pkt;
import java.io.*;
import java.util.*;


public class Main {
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		while(true) {
			
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			// 0, 0이 입력되면 나누기가 안되니까, 출력안됨.
			// 제일 먼저 코드 배치
			if (N == 0 && M == 0) {
				break;
			}
			
			
			if (M % N == 0) {
				System.out.println("factor");
			} else if (N % M == 0) {
				System.out.println("multiple");
			} else {
				System.out.println("neither");
			}
			
			
		}
				
		
	}

}
