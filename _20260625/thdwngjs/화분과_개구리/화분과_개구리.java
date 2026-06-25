package _20260625.thdwngjs.화분과_개구리;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        // 개구리가 들어있는 화분 위치(ball)를 추적하다가,
        // 매 연산마다 두 화분의 내용물을 맞바꾸는(swap) 시뮬레이션 문제.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());     // 화분 개수 (위치 추적만 하므로 직접 사용 X)
        long ball = Long.parseLong(st.nextToken());   // 개구리가 처음 들어있는 화분 번호
        int m = Integer.parseInt(st.nextToken());     // 교환 연산 횟수

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            // a <-> b 를 바꿀 때, 개구리가 둘 중 한쪽에 있으면 반대쪽으로 따라 이동.
            // (그 외 화분은 어디에 있든 개구리 위치에 영향이 없으므로 무시)
            if (ball == a) ball = b;
            else if (ball == b) ball = a;
        }
        System.out.println(ball); // 모든 교환 후 개구리의 최종 화분 번호
    }
}