package OJ_95;

import java.util.ArrayList;
import java.util.Scanner;

// 부분집합 탐색 문제 -> 백트래킹
public class Main {

    static int N;
    static long L, R;
    static long[] w;

    static ArrayList<Integer> cur = new ArrayList<>();
    static ArrayList<String> output = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        N = scanner.nextInt();
        L = scanner.nextLong();
        R = scanner.nextLong();

        // 짐에는 1번부터 N번까지 번호가 붙어 있다
        w = new long[N + 1];

        for (int i = 1; i <= N; i++) {
            w[i] = scanner.nextLong();
        }

        // 백트래킹 탐색.
        dfs(1, 0L);

        // 출력
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(output.size()).append("\n");
        for (int i = 0; i < output.size(); i++) {
            stringBuilder.append(output.get(i)).append("\n");
        }
        System.out.print(stringBuilder);
    }

    /*
    백트래킹.
    사전순, 하나 이상, 모두 출력.
     */
    static void dfs(int start, long sum) {
        // 조건 만족하는 짐의 번호 문자열로 저장.
        if (!cur.isEmpty() && L <= sum && sum <= R) {
            String temp = String.valueOf(cur.get(0));

            for (int i = 1; i < cur.size(); i++) {
                temp += (" " + cur.get(i));
            }

            output.add(temp);
        }

        // +i 재귀 호출 -> 종료 복귀 -> 마지막 번호 제거, 직전 상태 복원 -> 반복.
        for (int i = start; i <= N; i++) {
            cur.add(i);
            dfs(i + 1, sum + w[i]);
            cur.remove(cur.size() - 1);
        }
    }
}
