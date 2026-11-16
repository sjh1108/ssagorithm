import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 브루트 포스, 비트 마스킹, 누적 XOR
     *
     * 연속된 구간 내 모든 수를 XOR 한 값이 K인 구간의 수를 카운트해야 함
     * 주어지는 구간 최대 크기는 5000이므로 약 1250만 회 연산으로 해결할 수 있음
     * ^(XOR) 비트 연산자 사용
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int cnt = 0;
        int[] xor = new int[n];
        st = new StringTokenizer(br.readLine());

        for(int i=0; i<n; i++) {
            xor[i] = Integer.parseInt(st.nextToken());
            if(xor[i] == k) cnt++;

            // 이전 구간부터 자신까지의 구간에 대한 누적 XOR 연산
            for(int j=i-1; j>=0; j--) {
                xor[j] ^= xor[i];
                if(xor[j] == k) cnt++;
            }
        }

        System.out.println(cnt);
    }

}