package ssagorithm.week02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class week02_boj30802_웰컴키트 {

	 public static void main(String[] args) throws IOException {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        
	        // 참가자 수 N
	        long N = Long.parseLong(br.readLine().trim());
	        
	        // N 명 중 사이즈 별 사람 수 
	        StringTokenizer st = new StringTokenizer(br.readLine());
	        long[] sizes = new long[6];
	        for (int i = 0; i < 6; i++) {
	            sizes[i] = Long.parseLong(st.nextToken());
	        }

	        // 티셔츠 묶음 T, 펜 묶음 P 숫자 입력
	        st = new StringTokenizer(br.readLine());
	        long T = Long.parseLong(st.nextToken());
	        long P = Long.parseLong(st.nextToken());

	        // 티셔츠 사이즈 합
	        long tShirt = 0;
	        for (int i = 0; i < 6; i++) {
	            long s = sizes[i];
	            long a = s/T;
	            if(s%T !=0) {
	            	a++;
	            }
	            tShirt += a;
	        }

	        // N/P 몫 , N%P 나머지
	        long pens = N / P;
	        long pen = N % P;

	        // 출력
	        System.out.println(tShirt);
	        System.out.println(pens + " " + pen);
	    }
}
