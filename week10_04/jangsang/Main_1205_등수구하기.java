package algo2025_10_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1205_등수구하기 {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(input.readLine());

        int N = Integer.parseInt(tokens.nextToken()); 
        int taesu = Integer.parseInt(tokens.nextToken()); 
        int P = Integer.parseInt(tokens.nextToken()); 

        int[] scores = new int[N];
        if (N > 0) {
            tokens = new StringTokenizer(input.readLine());
            for (int i = 0; i < N; i++) {
                scores[i] = Integer.parseInt(tokens.nextToken());
            }
        }

        int rank = 1;

        if (N == P && scores[N - 1] >= taesu) {
            System.out.println(-1); 
            return;
        }

        for (int i = 0; i < N; i++) {
            if (scores[i] > taesu) {
                rank++;
            } else {
                break;
            }
        }

        System.out.println(rank);
    }
}
