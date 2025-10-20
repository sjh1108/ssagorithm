package day1020;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1476_날짜계산 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int E = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int y = E;
        while (true) {
            boolean sun = (y - S) % 28 == 0;
            boolean moon = (y - M) % 19 == 0;

            if (sun && moon) { 
                System.out.println(y);
                break;
            }
            y += 15;
        }
    }
}