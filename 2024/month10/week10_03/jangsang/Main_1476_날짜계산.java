package algo2025_10_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1476_날짜계산 {
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int e = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int E = 1, S = 1, M = 1;
        int year = 1;

        while (true) {
            if (E == e && S == s && M == m) {
                System.out.println(year);
                break;
            }
            year++;
            E++; S++; M++;
            if (E == 16) E = 1;   
            if (S == 29) S = 1;   
            if (M == 20) M = 1;   
        }
    }
}