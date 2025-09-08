package algorithm.d0907;

import java.io.*;
import java.util.*;

public class BOJ_11866_요세푸스문제O {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 사람 수
        int K = Integer.parseInt(st.nextToken()); // K번째 제거

        ArrayDeque<Integer> q = new ArrayDeque<>(N); // 1..N
        for (int i = 1; i <= N; i++) q.addLast(i);

        StringBuilder sb = new StringBuilder();
        sb.append('<');
        while (!q.isEmpty()) {
            // K-1번 앞에서 꺼내 뒤로 보내 회전
            for (int i = 1; i < K; i++) q.addLast(q.pollFirst());
            // K번째 제거
            sb.append(q.pollFirst());
            if (!q.isEmpty()) sb.append(", ");
        }
        sb.append('>');

        System.out.print(sb.toString());
    }
}
