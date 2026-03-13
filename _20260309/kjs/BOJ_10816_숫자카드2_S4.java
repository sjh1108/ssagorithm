import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < M; i++) {
            int target = Integer.parseInt(st.nextToken());

            // lower bound
            int left = 0;
            int right = N;

            while (left < right) {
                int mid = (left + right) / 2;

                if (arr[mid] >= target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            int lower = left;

            // upper bound
            left = 0;
            right = N;

            while (left < right) {
                int mid = (left + right) / 2;

                if (arr[mid] > target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            int upper = left;

            sb.append(upper - lower).append(" ");
        }

        System.out.print(sb.toString());
    }
}
