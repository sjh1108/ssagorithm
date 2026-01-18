package week01_01;

import java.io.*;
import java.util.*;

// 최소공배수 실버5
public class Main_13241 {
    static long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        long g = gcd(a, b);
        long lcm = (a / g) * b; // overflow 방지용으로 먼저 나눔

        System.out.println(lcm);
    }
}
