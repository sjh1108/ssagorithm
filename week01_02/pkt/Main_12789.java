package week01_01;

import java.io.*;
import java.util.*;

// 도키도키 간식드리미
public class Main_12789 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int need = 1;
        int[] stack = new int[N];
        int top = -1;

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(st.nextToken());

            // 스택에서 받을 수 있으면 먼저 계속 받기
            while (top >= 0 && stack[top] == need) {
                top--;
                need++;
            }

            if (x == need) {
                need++;
            } else {
                stack[++top] = x;
            }
        }

        // 남은 스택 처리
        while (top >= 0 && stack[top] == need) {
            top--;
            need++;
        }

        System.out.print(need == N + 1 ? "Nice" : "Sad");
    }
}