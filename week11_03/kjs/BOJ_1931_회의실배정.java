package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1931_회의실배정 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // [0] = 시작 시간, [1] = 끝나는 시간
        int[][] time = new int[N][2];


        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            time[i][0] = Integer.parseInt(st.nextToken()); // 시작 시간
            time[i][1] = Integer.parseInt(st.nextToken()); // 끝나는 시간
        }

        // 정렬
        Arrays.sort(time, (a, b) -> {
            if (a[1] == b[1]) {
                return a[0] - b[0];
            }
            return a[1] - b[1];
        });


        int count = 0;
        int End = 0; 

        for (int i = 0; i < N; i++) {
            if (time[i][0] >= End) {
                count++;
                End = time[i][1];
            }
        }

        System.out.println(count);
    }
}
