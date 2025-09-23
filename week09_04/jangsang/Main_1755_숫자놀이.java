package algo2025_09_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1755_숫자놀이 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        String[] numStr = {
            "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine"
        };

        int size = N - M + 1;
        String[] words = new String[size];

        for (int i = 0; i < size; i++) {
            int x = M + i;
            String word;
            if (x < 10) {
                word = numStr[x];
            } else {
                word = numStr[x / 10] + " " + numStr[x % 10];
            }
            words[i] = word + ":" + x;
        }

        Arrays.sort(words);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            String[] parts = words[i].split(":");
            int num = Integer.parseInt(parts[1]);

            if (i % 10 != 0) {
                sb.append(' ');
            }
            sb.append(num);

            if (i % 10 == 9) {
                sb.append('\n');
            }
        }

        System.out.print(sb.toString());
    }
}
