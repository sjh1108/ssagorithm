package algo2026_03_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_3088_화분부수기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        int[][] pots = new int[n][3];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pots[i][0] = Integer.parseInt(st.nextToken());
            pots[i][1] = Integer.parseInt(st.nextToken());
            pots[i][2] = Integer.parseInt(st.nextToken());
        }

        boolean[] seen = new boolean[1000001]; // seen[x] = true이면 숫자 x가 이미 깨진 화분에 있음
        int count = 0;

        for (int i = 0; i < n; i++) {
            int a = pots[i][0], b = pots[i][1], c = pots[i][2];

            if (seen[a] || seen[b] || seen[c]) {
                // 연쇄로 깨짐
            } else {
                // 직접 깨기
                count++;
            }

            seen[a] = true;
            seen[b] = true;
            seen[c] = true;
        }

        System.out.println(count);
    }
}