import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());

        int[] diff = new int[1002]; 

        for (int i = 0; i < N; i++) {
            int K = Integer.parseInt(br.readLine());
            for (int j = 0; j < K; j++) {
                st = new StringTokenizer(br.readLine());

                int S = Integer.parseInt(st.nextToken());
                int E = Integer.parseInt(st.nextToken());

                diff[S] += 1;
                diff[E] -= 1;
            }

        }

        int[] time = new int[1001];
        time[0] = diff[0];

        for (int i = 1; i <= 1000; i++) {
            time[i] = time[i - 1] + diff[i];
        }


        int sum = 0;
        for (int i = 0; i < T; i++) {
            sum += time[i];

        }

        int maxSum = sum;
        int best = 0;
        for (int start = 1; start + T <= 1000; start++) {
            sum = sum - time[start - 1] + time[start + T - 1];
            if (sum > maxSum) {
                maxSum = sum;
                best = start;
            }
        }

        System.out.println(best + " " + (best + T));

    }

}
